import java.io.*;
import java.net.*;

public class ServerThread extends Thread {
	private Socket socket = null;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public ServerThread(Socket socket) {
        super("ServerThread");
        this.socket = socket;
    }
    
    private boolean validateSecurityCode(int userCode) {
        return ((userCode < 100) && (userCode > 0) && (userCode % 2 != 0));
    }
    
    private boolean validateUser(int cardNumber, int pin) {
        int numberOfUsers = 3;
        int[][] userList = new int[numberOfUsers][2];

        userList[0][0] = 1234; userList[0][1] = 1234;
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
