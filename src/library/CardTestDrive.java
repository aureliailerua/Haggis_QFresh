package library;

public class CardTestDrive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		Card firstcard = new Card();
		
		firstcard.setCardID(1);
		firstcard.setCardPoint(2);
		firstcard.setCardRank(7);
		firstcard.setCardSuit("yellow");
		
		System.out.println("ID : "+firstcard.getCardID());
		System.out.println("Point : "+firstcard.getCardPoint());
		System.out.println("Rank : "+firstcard.getCardRank());
		System.out.println("Suit : "+firstcard.getCardSuit());
	}
}
