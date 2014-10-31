package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import org.apache.logging.log4j.*;

import library.GameState;
import library.Move;
import library.PropertyFile;

public class Server {
	
	
	private PropertyFile prop;
	private int port;
	private InetAddress address;
	private static Integer maxConnection = 4;
	private boolean isStopped = false;
	private GameState gameState;
	private ServerSocket socketConnection;
	private GameHandler dealer;
	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	
			
	public Server() throws IOException{
		
	
		String myLocation = Server.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		prop = new PropertyFile(myLocation);
		
		address  = InetAddress.getByName(prop.getProperty("server.address"));
		port = Integer.parseInt(prop.getProperty("port"));
		
		this.dealer = new GameHandler();		
	}
	
	/**
	 * @throws IOException
	 * start a listener for every client which connects
	 */
	public void startListen() throws IOException{
		
	     socketConnection = new ServerSocket(this.port,Server.maxConnection,this.address);
	     while (! isStopped()){
	    	 try{
	    		 Socket serverSocket = socketConnection.accept();
	    		 ClientHandler connection = new ClientHandler(serverSocket,dealer);
	    		 connection.start();
	    		 connection.initializeCLient(); 
	    		 dealer.playerAdded();
	    		 
	    	 } catch( IOException e ){
	    		 log.debug("client died unexpectedly");
	    	}
	     }
	}
	
    /**
     * @return
     * allow the server to be stopped gracefully, not implemented yet
     */
    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		try {
			Server server = new Server();
			server.startListen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
}