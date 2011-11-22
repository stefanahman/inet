import java.io.*;
import java.net.*;

/**
 *  @author Viebrapadata
*/
public class ATMServerThread extends Thread {
    private Socket socket = null;
    private BufferedReader in;
    PrintWriter out;
    public ATMServerThread(Socket socket) {
        super("ATMServerThread");
        this.socket = socket;
    }

    private String readLine() throws IOException {
        String str = in.readLine();
        //System.out.println(""  + socket + " : " + str);
        return str;
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

    private boolean validateSecurityCode(int userCode) {
        return ((userCode < 100) && (userCode > 0) && (userCode % 2 != 0));
    }

    public void run(){
         
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader
                (new InputStreamReader(socket.getInputStream()));
	
            String inputLine, outputLine;

            out.println("Type in your card number (XXXX):"); 
            inputLine = readLine();
            int cardNumber = Integer.parseInt(inputLine);

            out.println("And your pin (XXXX):"); 
            inputLine = readLine();
            int pin = Integer.parseInt(inputLine);
	
            int balance = 1000;
            int value;
            int choise;
            if(validateUser(cardNumber, pin)) {
                out.println("1");
                out.println("Welcome to Bank! (1)Balance, (2)Withdrawal, (3)Deposit, (4)Exit"); 
                inputLine = readLine();
                choise = Integer.parseInt(inputLine);
            } else {
                out.println("0");
                choise = 4;
                out.println("Wrong cardnumber or pin");
            }
            while (choise != 4) {
                int deposit = 1;
                switch (choise) {
                case 2:
                    deposit = -1;
                case 3:
                    out.println("Enter sercurity code (XX): ");  
                    inputLine = readLine();

                    if(validateSecurityCode(Integer.parseInt(inputLine))){
                        out.println("1"); // sucess
                        out.println("Enter amount: ");
                        inputLine = readLine();
                        value = Integer.parseInt(inputLine);
                        balance += deposit * value;
                        
                        
                    } else {
                        out.println("0");
                        out.println("Wrong security code");
                        
                    }
                case 1:
                    out.println("Current balance is " + balance + " dollars");
                    out.println("(1)Balance, (2)Withdrawal, (3)Deposit, (4)Exit");
                    inputLine=readLine();
                    choise = Integer.parseInt(inputLine);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                default: 
                    break;
                }
            }
            out.println("Good Bye");
            out.close();
            in.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    
    }
}
