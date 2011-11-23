import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	private Socket socket = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private long[] returnArray;
	
	private ServerByteUnpacker sbu = new ServerByteUnpacker();
	private ServerBytePacker sbp = new ServerBytePacker();
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    

    private void checkStatus(byte[] bytePackage) throws IOException{
		switch(bytePackage[0]){
		case 0x00:
			returnArray = new long[2];
			returnArray[0] = sbu.loginGetCardNumber(bytePackage);
			returnArray[1] = sbu.loginGetPin(bytePackage);
			if(validateUser(returnArray[0], returnArray[1])){
				System.out.println("s");
				out.flush();
				out.write(sbp.loginsucess());
			} else {
				System.out.println("f");
				out.flush();
				out.write(sbp.loginfailed());
			}
			
			break;
		case 0x01: 
			returnArray[0] = sbu.writeBalance(bytePackage);
			System.out.println("Balance:");
			System.out.println(returnArray[0]);
			break;
		case 0x02: 
			returnArray[0] = sbu.getSecurityCode(bytePackage);
			returnArray[1] = sbu.getAmount(bytePackage);
			System.out.println("Withraw:");
			System.out.println(returnArray[0]);
			System.out.println(returnArray[1]);
			break;
		case 0x03: 
			returnArray[0] = sbu.getSecurityCode(bytePackage);
			returnArray[1] = sbu.getAmount(bytePackage);
			System.out.println("Deposit:");
			System.out.println(returnArray[0]);
			System.out.println(returnArray[1]);
			break;
		default:
			break;
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

    		in.read(buffer,0,10);
    		
    		checkStatus(buffer);
    		
    		in.read(buffer,0,10);
    		
    		checkStatus(buffer);
        	
        	int i = 0;
            
            while(i != 1){

        		
            }
            
            out.close();
            in.close();
            socket.close();
    	} catch (IOException e){
            e.printStackTrace();
        }
    }
}
