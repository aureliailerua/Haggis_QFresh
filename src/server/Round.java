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
public class Round implements Serializable {
	public ArrayList<Tick> tickList;
	private PlayerToken roundWinner;
	private static final Logger log = LogManager.getLogger( Round.class.getName() );
	
	public Tick getActiveTick(){
		return tickList.get(tickList.size()-1);
	}
	
	public void addNewTick(){
		tickList.add(new Tick());
	}
	
	public Round(){
		log.debug("ROUND - new Round");
		tickList = new ArrayList<Tick>();
	}

	public PlayerToken getRoundWinner() {
		return roundWinner;
	}

	public void setRoundWinner(PlayerToken roundWinner) {
		this.roundWinner = roundWinner;
	}
	
}