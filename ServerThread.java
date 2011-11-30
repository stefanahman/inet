import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	private Socket socket = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean loggedin = false;
    private boolean listening = true;
    private long userCardnumber;
    private int userPin;
    private int securitycode;
    private int language;
    private int clientVersion;
    private long amount;
    private long balance;
    private BankAccount activeAccount;
    
    private static Langpacks packs = new Langpacks();
	
	private ServerByteUnpacker sbu = new ServerByteUnpacker();
	private ServerBytePacker sbp = new ServerBytePacker();
    public ServerThread(Socket socket) throws SocketException {
        super("ServerThread");
        this.socket = socket;
    }
    
    private void checkStatus(byte[] bytePackage) throws IOException, InterruptedException{
    	switch(bytePackage[0]){
		case 0:
			userCardnumber = sbu.loginGetCardNumber(bytePackage);
			userPin = sbu.loginGetPin(bytePackage);
			activeAccount = AccountDatabase.login(userCardnumber, userPin);
			//sleep(2000);
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
			//sleep(2000);
			balance = activeAccount.getBalance();
			write(10,sbp.balance(balance));
			
			break;
		case 2:  // Withrawal
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			//sleep(2000);
			if(activeAccount.withdrawal(amount, securitycode)){
				write(2,sbp.verify(1));
			} else {
				write(2,sbp.verify(0));
			}
			break;
		case 3: // Deposit
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			//sleep(2000);
			if(activeAccount.deposit(amount, securitycode)){
				write(2,sbp.verify(1));
			} else {
				write(2,sbp.verify(0));
			}
			break;
		case 4:
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
		case 5:
			clientVersion = sbu.getVersion(bytePackage);
			currentVersion = banner.bannerNeedsUpdate(version);
			if(clientVersion != currentVersion){
				write(1,sbp.version(currentVersion));
				out.writeObject(banner.getLatest(language));
			}
			break;
		case 7:
			write(1,sbp.exit());
			AccountDatabase.logout(activeAccount);
			System.out.println("Client '" + userCardnumber + "' disconnected");
			listening = false;
			break;
		default:
			break;
		}
	}
    
    private void read(byte[] buffer) throws IOException, InterruptedException {
		int expectedSize, size;
		in.read(buffer); // read header
		expectedSize = (int) buffer[0];
		size = in.read(buffer); // read content
		if(size == expectedSize){
			checkStatus(buffer);
		} else if(expectedSize > 0) {
			System.err.println("Error: " + buffer[0]);
			System.err.println("Header mismatch:");
			System.err.println("Size: " + size + ", Expected size: " + expectedSize);
		}
	}
    
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
    		read(buffer);
    		read(buffer);
    		
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
            // e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
