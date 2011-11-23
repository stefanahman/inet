import java.io.*;   
import java.net.*;  
import java.util.Scanner;

public class Client {
	private static int connectionPort = 8989;

	private static Socket socket = null;
	private static ObjectOutputStream out = null;
	private static ObjectInputStream in = null;
	private static String adress = "";
	
	private static boolean verification = false;
	private static Scanner scanner = new Scanner(System.in);
	
	private static ClientBytePacker cbp = new ClientBytePacker();
	private static ClientByteUnpacker cbu = new ClientByteUnpacker();
	/**
	 * @param args
	 */
	
	private static int checkStatus(byte[] bytePackage) throws IOException{
		switch(bytePackage[0]){
		case 0x00:
			verification = true;
			return 0;
		case 0x01:
			verification = false;
			return 1;
		case 0x02:
			System.out.println("Account: $" + cbu.getBalance(bytePackage));
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
		System.out.println("");
		System.out.println("What would you like to do?");
		System.out.println("---------------------------");
		System.out.println("(1)Balance");
		System.out.println("(2)Withdrawal");
		System.out.println("(3)Deposit");
		System.out.println("(6)Change language");
		System.out.println("(7)Exit");
		System.out.println("---------------------------");
	}
	
	private static void anykey(){
		System.out.println("");
		System.out.println("Press any key to continue..");
		scanner.nextLine();
		scanner.nextLine();
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
        
        System.out.println("--------------------------");
        System.out.println("Welcome to iBank ");
        System.out.println("--------------------------");
        System.out.println("");
        
        byte[] buffer = new byte[10];
        
        int securitycode;
        long amount;
        int menuOption;
        
        System.out.print("cardnumber> ");
        long cardnumber = scanner.nextLong();
        
        System.out.print("pin> ");
        int pin = scanner.nextInt();
        
        out.write(cbp.login(cardnumber, pin));
        out.reset();
        System.out.println("");
        System.out.print("Verificating login ... ");
        
        in.read(buffer);
        
        checkStatus(buffer);
        // Login done
        
        if(verification){
        	System.out.println("Valid!");
        	while(true){
        		menu();
        		System.out.print("> ");
            	menuOption = scanner.nextInt();
            	System.out.println("");
            	switch(menuOption){
            	case 1: // balance
            		out.write(cbp.balance());
            		out.reset();
            		System.out.println("Receiving balance ...");
            		in.read(buffer);
            		checkStatus(buffer);
            		anykey();
            		break;
            	case 2: // withrawal
            		System.out.print("securitycode> ");
                	securitycode = scanner.nextInt();
                	System.out.print("amount> $");
                	amount = scanner.nextInt();
            		out.write(cbp.withdrawal(securitycode, amount));
            		out.reset();
            		System.out.print("Verificating ... ");
            		in.read(buffer);
            		checkStatus(buffer);
            		if(verification)
            			System.out.println("Successful!");
            		else
            			System.out.println("Unsuccessful!");
            		anykey();
            		break;
            	case 3: // deposit
            		System.out.print("securitycode> ");
                	securitycode = scanner.nextInt();
                	System.out.print("amount> $");
                	amount = scanner.nextInt();
            		out.write(cbp.deposit(securitycode, amount));
            		out.reset();
            		System.out.print("Verificating ... ");
            		in.read(buffer);
            		checkStatus(buffer);
            		if(verification)
            			System.out.println("Successful!");
            		else
            			System.out.println("Unsuccessful!");
            		anykey();
            		break;
            	case 6:
            		System.out.println("");
            		System.out.println("Coming soon!");
            		anykey();
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
        	System.out.println("Invalid.");
        }
        System.out.println("Good bye!");

        out.close();
        in.close();
        socket.close();
	}

}
