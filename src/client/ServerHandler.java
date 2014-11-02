package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;


import library.GameState;
import library.Container;
import library.GameState.PlayerToken;

public class ServerHandler extends Observable implements Runnable {
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	public GameState.PlayerToken token;
	public GameState gameState;
	private final Object lock = new Object();
	
	public ServerHandler (Socket socket){
		this.gameState = new GameState();
		this.socket = socket;
	}
	
	/**
	 * @throws IOException
	 * open connections and retrieve player token
	 */
	public void initialize() throws IOException{
		
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		System.out.println("client socket established");
		try {
			this.token = (GameState.PlayerToken) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

 
    /**
     * @throws IOException
     * watch input socket for new gamestate object 
     * update the gamestate object
     * notify observers
     */
    private void watchInput() throws IOException{
    	System.out.println("listening for GameState");
  		try{
  			GameState newGameState;
    		while ( (newGameState = (GameState)in.readObject()) != null){        		
    			this.gameState = newGameState;
        		this.setChanged();
        		this.notifyObservers();
    		}
        		
		} catch (ClassNotFoundException e) {
			//TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);	
		} 

    }
    
	public GameState getGameState() {
		synchronized (lock) {
			return gameState;
		}
		
	}
	
	/**
	 * @param move
	 * send move object to the server
	 */
	public void send(Container move){	
		try {
			this.out.writeObject(move);
			this.out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			this.watchInput();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
		// TODO Auto-generated method stub	
	}
}