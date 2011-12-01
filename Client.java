import java.io.*;   
import java.net.*;  
import java.util.Scanner;

/**
 * The Class Client.
 * 
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class Client {
	
	private static int connectionPort = 8989;
	private static Socket socket = null;
	
	private static ObjectOutputStream out = null;
	private static ObjectInputStream in = null;
	
	private static Scanner scanner = new Scanner(System.in);

	private static String adress = "";
	private static String[] bufferArray;
	private static String banner;
	
	private static int ver = 0;
	private static int  langOpt = 2;
	private static int menuOption;
	private static int securitycode;
	private static long amount;
	
	private static Language lang = new Language();
	
	private static boolean verification = false;
	
	private static byte[] buffer = new byte[10];

	private static ClientBytePacker cbp = new ClientBytePacker();
	private static ClientByteUnpacker cbu = new ClientByteUnpacker();
	
	/**
	 * Check status of package.
	 *
	 * @param bytePackage the byte package
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void checkStatus(byte[] bytePackage) throws IOException{
		switch(bytePackage[0]){
		case 0:
			if(cbu.getVerify(bytePackage) == 1)
				verification = true;
			else
				verification = false;
			break;
		case 1:
			System.out.println(lang.receivingBalance);
			System.out.println(lang.account + ": $" + cbu.getBalance(bytePackage));
			break;
		case 5:
			ver = cbu.getVersion(bytePackage);
		default:
			break;
		}
	}
	
	/**
	 * Menu will print the menu of the client.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	private static void menu() throws IOException, ClassNotFoundException{
		write(2,cbp.requestBanner(ver));
		read(buffer);
		if(verification){
			read(buffer);
			banner = (String) in.readObject();
		}
		System.out.println(((char) 27)+"[2J");
		System.out.println("");
		System.out.println("---------------------------");
		System.out.println(banner);
		System.out.println("---------------------------");
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
	
	/**
	 * Anykey prompts the user to press any key to continue.
	 */
	private static void anykey(){
		System.out.println("");
		System.out.println(lang.anyKeymessage);
		scanner.nextLine();
		scanner.nextLine();
	}
	
	/**
	 * Read will read header and and message package sent from the server.
	 *
	 * @param buffer the buffer
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void read(byte[] buffer) throws IOException {
		int expectedSize, size;
		in.read(buffer); // read header
		expectedSize = (int) buffer[0];
		size = in.read(buffer); // read content
		if(size == expectedSize){
			checkStatus(buffer);
		} else if(expectedSize > 0) {
			System.err.println("Error: " + buffer[0]);
			System.err.println("Header mismatch:");
			System.err.println("Size: " + size + ", Expected size: " + expectedSize);
		}
	}
	
	/**
	 * Write will send header and message package to the server.
	 *
	 * @param size the size
	 * @param pack the pack
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private static void write(int size, byte[] pack) throws IOException {
		out.write(cbp.header((byte) size));
		out.reset();
		out.write(pack);
		out.reset();
	}
	
	/**
	 * The main method, connect client and prompt user menu.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws InterruptedException the interrupted exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static void main(String[] args) throws IOException, InterruptedException, ClassNotFoundException {
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
        
        write(2,cbp.setLang(langOpt));
        bufferArray = (String[]) in.readObject();
    	lang.updateLanguage(bufferArray);
        
        System.out.println("--------------------------");
        System.out.println(lang.welcome);
        System.out.println("--------------------------");
        System.out.println("");
        
        System.out.print(lang.cardnumber);
        long cardnumber = scanner.nextLong();
        
        System.out.print(lang.pin);
        int pin = scanner.nextInt();
        
        write(10,cbp.login(cardnumber, pin));
        System.out.println("");
        System.out.print(lang.verificate);
        
        read(buffer); // login verify
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
            		write(1,cbp.balance());
            		read(buffer);
            		anykey();
            		break;
            	case 2: // withrawal
            		System.out.print(lang.securityCode);
                	securitycode = scanner.nextInt();
                	System.out.print(lang.amount + " $");
                	amount = scanner.nextInt();
            		write(10,cbp.withdrawal(securitycode, amount));
            		System.out.print(lang.verificate);
            		read(buffer);
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
            		write(10,cbp.deposit(securitycode, amount));
            		System.out.print(lang.verificate);
            		read(buffer);
            		if(verification)
            			System.out.println(lang.successful);
            		else
            			System.out.println(lang.unsuccessful);
            		anykey();
            		break;
            	case 6:
            		System.out.println(lang.sv_se);
            		System.out.println(lang.en_us);
            		System.out.print("> ");
            		langOpt = scanner.nextInt();
            		if(langOpt > 0 && langOpt < 3) {
                    	write(2,cbp.setLang(langOpt));
                    	bufferArray = (String[]) in.readObject();
                    	lang.updateLanguage(bufferArray);
            		} else 
            			System.out.println(lang.invalid);
            		anykey();
            		break;
            	case 7:
            		write(1,cbp.exit());
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
