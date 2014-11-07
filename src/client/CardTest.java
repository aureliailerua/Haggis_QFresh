package client;

public class CardTest {

	int cardID;
	int cardRank;
	String cardSuit;
	int cardPoint;
	
	public CardTest(int ID, int rank, int point, String suit) {
		cardID = ID;
		cardRank = rank;
		cardPoint = point;
		cardSuit = suit;
	}
	public String getCardName() {
		return cardSuit + cardRank;
	}
	
	public int getcardID() {
		return cardID;
	}

	public int getcardRank() {
		return cardRank;
	}
	
	public String getSuit() {
		return cardSuit;
	}
	
	public String getName() {
		return cardSuit + cardRank;
	}
}
