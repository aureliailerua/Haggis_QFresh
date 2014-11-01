package library;

/**
 * @author andreas.denger
 * This class provides the structure for all cards and will eventually
 * provide some helper-methods to aid in validating moves and comparing Cards
 */
public class Card {

	protected int cardID;
	private int cardRank;
	private int cardPoint;
	private String cardSuit;
	protected static final String[] SUITS = {"green", "yellow", "grey", "black", "orange", "joker"};
	protected enum Suits { Green, Yellow, Grey, Black, Orange, Joker };
	//public enum[] SuitList = { Suits.Green, Suits.Yellow, Suits.Grey, Suits.Black, Suits.Orange, Suits.Joker };
	
	public Card(int lvCardId, int lvCardRank, String lvCardSuit, int lvCardPoint) {
		//System.out.println(Suits.values().length);
		this.cardID = lvCardId;
		this.cardRank = lvCardRank;
		this.cardSuit = lvCardSuit;
		this.cardPoint = cardRank % 2;
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
	
	/**
	 * Set the Suit of a card, provided it is in SUITS[].  
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
	

}	

