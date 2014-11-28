package library;

import java.io.Serializable;
import java.util.ArrayList;

import library.GameState.PlayerToken;


/**
 * @author benjamin.indermuehle
 * Simple prototype of a implementation.  
 *
 */
public class Container implements Serializable{
	
	private static final long serialVersionUID = 2245261864373794436L;
	public ArrayList<Card> playCards;
	public boolean playPass; 

	private int addition;
	private PlayerToken token;
	
	public Container(PlayerToken token, ArrayList<Card> cards){
		this.token = token;
		playCards = cards;
	}
	
	public Container(ArrayList<Card> cards){
		playCards = cards;
	}
	
	public Container(boolean pass){
		this.playPass = pass;
	}
	
	public Container(){
	}
	
	public ArrayList<Card> getPlayCards(){
		return playCards;
	}
	
	public int getAddition() {
		return addition;
	}

	public void setAddition(int add) {
		this.addition = add;
	}


	public void setToken(PlayerToken token) {
		this.token = token;
		
	}

	public PlayerToken getToken() {
		return token;
	}
}
