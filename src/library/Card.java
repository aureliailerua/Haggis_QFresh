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
	
	public int getCardID() {
		return cardID;
	}
	public void setCardID(int cardID) {
		this.cardID = cardID;
	}
	public int getCardRank() {
		return cardRank;
	}
	public void setCardRank(int cardRank) {
		this.cardRank = cardRank;
	}
	public int getCardPoint() {
		return cardPoint;
	}
	public void setCardPoint(int cardPoint) {
		this.cardPoint = cardPoint;
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
	
	public String getCardSuit() {
		return cardSuit;
	}
}	

