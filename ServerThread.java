import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	private Socket socket = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean login = false;
    private boolean listening = true;
    private long userCardnumber;
    private int userPin;
    private int securitycode;
    private long amount;
    private long balance;
    private BankAccount activeAccount;
	
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
			sleep(2000);
			if(activeAccount != null){
				System.out.println("Client '" + userCardnumber + "' connected");
				login = true;
				out.write(sbp.success());
				out.reset();
			} else {
				login = false;
				out.write(sbp.failed());
				out.reset();
			}
			break;
		case 0x01: // Balance
			sleep(2000);
			balance = activeAccount.getBalance();
			out.write(sbp.balance(balance));
			out.reset();
			
			break;
		case 0x02:  // Withrawal
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			sleep(2000);
			if(activeAccount.withdrawal(amount, securitycode)){
				out.write(sbp.success());
				out.reset();
			} else {
				out.write(sbp.failed());
				out.reset();
			}
			break;
		case 0x03: // Deposit
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			sleep(2000);
			if(activeAccount.deposit(amount, securitycode)){
				out.write(sbp.success());
				out.reset();
			} else {
				out.write(sbp.failed());
				out.reset();
			}
			break;
		case 0x07:
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
    
    public void run(){
		
    	try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
                   
            byte[] buffer = new byte[10];
            System.out.println("Thread opened!");
    		in.read(buffer);
    		checkStatus(buffer);
    		
    		
    		// Login done
    		if(login) {
    			while(listening){
        			in.read(buffer);
        			checkStatus(buffer);
        		}
    		}
            out.close();
            in.close();
            socket.close();
            System.out.println("Closed thread!");
    	} catch (IOException e){
    		System.out.println("Closed thread!");
            // e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
