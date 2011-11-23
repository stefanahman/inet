import java.io.*;   
import java.net.*;  
import java.util.Scanner;

public class Client {
	private static int connectionPort = 8989;

	private static Socket socket = null;
	private static ObjectOutputStream out = null;
	private static ObjectInputStream in = null;
	private static String adress = "";
	
	private static ClientBytePacker cbp = new ClientBytePacker();
	private static ClientByteUnpacker cbu = new ClientByteUnpacker();
	/**
	 * @param args
	 */
	
	private static void checkStatus(byte[] bytePackage) throws IOException{
		switch(bytePackage[0]){
		case 0x00:
			System.out.println("Login sucessful!");
			break;
		case 0x01:
			System.out.println("Login failed!");
			break;
		default:
			System.out.println("default");
			break;
		}
	}
	public static void main(String[] args) throws IOException {
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
        
        System.out.print("cardnumber> ");
        long cardnumber = scanner.nextLong();
        
        System.out.print("pin> ");
        int pin = scanner.nextInt();
        
        out.write(cbp.login(cardnumber, pin));
        

        in.read(buffer,0,10);
        System.out.println("innan");
        checkStatus(buffer);
        System.out.println("efter");
        int i = 0;
        
        while(i != 1){

    		
        }
        out.close();
        in.close();
        socket.close();
	}

}
