/**
 * 
 */
package server;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.GameState.PlayerToken;

/**
 * @author aurelia.erhardt
 *
 */
public class Round {
	public ArrayList<Tick> tickList;
	private PlayerToken roundWinner;
	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	
	public Tick getActiveTick(){
		return tickList.get(tickList.size()-1);
	}
	
	public void addNewTick(){
		tickList.add(new Tick());
	}
	
	public Round(){
		log.debug("ROUND - new Round");
		tickList = new ArrayList<Tick>();
		addNewTick();
	}

	/**
	 * @return the roundWinner
	 */
	public PlayerToken getRoundWinner() {
		return roundWinner;
	}

	/**
	 * @param roundWinner the roundWinner to set
	 */
	public void setRoundWinner(PlayerToken roundWinner) {
		this.roundWinner = roundWinner;
	}
	
}