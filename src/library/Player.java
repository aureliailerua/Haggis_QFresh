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
	
	/**
	 * @return Points of Player
	 */
	public int getPlayerPoints() {
		return playerPoints;
	}
	/**
	 * @param set new value of Points
	 */
	public void setPlayerPoints(int points) {
		this.playerPoints = points;
	}
	/**
	 * @param points to add to existing points
	 */
	public void addPoints(int points) {
		this.playerPoints += points;
	}
	/**
	 * @return get playerCards as ArrayList<Card>
	 */
	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}
	/**
	 * @param set playerCards as ArrayList<Card>
	 */
	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}
	/**
	 * @return get playerJokers as ArrayList<Card>
	 */
	public ArrayList<Card> getPlayerJokers() {
		return playerJokers;
	}
	/**
	 * @param set playerJokers as ArrayList<Card>
	 */
	public void setPlayerJokers(ArrayList<Card> playerJokers) {
		this.playerJokers = playerJokers;
	}

	/**
	 * @return the token
	 */
	public PlayerToken getToken() {
		return token;
	}

	/**
	 * @param token the token to set
	 */
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

	/**
	 * @return the playerIsfinished
	 */
	public boolean isFinished() {
		return playerIsfinished;
	}

	/**
	 * @param playerIsfinished the playerIsfinished to set
	 */
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
