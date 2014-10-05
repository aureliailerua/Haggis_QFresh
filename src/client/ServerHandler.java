package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Observable;


import library.GameState;
import library.Move;
import library.GameState.PlayerToken;

public class ServerHandler extends Observable implements Runnable {
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	public GameState.PlayerToken token;
	public GameState gameState;
	private boolean isStopped = false;
	private final Object lock = new Object();
	private Move testmove;
	
	public ServerHandler (Socket socket){
		this.gameState = new GameState();
		this.socket = socket;
	}
	
	public void initialize() throws IOException{
		
		out = new ObjectOutputStream(socket.getOutputStream());
		token = PlayerToken.one;
		System.out.println("client socket established");
		//try {
		//	this.token = (GameState.PlayerToken) in.readObject();
		//} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
	//	}
	}

    
	private synchronized boolean isStopped() {
        return this.isStopped;
    }
    private void watchInput() throws IOException{
		in = new ObjectInputStream(socket.getInputStream());
    	System.out.println("listening for GameState");
    	while (!this.isStopped( )){
    		try{
    			//GameState newGameState = (GameState) this.in.readObject();
    			testmove = (Move) in.readObject();
    			System.out.println(testmove.getAddition());
    			//int counter =(Integer) this.in.readObject();
    			//System.out.println(counter);
    			//System.out.println("recieved new GameState");
    		//	System.out.println(newGameState.getNumber());
        		//this.gameState = newGameState;
        		this.setChanged();
        		this.notifyObservers();
        		
    		} catch (ClassNotFoundException e) {
    			//TODO Auto-generated catch block
    			e.printStackTrace();
    			//System.exit(1);	
    		} 
    	}
    }
    
	public GameState getGameState() {
		synchronized (lock) {
			return gameState;
		}
		
	}
	
	public void send(Move move){	
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
