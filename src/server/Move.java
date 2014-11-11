/**
 * 
 */
package server;

import java.util.ArrayList;

import library.Card;
import library.GameState.PlayerToken;

/**
 * @author aurelia.erhardt
 *
 */
public class Move {
	private ArrayList<Card> cardList;
	private PlayerToken movingPlayer;
	
	public Move(){};
	
	public Move(PlayerToken movingPlayer, ArrayList<Card> cardList){
		this.cardList  = cardList;
		this.movingPlayer = movingPlayer;
	}
}
