import java.io.*;   
import java.net.*;  
import java.util.Scanner;

public class Client {
	private static int connectionPort = 8989;

	private static Socket socket = null;
	private static ObjectOutputStream out = null;
	private static ObjectInputStream in = null;
	private static String adress = "";
	
	private static boolean login = false;
	
	private static ClientBytePacker cbp = new ClientBytePacker();
	private static ClientByteUnpacker cbu = new ClientByteUnpacker();
	/**
	 * @param args
	 */
	
	private static int checkStatus(byte[] bytePackage) throws IOException{
		switch(bytePackage[0]){
		case 0x00:
			login = true;
			return 0;
		case 0x01:
			login = false;
			return 1;
		case 0x02:
			System.out.println("Current balance: " + cbu.getBalance(bytePackage));
			return 2;
		case 0x03:
			return 3;
		case 0x04:
			return 4;
		default:
			return 0;
		}
	}
	
	private static void menu(){
		System.out.println("Welcome to Bank! (1)Balance, (2)Withdrawal, (3)Deposit, (7)Exit"); 
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
        try {
            adress = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Missing argument ip-adress");
            System.exit(1);
        }
        try {
            socket = new Socket(adress, connectionPort); 
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " +adress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't open connection to " + adress);
            System.exit(1);
        }
        System.out.println("Contacting bank ... ");
        
        byte[] buffer = new byte[10];
        Scanner scanner = new Scanner(System.in);
        int securitycode;
        long amount;
        
        System.out.print("cardnumber> ");
        long cardnumber = scanner.nextLong();
        
        System.out.print("pin> ");
        int pin = scanner.nextInt();
        
        out.write(cbp.login(cardnumber, pin));
        out.reset();
        in.read(buffer);
        checkStatus(buffer);
        // Login done
        
        if(login){ 
        	while(true){
        		menu();
        		System.out.print("> ");
            	int menuOption = scanner.nextInt();
            	switch(menuOption){
            	case 1: // balance
            		out.write(cbp.balance());
            		out.reset();
            		in.read(buffer);
            		checkStatus(buffer);
            		break;
            	case 2: // withrawal
            		System.out.print("securitycode> ");
                	securitycode = scanner.nextInt();
                	System.out.print("amount> ");
                	amount = scanner.nextInt();
            		out.write(cbp.withdrawal(securitycode, amount));
            		out.reset();
            		break;
            	case 3: // deposit
            		System.out.print("securitycode> ");
                	securitycode = scanner.nextInt();
                	System.out.print("amount> ");
                	amount = scanner.nextInt();
            		out.write(cbp.deposit(securitycode, amount));
            		out.reset();
            		break;
            	case 7:
            		out.write(cbp.exit());
            		out.reset();
            		break;
            	} 
            	if(menuOption == 7)
        			break;
            }
        } else {
        	System.out.println("Wrong login.");
        }
        System.out.println("Good bye!");

        out.close();
        in.close();
        socket.close();
	}

}
