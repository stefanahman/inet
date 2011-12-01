import java.io.*;
import java.net.*;

/**
 * The Class ServerThread.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class ServerThread extends Thread {
	
	private Socket socket = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
    private boolean loggedin = false;
    private boolean listening = true;
    
    private int userPin;
    private int securitycode;
    private int language;
    private int currentLang;
    private int clientVersion;
    private int currentVersion;   
    private long userCardnumber;
    private long amount;
    private long balance;
    
    private BankAccount activeAccount;
    
    private static Banner banner = new Banner();
    private static Langpacks packs = new Langpacks();
    
	private ServerByteUnpacker sbu = new ServerByteUnpacker();
	private ServerBytePacker sbp = new ServerBytePacker();
    
    /**
     * Instantiates a new server thread.
     *
     * @param socket the socket
     * @throws SocketException the socket exception
     */
    public ServerThread(Socket socket) throws SocketException {
        super("ServerThread");
        this.socket = socket;
    }
    
    /**
     * Check status of recieved package.
     *
     * @param bytePackage the byte package
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    private void checkStatus(byte[] bytePackage) throws IOException, InterruptedException{
    	switch(bytePackage[0]){
		case 0:
			userCardnumber = sbu.loginGetCardNumber(bytePackage);
			userPin = sbu.loginGetPin(bytePackage);
			activeAccount = AccountDatabase.login(userCardnumber, userPin);
			if(activeAccount != null){
				System.out.println("Client '" + userCardnumber + "' connected");
				loggedin = true;
				write(2,sbp.verify(1));
			} else {
				loggedin = false;
				write(2,sbp.verify(0));
			}
			break;
		case 1: // Balance
			balance = activeAccount.getBalance();
			write(10,sbp.balance(balance));
			
			break;
		case 2:  // Withrawal
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			if(activeAccount.withdrawal(amount, securitycode)){
				write(2,sbp.verify(1));
			} else {
				write(2,sbp.verify(0));
			}
			break;
		case 3: // Deposit
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			if(activeAccount.deposit(amount, securitycode)){
				write(2,sbp.verify(1));
			} else {
				write(2,sbp.verify(0));
			}
			break;
		case 4: // Language
			language = sbu.getLanguage(bytePackage);
			switch(language){
			case 1: 
				out.writeObject(packs.getSwedish());
				out.reset();
				break;
			case 2: 
				out.writeObject(packs.getEnglish()); 
				out.reset(); 
				break;
			}
			break; 
		case 5: // Banner update
			clientVersion = sbu.getVersion(bytePackage);
			currentVersion = banner.bannerNeedsUpdate(clientVersion);
			if((clientVersion != currentVersion) || (currentLang != language)){
				currentLang = language;
				write(2,sbp.verify(1));
				write(2,sbp.verison(currentVersion));
				out.writeObject(banner.getLatest(language));
				out.reset();
			} else {
				write(2,sbp.verify(0));
			}
			break;
		case 7: // Exit
			write(1,sbp.exit());
			AccountDatabase.logout(activeAccount);
			System.out.println("Client '" + userCardnumber + "' disconnected");
			listening = false;
			break;
		default:
			break;
		}
	}
    
    /**
     * Read package from socket.
     *
     * @param buffer the buffer
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws InterruptedException the interrupted exception
     */
    private void read(byte[] buffer) throws IOException, InterruptedException {
		int expectedSize, size;
		in.read(buffer); // read header
		expectedSize = (int) buffer[0];
		size = in.read(buffer); // read content
		if(size == expectedSize){
			checkStatus(buffer);
		} else if(size != -1) {
			System.err.println("Error: " + buffer[0]);
			System.err.println("Header mismatch:");
			System.err.println("Size: " + size + ", Expected size: " + expectedSize);
		}
	}
    
	/**
	 * Write to socket.
	 *
	 * @param size the size
	 * @param pack the pack
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void write(int size, byte[] pack) throws IOException {
		out.write(sbp.header((byte) size));
		out.reset();
		out.write(pack);
		out.reset();
	}
    

    public void run(){
    	try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
                   
            byte[] buffer = new byte[10];
            System.out.println("Thread opened!");
    		read(buffer); // read lang
    		read(buffer); // read login
    		
    		// Login done
    		if(loggedin) {
    			while(listening){
    				read(buffer);
        		}
    		}
    		
            out.close();
            in.close();
            socket.close();
            System.out.println("Closed thread!");
    	} catch (IOException e){
    		if(loggedin){
    			System.out.println("Client '" + userCardnumber + "' disconnected");
    		}
    		System.out.println("Closed thread!");
        } catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}
