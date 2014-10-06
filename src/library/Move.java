package library;

import java.io.Serializable;

import library.GameState.PlayerToken;


/**
 * @author benjamin.indermuehle
 * Simple prototype of a implementation.  
 *
 */
public class Move implements Serializable{
	
	private static final long serialVersionUID = 2245261864373794436L;

	private int addition;
	private PlayerToken token;
	
	public int getAddition() {
		return addition;
	}

	public void setAddition(int add) {
		this.addition = add;
	}

	public Move(){
		
	}

	public void setToken(PlayerToken token) {
		this.token = token;
		
	}

	public PlayerToken getToken() {
		return token;
	}

	public boolean makeMove(GameState gameState) {
		gameState.setNumber(gameState.getNumber()+addition);
		System.out.printf("GameState Number is now %d\n",gameState.getNumber());
		return true;
		
	}

}
