import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	private Socket socket = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private boolean login = false;
    private long userCardnumber;
    private int userPin;
    private int securitycode;
    private long amount;
    private long balance;
    private BankAccount activeAccount;
	
	private ServerByteUnpacker sbu = new ServerByteUnpacker();
	private ServerBytePacker sbp = new ServerBytePacker();
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    
    private int checkStatus(byte[] bytePackage) throws IOException{
		switch(bytePackage[0]){
		case 0x00:
			userCardnumber = sbu.loginGetCardNumber(bytePackage);
			userPin = sbu.loginGetPin(bytePackage);
			activeAccount = AccountDatabase.login(userCardnumber, userPin);
			if(activeAccount != null){
				login = true;
				out.write(sbp.loginsucess());
				out.reset();
			} else {
				login = false;
				out.write(sbp.loginfailed());
				out.reset();
			}
			return 0;
		case 0x01: // Balance
			balance = activeAccount.getBalance();
			out.write(sbp.balance(balance));
			out.reset();
			return 1;
		case 0x02:  // Withrawal
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			System.out.println(amount);
			activeAccount.withdrawal(amount, securitycode);
			return 2;
		case 0x03: // Deposit
			securitycode = sbu.getSecurityCode(bytePackage);
			amount = sbu.getAmount(bytePackage);
			System.out.println(amount);
			activeAccount.deposit(amount, securitycode);
			return 3;
		case 0x07:
			out.write(sbp.exit());
    		out.reset();
			AccountDatabase.logout(activeAccount);
			return 7;
		default:
			return 0;
		}
		
	}
    
    public void run(){
		
    	try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
                   
            byte[] buffer = new byte[10];
            int menuOption = 0;
            
    		in.read(buffer);
    		checkStatus(buffer);
    		System.out.println("Client connected, thread opened");
    		// Login done
    		if(login) {
    			while(true){
        			in.read(buffer);
            		if(checkStatus(buffer) == 7)
            			break;
        		}
        		
    		}
            out.close();
            in.close();
            socket.close();
            System.out.println("Closed thread!");
    	} catch (IOException e){
            e.printStackTrace();
        }
    }
}
