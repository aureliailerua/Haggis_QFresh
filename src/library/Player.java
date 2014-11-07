/**
 * 
 */
package library;

import java.util.ArrayList;

/**
 * @author andreas.denger
 * This class will be instantiated for each Player in the game.
 * It holds their numbered Cards, Jokers and the points.
 */
public class Player {
	ArrayList<Card> playerCards;
	ArrayList<Card> playerJokers;
	private int playerPoints;
	
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
		this.playerPoints =+ points;
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
}//Player
