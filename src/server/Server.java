package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import library.GameState;
import library.Move;
import library.PropertyFile;

/**
 * @author benjamin.indermuehle
 * Server class is the main server class which listens to a port.
 */
public class Server extends Thread{
	
	
	private PropertyFile prop;
	private int port;
	private InetAddress address;
	private static Integer maxConnection = 4;
	private boolean isStopped;
	private ServerSocket socketConnection;
	private GameHandler dealer; 
	private ClientHandler clientHandler;
	private ArrayList<ClientHandler> clients;
	public Server() throws IOException{
		
		prop = new PropertyFile();
		address  = prop.getServerAddress();
		port = prop.getPort();
		this.dealer = new GameHandler();
		isStopped = false;
		
		
	}
	/**
	 * @throws IOException
	 * start a listener for every client which connects
	 */
	private void startListen() throws IOException{
		
	     socketConnection = new ServerSocket(this.port,Server.maxConnection,this.address);
	     clients = new ArrayList<ClientHandler>();
	     while (! isStopped()){
	    	 try{
	    		 Socket serverSocket = socketConnection.accept();
	    		 System.out.println("Client connection recieved, initializing Client");	
	    		 clients.add(clientHandler);
	    		 clientHandler = new ClientHandler(serverSocket,dealer);
	    		 clientHandler.start();
	    		 clientHandler.initializeCLient(); 
	    		 dealer.playerAdded();
	    		 
	    	 } catch( IOException e ){
	    		 System.out.println("client died unexpectedly");
	    	}
	     }
	    	 
	}
	
	public void stopListen() throws IOException{
		this.isStopped = true;
		for (ClientHandler client : clients){
			client.stopListen();
			client.finalize();
		}
	}
	
    /**
     * @return
     * allow the server to be stopped gracefully, not implemented yet
     */
    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    
   
    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    public void run(){
    	try {
			startListen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
    }
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		try {
			Server server = new Server();
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
}
