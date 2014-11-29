/**
 * 
 */
package server;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.GameState;
import library.GameState.PlayerToken;

/**
 * @author aurelia.erhardt
 *
 */
public class Tick {
	public ArrayList<Move> moveList;
	private PlayerToken tickWinner;
	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	
	public Tick(){
		log.debug("TICK - new Tick");
		moveList = new ArrayList<Move>();
	}
	
	public Move getActiveMove(){
		return moveList.get(moveList.size()-1);
	}
	
	public boolean checkPass(int numPlayers){
		if (numPlayers==3 && moveList.size() < 2){
			return false;
		} else if (numPlayers==2 && moveList.size() < 1){
			return false;
		} else if (numPlayers==2 && moveList.get(moveList.size()-1).Pass()){
			return true;
		} else if (numPlayers==3 && moveList.get(moveList.size()-2).Pass()	&& moveList.get(moveList.size()-1).Pass()){
			return true;
		} else {
			return false;
		}
	}
	
	public void addMove(Move move){
		this.moveList.add(move);
		log.debug("TICK - move added  -  moveList.size() = "+moveList.size());
	}

	/**
	 * @return the tickWinner
	 */
	public PlayerToken getTickWinner() {
		return tickWinner;
	}

	/**
	 * @param tickWinner the tickWinner to set
	 */
	public void setTickWinner(PlayerToken tickWinner) {
		this.tickWinner = tickWinner;
	}
}