import java.io.*;   
import java.net.*;  
import java.util.Scanner;

public class Client {
	private static int connectionPort = 8989;

	private static Socket socket = null;
	private static ObjectOutputStream out = null;
	private static ObjectInputStream in = null;
	private static String adress = "";
	private static Language lang = new Language();
	private static Langpacks packs = new Langpacks();
	
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
			System.out.println(lang.account + ": $" + cbu.getBalance(bytePackage));
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
		System.out.println(lang.menu);
		System.out.println("---------------------------");
		System.out.println(lang.menu1);
		System.out.println(lang.menu2);
		System.out.println(lang.menu3);
		System.out.println(lang.menu6);
		System.out.println(lang.menu7);
		System.out.println("---------------------------");
	}
	
	private static void anykey(){
		System.out.println("");
		System.out.println(lang.anyKeymessage);
		scanner.nextLine();
		scanner.nextLine();
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
        try {
            adress = args[0];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println(lang.misingIP);
            System.exit(1);
        }
        try {
            socket = new Socket(adress, connectionPort); 
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (UnknownHostException e) {
            System.err.println(lang.unknownHost + adress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println(lang.noConnection + adress);
            System.exit(1);
        }
        
        System.out.println("--------------------------");
        System.out.println(lang.welcome);
        System.out.println("--------------------------");
        System.out.println("");
        
        byte[] buffer = new byte[10];
        
        int securitycode;
        long amount;
        int menuOption;
        
        System.out.print(lang.cardnumber);
        long cardnumber = scanner.nextLong();
        
        System.out.print(lang.pin);
        int pin = scanner.nextInt();
        
        out.write(cbp.login(cardnumber, pin));
        out.reset();
        System.out.println("");
        System.out.print(lang.verificate);
        
        in.read(buffer);
        
        checkStatus(buffer);
        // Login done
        
        if(verification){
        	System.out.println(lang.valid);
        	while(true){
        		menu();
        		System.out.print("> ");
            	menuOption = scanner.nextInt();
            	System.out.println("");
            	switch(menuOption){
            	case 1: // balance
            		out.write(cbp.balance());
            		out.reset();
            		System.out.println(lang.receivingBalance);
            		in.read(buffer);
            		checkStatus(buffer);
            		anykey();
            		break;
            	case 2: // withrawal
            		System.out.print(lang.securityCode);
                	securitycode = scanner.nextInt();
                	System.out.print(lang.amount + " $");
                	amount = scanner.nextInt();
            		out.write(cbp.withdrawal(securitycode, amount));
            		out.reset();
            		System.out.print(lang.verificate);
            		in.read(buffer);
            		checkStatus(buffer);
            		if(verification)
            			System.out.println(lang.successful);
            		else
            			System.out.println(lang.unsuccessful);
            		anykey();
            		break;
            	case 3: // deposit
            		System.out.print(lang.securityCode);
                	securitycode = scanner.nextInt();
                	System.out.print(lang.amount + " $");
                	amount = scanner.nextInt();
            		out.write(cbp.deposit(securitycode, amount));
            		out.reset();
            		System.out.print(lang.verificate);
            		in.read(buffer);
            		checkStatus(buffer);
            		if(verification)
            			System.out.println(lang.successful);
            		else
            			System.out.println(lang.unsuccessful);
            		anykey();
            		break;
            	case 6:
            		System.out.println("");
            		lang.updateLanguage(packs.getSwedish());
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
        	System.out.println(lang.invalid);
        }
        System.out.println(lang.goodBye);

        out.close();
        in.close();
        socket.close();
	}

}
