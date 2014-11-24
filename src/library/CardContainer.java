package library;

import java.util.ArrayList;

public class CardContainer extends Container {

	public ArrayList<Card> playCards;
	
	public CardContainer(ArrayList<Card> cards){
		playCards = cards;
	}
	
	public ArrayList<Card> getPlayCards(){
		return playCards;
	}

	
}
