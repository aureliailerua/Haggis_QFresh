package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

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

	public Server() throws IOException{

		this.gameState = new GameState();
		
		String myLocation = Server.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		prop = new PropertyFile(myLocation);
		
		address  = InetAddress.getByName(prop.getProperty("server.address"));
		port = Integer.parseInt(prop.getProperty("port"));
		
	}
	public void listen(){
		try {

	         socketConnection = new ServerSocket(this.port,Server.maxConnection,this.address);
	         while (! isStopped()){
	        	 Socket serverSocket = socketConnection.accept();
	        	 
	        	 System.out.println(this.gameState.makeMove());
	        	 ObjectOutputStream serverOutputStream = new ObjectOutputStream(serverSocket.getOutputStream());
	        	 
	        	 //ObjectInputStream serverInputStream = new ObjectInputStream(pipe.getInputStream());
	        	 GameState.PlayerToken token = this.gameState.addPlayer();
	        	 
	        	 if (token==null){
	        		 System.out.println("three Players already connected");
	        	 } else {
	        		 System.out.println("writing Object");
	        		 serverOutputStream.writeObject(token);
	        		 serverOutputStream.flush();
	        		 Connection connection = new Connection(serverSocket,gameState);
	        		 connection.start();
	        	 }
	        	 
	         }
	         
	    }  catch(Exception e) {
	    	e.printStackTrace();
	    
	    }
	      
	}

    private synchronized boolean isStopped() {
        return this.isStopped;
    }
    
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		try {
			Server server = new Server();
			server.listen();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub

	}
	private class Connection extends Thread{
		private Socket serverSocket;
		private ObjectOutputStream out;
		
		public Connection(Socket serverSocket, GameState game){
			this.serverSocket = serverSocket;
		}
		
		public void run(){
			
		}
		public void finalize(){
			try {
				this.serverSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		

}
