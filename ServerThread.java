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
		case 0x00:
			userCardnumber = sbu.loginGetCardNumber(bytePackage);
			userPin = sbu.loginGetPin(bytePackage);
			activeAccount = AccountDatabase.login(userCardnumber, userPin);
			//sleep(2000);
			if(activeAccount != null){
				System.out.println("Client '" + userCardnumber + "' connected");
				loggedin = true;
				out.write(sbp.header((byte) 1));
				out.reset();
				out.write(sbp.success());
				out.reset();
			} else {
				loggedin = false;
				out.write(sbp.header((byte) 1));
				out.reset();
				out.write(sbp.failed());
				out.reset();
			}
			break;
		case 0x01: // Balance
			//sleep(2000);
			balance = activeAccount.getBalance();
			out.write(sbp.header((byte) 10));
			out.reset();
			out.write(sbp.balance(balance));
			out.reset();
			
			break;
		case 0x02:  // Withrawal
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			//sleep(2000);
			if(activeAccount.withdrawal(amount, securitycode)){
				out.write(sbp.header((byte) 1));
				out.reset();
				out.write(sbp.success());
				out.reset();
			} else {
				out.write(sbp.header((byte) 1));
				out.reset();
				out.write(sbp.failed());
				out.reset();
			}
			break;
		case 0x03: // Deposit
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			//sleep(2000);
			if(activeAccount.deposit(amount, securitycode)){
				out.write(sbp.header((byte) 1));
				out.reset();
				out.write(sbp.success());
				out.reset();
			} else {
				out.write(sbp.header((byte) 1));
				out.reset();
				out.write(sbp.failed());
				out.reset();
			}
			break;
		case 0x04:
			language = sbu.getLanguage(bytePackage);
			System.out.println("language " + language);
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
		case 0x07:
			out.write(sbp.header((byte) 1));
			out.reset();
			out.write(sbp.exit());
    		out.reset();
			AccountDatabase.logout(activeAccount);
			System.out.println("Client '" + userCardnumber + "' disconnected");
			listening = false;
			break;
		default:
			break;
		}
		
	}
    
    private byte[] read(byte[] buffer) throws IOException {
		int expectedSize, size;
		in.read(buffer); // read header
		
		expectedSize = (int) buffer[0];
		size = in.read(buffer);
		
		if(size == expectedSize)
			return buffer;
		else
			return null;
	}
    
    public void run(){
		
    	try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
                   
            byte[] buffer = new byte[10];
            System.out.println("Thread opened!");
    		checkStatus(read(buffer));
    		
    		// Login done
    		if(loggedin) {
    			while(listening){
    				checkStatus(read(buffer));
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
