package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

import library.Card;
import library.CardContainer;
import library.CardDeck;
import library.GameState;
import library.GameState.PlayerToken;
import library.GameState.State;
import library.Container;
import library.Player;

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
		if (gameState.getNumPlayers() >=2) {
			gameState.setActivePlayer(PlayerToken.one);
			gameState.setState(GameState.State.running);
			gameState.notifyObservers();
			System.out.println("starting game");
			gameState.activeCardDeck = new CardDeck(gameState.getNumPlayers());
			
			for (Player player : gameState.playerList){
				player.setPlayerCards(gameState.activeCardDeck.give14Cards());
				player.setPlayerJokers(gameState.activeCardDeck.give3Jokers());
			}
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
	
	
	public void checkMove(CardContainer lvContainer) {
		
		ArrayList<Card> cards = lvContainer.getPlayCards();
		// rough evaluation of the move
		int cardCount = cards.size();
		int minimum = 14;
		for (Card c : cards ) {
			minimum = Math.min (minimum,  c.getCardRank());		
		}
		//if minimum <= round.tick.Cards.getLowestCard();
		//if cards.size()<= round.tick.Cards.size();
		
		
		
		// fine-grain evaluation of the move
		//int[] suitCount = {0,0,0,0,0,0};
		
		/*
		int[] rankCount = new int[14]; 
		for (Card c : cards ) {
			rankCount[c.getCardRank()]++;
		}*/ 
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