/**
 * 
 */
package library;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.GameState.PlayerToken;

/**
 * @author andreas.denger
 * This class will be instantiated for each Player in the game.
 * It holds their numbered Cards, Jokers and the points.
 * Also holds flag if player is out of cards.
 */
public class Player implements Serializable, Comparable {
	ArrayList<Card> playerCards;
	ArrayList<Card> playerJokers;
	private int playerPoints;
	private PlayerToken token;
	private boolean playerIsfinished = false;
	
	private static final Logger log = LogManager.getLogger( Player.class.getName() );
	
	public Player(PlayerToken lvToken){
		this.setToken(lvToken);
	}
	
	public int getPlayerPoints() {
		return playerPoints;
	}

	public void setPlayerPoints(int points) {
		this.playerPoints = points;
	}

	public void addPoints(int points) {
		this.playerPoints += points;
	}

	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}

	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}

	public ArrayList<Card> getPlayerJokers() {
		return playerJokers;
	}

	public void setPlayerJokers(ArrayList<Card> playerJokers) {
		this.playerJokers = playerJokers;
	}

	public PlayerToken getToken() {
		return token;
	}

	public void setToken(PlayerToken token) {
		this.token = token;
	}

	/**
	 * @param ArrayList<Card> to remove from Player
	 */
	public void removeCardsFromPlayer(ArrayList<Card> lvCards){
		for (Card lvRemoveCard : lvCards){
			if (playerCards.remove(lvRemoveCard)){
				log.debug("PLAYER "+this.getToken()+"- card removed  ID: "+lvRemoveCard.getCardID());
			}
		}
		for (Card lvRemoveCard : lvCards){
			if (playerJokers.remove(lvRemoveCard)){
				log.debug("PLAYER "+this.getToken()+"- Joker removed  ID: "+lvRemoveCard.getCardID());
			}
		}
	}

	public boolean isFinished() {
		return playerIsfinished;
	}

	public void setPlayerIsfinished(boolean playerIsfinished) {
		this.playerIsfinished = playerIsfinished;
	}
	
	/**
	 * compare for points (felicita.acklin)
	 */
	@Override
	public int compareTo(Object o) {
		Player player = (Player)o;
		return getPlayerPoints()-player.getPlayerPoints();
	}
}//Player
