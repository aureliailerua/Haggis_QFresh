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
	
	public int getcardID() {
		return cardID;
	}

	public String getSuit() {
		return cardSuit;
	}
	//public String getFilename() {
	//	String filename = Integer.toString(cardRank);
	//	String cardFilename = filename + cardSuit;
	//	return cardFilename;
	//}
	
	//public String[] getFilename() {
	//	return (String)cardRank + cardSuit;
	//}
}
