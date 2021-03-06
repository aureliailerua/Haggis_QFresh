package library;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @author andreas.denger
 * This class provides the structure for all cards and 
 * helper-methods to aid in validating moves and comparing Cards
 */
public class Card implements Serializable, Comparable {

	protected int cardID;
	private int cardRank;
	private int cardPoint;
	private String cardSuit;
	public static final String[] SUITS = {"green", "yellow", "grey", "red", "orange", "joker"};
	protected enum Suits { Green, Yellow, Grey, Red, Orange, Joker };
	//public enum[] SuitList = { Suits.Green, Suits.Yellow, Suits.Grey, Suits.Red, Suits.Orange, Suits.Joker };
	
	public Card(int lvCardId, int lvCardRank, String lvCardSuit, int lvCardPoint) {
		//System.out.println(Suits.values().length);
		this.cardID = lvCardId;
		this.cardRank = lvCardRank;
		this.cardSuit = lvCardSuit;
		this.cardPoint = lvCardPoint;
	}
	public int getCardID() {
		return cardID;
	}
	public int getCardRank() {
		return cardRank;
	}
	public int getCardPoint() {
		return cardPoint;
	}
	public String getCardSuit() {
		return cardSuit;
	}

	public String getCardName() {
		return cardSuit + cardRank;
	}
	
	/**
	 * Set the Suit of a card, provided it is in Card.SUITS[].  
	 * @param suit in lowercase-String {"green", "yellow", "grey", "black", "orange", "joker"}
	 * @throws IllegalArgumentException 
	 */
	public void setCardSuit(String s){
		for (String suit : Card.SUITS){
			if(suit.equals(s)) {
				this.cardSuit = s;
				return;
			}
		}
		throw new IllegalArgumentException("illegal cardSuit ... not in SUITS[]");
	}
	
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card card = (Card)obj;
		if (getCardID() == card.getCardID())
			return true;
	return false;
	}
	
	@Override
	public int compareTo(Object o) {
		Card card = (Card)o;
		return getCardID()-card.getCardID();
	}
	
	public static Comparator<Card> CardSuitComparator  = new Comparator<Card>() {
		public int compare(Card card1, Card card2) {
			int suit1 = Arrays.asList(card1.SUITS).indexOf(card1.getCardSuit());
			int suit2 = Arrays.asList(card2.SUITS).indexOf(card2.getCardSuit());
			
			int delta = suit1 - suit2;
			
			if (delta == 0) {
				return card1.compareTo(card2);
			}
			return delta;
		}
	};	
}	