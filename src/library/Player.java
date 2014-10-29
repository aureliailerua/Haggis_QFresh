/**
 * 
 */
package library;

import java.util.ArrayList;

/**
 * @author andreas.denger
 *
 */
public class Player {
	ArrayList<Card> playerCards;
	ArrayList<Card> playerJokers;
	private int points;
	
	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	/**
	 * @param points the points to set
	 */
	public void addPoints(int points) {
		this.points =+ points;
	}
	/**
	 * @return the playerCards
	 */
	public ArrayList<Card> getPlayerCards() {
		return playerCards;
	}
	/**
	 * @param playerCards the playerCards to set
	 */
	public void setPlayerCards(ArrayList<Card> playerCards) {
		this.playerCards = playerCards;
	}
	/**
	 * @return the playerJokers
	 */
	public ArrayList<Card> getPlayerJokers() {
		return playerJokers;
	}
	/**
	 * @param playerJokers the playerJokers to set
	 */
	public void setPlayerJokers(ArrayList<Card> playerJokers) {
		this.playerJokers = playerJokers;
	}
}
