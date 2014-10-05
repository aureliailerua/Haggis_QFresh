package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import library.Counter;
import library.GameState;
import library.Move;

public class ClientHandler extends Thread implements Observer {
		private Socket socket;
		private GameState.PlayerToken token;
		private ObjectInputStream in;
		private ObjectOutputStream out;
		private GameHandler dealer;
		private Move testMove;
		
	public ClientHandler (Socket socket, GameHandler dealer){
		this.socket = socket;
		this.dealer = dealer;
		this.testMove = new Move();
	}

	
	/**
	 * we need to open a connection to the client and give it a token so we can identify it in the future
	 */
	public void initializeCLient() throws IOException{
		System.out.println("initializing Client");		
		this.out = new ObjectOutputStream(this.socket.getOutputStream());		
		try {
	
			token = dealer.addPlayer(this);
			
		} catch (MaxPlayerException e){
			System.out.println(e.getMessage());
			this.finalize();
		}
		//System.out.println("sending token");
		//out.writeObject(token);
		//out.flush();
	}
	
	public void run(){
		try {
			this.in = new ObjectInputStream(this.socket.getInputStream());
			this.readInput();
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Could not establish client connection;");
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
	
	public void readInput() throws IOException{
		while (true){
			Move move;
    		try{
    			 move = (Move) this.in.readObject();
    			 System.out.println("recieved new Move");
    			 move.setToken(this.token);
    	    	this.dealer.makeMove(move);
    		} catch (ClassNotFoundException e) {
    			//TODO Auto-generated catch block
    			e.printStackTrace();
    			//System.exit(1);	
    		}
		}
	}
	private void sendGameState (){
		try {
		
			GameState gameState = dealer.getGameState();
			out.writeObject(testMove);
			out.flush();
			testMove.setAddition(testMove.getAddition()+1);
			//this.out.writeObject(gameState);
			System.out.println(testMove.getAddition());
			
		} catch (IOException e){
			e.printStackTrace();
			this.finalize();
		}
		
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		System.out.println("sending new Gamestate");
		sendGameState();
		
	}
}
