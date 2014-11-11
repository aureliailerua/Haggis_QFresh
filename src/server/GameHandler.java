package server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
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
@SuppressWarnings("JavadocReference")
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




    public static boolean allSameSuit (ArrayList<Card> cards) {
        boolean sameSuit = true;
        String comparableSuit = cards.get(0).getCardSuit();
        for (Card c : cards) {
            if (!c.getCardSuit().equals(comparableSuit)) {
                sameSuit = false;
                break;
            }
        }
        return sameSuit;
    }


    public static boolean allSameRank (ArrayList<Card> cards){
        boolean sameRank = true;
        int comparableRank = cards.get(0).getCardRank();
        for (Card c : cards) {
            if (c.getCardRank() != comparableRank) {
                sameRank = false;
                break;
            }
        }
        return sameRank;
    }

    public static boolean allInSequence (ArrayList<Card> cards){
        boolean inSequence = true;
        int sequenceBase = cards.get(0).getCardRank();
        int i = 0;
        for (Card c : cards) {
            if (c.getCardRank()   != sequenceBase + i) {
                inSequence = false;
                i++;
                break;
            }
        }
        return inSequence;
    }

     //the pattern might be set in the gamestate - as current pattern
    public enum Pattern  { single, pair, threeOfAKind, fourOfAKind, fiveOfAKind, sixOfAKind,
        runOfThreeSingles, runOfFiveSingles, runOfTwoPairs, runOfThreePairs, runOfTwoOfAKind };
     public Pattern currentPattern;

    public void getPattern(ArrayList<Card> cards) {
        int cardCount = cards.size();
        boolean allSameSuit = allSameSuit(cards);
        boolean allSameRank = allSameRank(cards);
        boolean allInSequence = allInSequence(cards);

        //depending on Card count, checks could be defined accordingly

        //__________Set Patterns___________________________________
        if (cardCount == 1)                      currentPattern = Pattern.single;
        if (cardCount == 2 && allSameRank)       currentPattern = Pattern.pair;
        if (cardCount == 3 && allSameRank)       currentPattern = Pattern.threeOfAKind;
        if (cardCount == 4 && allSameRank)       currentPattern = Pattern.fourOfAKind;
        if (cardCount == 5 && allSameRank)       currentPattern = Pattern.fiveOfAKind;
        if (cardCount == 6 && allSameRank)       currentPattern = Pattern.sixOfAKind;

        //__________Sequence Patterns___________________________________

        if (cardCount == 3 && allSameSuit && allInSequence)      currentPattern = Pattern.runOfThreeSingles;
        if (cardCount == 5 && allSameSuit && allInSequence)      currentPattern = Pattern.runOfFiveSingles;

        //__________Combo Patterns___________________________________
        // need own checkMethods - run of Two Pairs - run of Three Pairs etc..-
        //return Pattern; or set Pattern.Pair in the Card Container instance Variables.
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