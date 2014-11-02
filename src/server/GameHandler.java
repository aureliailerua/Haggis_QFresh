package server;

import java.util.Observable;

import library.GameState;
import library.GameState.PlayerToken;
import library.GameState.State;
import library.Container;

/**
 * @author benjamin.indermuehle
 *
 */
/**
 * @author benjamin.indermuehle
 *
 */
/**
 * @author benjamin.indermuehle
 *
 */
/**
 * @author benjamin.indermuehle
 *
 */
public class GameHandler {
	
	private GameState gameState;
	
	public GameHandler(){
		
		this.gameState = new GameState();
	}
	
	/**
	 * FIXME some more logic will be needed here to start the game when enough players are connected. 
	 * 
	 */
	public PlayerToken addPlayer(ClientHandler client) throws MaxPlayerException{ 
		PlayerToken token = gameState.addPlayer();
		gameState.addObserver(client);
		return token;
	}

	/**
	 * checks if enough players are joined and starts the game.
	 * #FIXME this function should be renamed
	 */
	private void startGame() {
		if (gameState.getNumPlayers() >=2){
			gameState.setActivePlayer(PlayerToken.one);
			gameState.setState(GameState.State.running);
			gameState.notifyObservers();
			System.out.println("starting game");
		}
		
	}
	public synchronized GameState getGameState(){
		return this.gameState;
	}
	
	/**
	 * @param move
	 * Applies move Object to the GameState Object
	 */
	public synchronized void makeMove(Container move) {
		if ( move.getToken() == gameState.getActivePlayer() &&
				gameState.getState() == State.running){
			if( move.makeMove(gameState)){
				setNextActivePlayer();
				gameState.notifyObservers();
			}	
		}
	}

	private boolean setNextActivePlayer() {

		if (gameState.getActivePlayer() == PlayerToken.one){
			gameState.setActivePlayer( PlayerToken.two);
			return true;
		}
		if ( gameState.getActivePlayer() == PlayerToken.two
				&& gameState.getNumPlayers() > 2){
			gameState.setActivePlayer(PlayerToken.three);
			return true;	
		}
		gameState.setActivePlayer(PlayerToken.one);
		return true;
	}

	/**
	 * this function will be called after player initialization is finished to check if game
	 * should be started
	 */
	public void playerAdded() {
		System.out.println("player Added");
		startGame();
	}
}