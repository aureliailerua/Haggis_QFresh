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
import library.Container;
import library.PropertyFile;

public class Server extends Thread{
		
	private PropertyFile prop;
	private int port;
	private InetAddress address;
	private static Integer maxConnection = 4;
	private boolean isStopped = false;
	private GameState gameState;
	private ServerSocket socketConnection;
	private GameHandler dealer;
	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	
	/**
	 * @throws IOException
	 */
	public Server() throws IOException {
		
		prop = new PropertyFile();
		
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
	public void run(){
		try {
			startListen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stopListen(){
		isStopped = true;
		System.out.println("here");
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
			server.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

		

}
