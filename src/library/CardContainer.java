package library;

import java.util.ArrayList;

import library.GameState.PlayerToken;


/**
 * @author benjamin.indermuehle, andreas.denger
 * used to ship player actions from client to server
 * ArrayList<Card> is empty when player passes
 */
public class CardContainer extends Container{
	
	private static final long serialVersionUID = 2245261864373794436L;
	public ArrayList<Card> playCards;
	public boolean playPass; 

	private int addition;
	private PlayerToken token;
	
	public CardContainer(PlayerToken token, ArrayList<Card> cards){
		this.token = token;
		playCards = cards;
	}
	
	public CardContainer(ArrayList<Card> cards){
		playCards = cards;
	}
	
	public CardContainer(boolean pass){
		this.playPass = pass;
	}
	
	public CardContainer(){
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
