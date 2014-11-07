package library;

public class CardTestDrive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Card firstcard = new Card(5, 8, Card.SUITS[3], 7 % 2);
		
		System.out.println("ID : "+firstcard.getCardID());
		System.out.println("Point : "+firstcard.getCardPoint());
		System.out.println("Rank : "+firstcard.getCardRank());
		System.out.println("Suit : "+firstcard.getCardSuit());
	}
}