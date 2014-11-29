package server;

import java.util.ArrayList;
import java.util.Collections;


import library.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.GameState.PlayerToken;
import library.GameState.State;


/**
 * @author benjamin.indermuehle / andreas.denger
 *
 */
@SuppressWarnings("JavadocReference")
public class GameHandler {
	public GameState gameState;
	private static final Logger log = LogManager.getLogger( GameHandler.class.getName() );
	
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
		if (gameState.getNumPlayers() >=2) {
			gameState.setActivePlayer(PlayerToken.one);
			gameState.setState(GameState.State.running);
			gameState.activeCardDeck = new CardDeck(gameState.getNumPlayers());
		
			for (Player player : gameState.playerList){
				player.setPlayerCards(gameState.activeCardDeck.give14Cards());
				player.setPlayerJokers(gameState.activeCardDeck.give3Jokers());
			}
			gameState.roundList.add(new Round());
			gameState.setActivePattern("");
			gameState.notifyObservers();
			log.debug("starting game");
		}
	}
	public synchronized GameState getGameState(){
		return this.gameState;
	}
	
	/**
	 * @param move
	 * Applies move Object to the GameState Object
	 */
	public synchronized void newMove(Container lvContainer) {
		if ( lvContainer.getToken() == gameState.getActivePlayer() &&
				gameState.getState() == State.running){
			
			ArrayList<Card> lvCards = lvContainer.getPlayCards();
			PlayerToken lvToken = lvContainer.getToken();
						
			if (lvCards.size()==0){
				//this means the player is passing
				gameState.commitMove(lvToken, lvCards);
			} else {
				if (gameState.checkMove(lvCards)){
					gameState.commitMove(lvToken, lvCards);
				} else{
					gameState.rejectMove();
					// TODO:geht das so?!?
				}
			}
			//at this point the move is commited or rejected. 
			//Now for the Round/Tick mechanic....
			if(gameState.checkEndRound() && gameState.checkEndTick()){
				gameState.newRound();
			} else if (gameState.checkEndTick()){
				gameState.newTick();
			} else {
				setNextActivePlayer();
			}
			gameState.notifyObservers();
			//moveEnd
			//	setNextActivePlayer();
			//	gameState.notifyObservers();
		}
	}
	

    //all pattern related checks moved to class Pattern


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
	
	/**
	 * This method sorts an array by CardID using bubbleSort
	 * @param ArrayList<Card> unsorted Cards
	 * @return ArrayList<Card> sorted by cardID
	 */
	public static ArrayList<Card> bubbleSort( ArrayList<Card> cards ) {
		boolean swapFlag = true; 
		while (swapFlag) {
			swapFlag= false;   
			for( int j=0;  j < cards.size()-1;  j++ ){
				if ( cards.get(j).getCardID() > cards.get(j+1).getCardID() ) {
					Collections.swap(cards, j, j+1);
					swapFlag = true;
				}
			} 
		} 
		return cards;
	}

}