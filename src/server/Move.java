/**
 * 
 */
package server;

import java.io.Serializable;
import java.util.ArrayList;




import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.GameState.PlayerToken;


/**
 * @author aurelia.erhardt, andreas.denger
 *
 */
public class Move implements Serializable {
	private ArrayList<Card> cardList;
	private PlayerToken movingPlayer;
	private boolean Pass;
	private static final Logger log = LogManager.getLogger( Move.class.getName() );
	
	public Move(PlayerToken movingPlayer, ArrayList<Card> cardList){
		if (cardList.size() == 0){
			this.Pass = true;
		} else { 
			this.Pass = false;
		}
		log.debug("MOVE - new Move  -  cardList.size() = "+cardList.size()
						+"  -  movingPlayer = "+ movingPlayer
						+"  -  Pass = "+this.Pass);
		this.cardList = cardList;
		this.movingPlayer = movingPlayer;

		
	}

	public PlayerToken getMovingPlayer() {
		return movingPlayer;
	}

	public boolean Pass() {
		return Pass;
	}
	public ArrayList<Card> getCardList(){
		return cardList;
	}
}
