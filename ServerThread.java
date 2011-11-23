import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	private Socket socket = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private long[] returnArray;
    public static int menuOption = 0;
    private boolean login = false;
	
	private ServerByteUnpacker sbu = new ServerByteUnpacker();
	private ServerBytePacker sbp = new ServerBytePacker();
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    
    private int checkStatus(byte[] bytePackage) throws IOException{
		switch(bytePackage[0]){
		case 0x00:
			returnArray = new long[2];
			returnArray[0] = sbu.loginGetCardNumber(bytePackage);
			returnArray[1] = sbu.loginGetPin(bytePackage);
			if(validateUser(returnArray[0], returnArray[1])){
				login = true;
				out.write(sbp.loginsucess());
				out.reset();
			} else {
				login = false;
				out.write(sbp.loginfailed());
				out.reset();
			}
			return (menuOption = 0);
		case 0x01:
			returnArray[0] = sbu.writeBalance(bytePackage);
			System.out.println(returnArray[0]);
			return (menuOption = 1);
		case 0x02: 
			returnArray[0] = sbu.getSecurityCode(bytePackage);
			returnArray[1] = sbu.getAmount(bytePackage);
			System.out.print("sc> ");
			System.out.println(returnArray[0]);
			System.out.print("am> ");
			System.out.println(returnArray[1]);
			return (menuOption = 2);
		case 0x03:
			returnArray[0] = sbu.getSecurityCode(bytePackage);
			returnArray[1] = sbu.getAmount(bytePackage);
			System.out.print("sc> ");
			System.out.println(returnArray[0]);
			System.out.print("am> ");
			System.out.println(returnArray[1]);
			return (menuOption = 3);
		case 0x07:
			return (menuOption = 7);
		default:
			return (menuOption = 0);
		}
		
	}
     
    private boolean validateSecurityCode(int userCode) {
        return ((userCode < 100) && (userCode > 0) && (userCode % 2 != 0));
    }
    
    private boolean validateUser(long cardNumber, long pin) {
        int numberOfUsers = 3;
        long[][] userList = new long[numberOfUsers][2];

        userList[0][0] = 1234123412341234L; userList[0][1] = 1234;
        userList[1][0] = 1337; userList[1][1] = 6666;
        userList[2][0] = 0000; userList[2][1] = 9999;

        for (int i = 0 ; i < numberOfUsers ; i++) {
            if (userList[i][0] == cardNumber){
                for (int j = 0 ; j < 2 ; j++) {
                    if (userList[i][j] == pin){
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    public void run(){
		
    	try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
                   
            byte[] buffer = new byte[10];
            int menuOption = 0;
            
    		in.read(buffer);
    		checkStatus(buffer);
    		
    		// Login done
    		
    		if(login) {
    			System.out.println("Client connected, thread opened");
    			while(true){
        			in.read(buffer);
        			menuOption = checkStatus(buffer);
            		switch(menuOption){
                	case 1:
                		System.out.println("Balance");
                		break;
                	case 2:
                		System.out.println("Withraw");
                		break;
                	case 3:
                		System.out.println("Deposit");
                		break;
                	case 7:
                		out.write(sbp.exit());
                		out.reset();
                		break;
                	}
            		if(menuOption == 7)
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
