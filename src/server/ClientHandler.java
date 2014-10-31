package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.GameState;
import library.Move;

/**
 * @author benjamin.indermuehle
 *
 */
/**
 * @author benjamin.indermuehle
 *
 */
public class ClientHandler extends Thread implements Observer {
		private Socket socket;
		private GameState.PlayerToken token;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private GameHandler dealer;
		private static final Logger log = LogManager.getLogger( Server.class.getName() );


		
	public ClientHandler (Socket socket, GameHandler dealer){
		this.socket = socket;
		this.dealer = dealer;

	}
	
	/**
	 * we need to open a connection to the client and give it a token so we can identify it in the future
	 */
	public void initializeCLient() throws IOException{
		log.debug("initializing Client");		
		this.out = new ObjectOutputStream(this.socket.getOutputStream());		
		try {
	
			token = dealer.addPlayer(this);
			
		} catch (MaxPlayerException e){
			System.out.println(e.getMessage());
			this.finalize();
		}
		System.out.println("sending token");
		out.writeObject(token);
		out.flush();
		out.reset();
	}
	
	public void run(){
		try {
			this.in = new ObjectInputStream(this.socket.getInputStream());
			this.readInput();
		}catch(IOException e){
			e.printStackTrace();
			log.debug("Could not establish client connection;");
			this.finalize();
		}
	}
	
	public void finalize(){
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @throws IOException
	 * listen to client input, add the playertoken to the move.
	 * hand move to dealer.
	 */
	public void readInput() throws IOException{
		while (true){
			Move move;
    		try{
    			 move = (Move) this.in.readObject();
    			 log.debug("recieved new Move");
    			 move.setToken(this.token);
    	    	this.dealer.makeMove(move);
    		} catch (ClassNotFoundException e) {
    			//TODO Auto-generated catch block
    			e.printStackTrace();
    			System.exit(1);	
    		}
		}
	}
	
	/**
	 * send GameState to Client 
	 */
	private void sendGameState (){
		try {
		
			GameState gameState = dealer.getGameState();
			out.writeObject(gameState);
			out.flush();
			out.reset();
			
		} catch (IOException e){
			e.printStackTrace();
			this.finalize();
		}
		
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * this method will be called when the GameState object changes
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		log.debug("sending new Gamestate");
		sendGameState();
		
	}
}
