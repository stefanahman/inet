import java.net.*;
import java.io.*;

/**
 * The Class Server.
 *
 * @author Marcus Wallstersson, mwallst@kth.se
 * @author Stefan Åhman, sahman@kth.se
 */
public class Server {

    private static int connectionPort = 8989;
    
    /**
     * The main method start, creates new server threads when client connects.
     *
     * @param args the arguments
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void main(String[] args) throws IOException {
        
        ServerSocket serverSocket = null;
       
        boolean listening = true;
        
        try {
            serverSocket = new ServerSocket(connectionPort);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + connectionPort);
            System.exit(1);
        }
        
        AccountDatabase.readUsers();
        System.out.println("Bank started listening on port: " + connectionPort);
        while (listening)
            new ServerThread(serverSocket.accept()).start();

        serverSocket.close();
    }
}
