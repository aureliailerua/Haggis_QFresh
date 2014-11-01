package library;

public class CardDeckTestDrive {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("***********************");
		System.out.println("****** 2 Players ******");
		System.out.println("***********************");
		
		CardDeck deck2 = new CardDeck();
		deck2.cardDeck = deck2.buildDeck(2);
		System.out.println(deck2.cardDeck.size()+" Cards");
		
		for (Card card : deck2.cardDeck){
			System.out.print("ID: "+card.getCardID());
			System.out.print("   | Rank: "+card.getCardRank());
			System.out.print("   | Point: "+card.getCardPoint());
			System.out.println("   | Point: "+card.getCardSuit());
		}
				
		System.out.println("***********************");
		System.out.println("****** 3 Players ******");
		System.out.println("***********************");
		
		CardDeck deck3 = new CardDeck();
		deck3.cardDeck = deck3.buildDeck(3);
		System.out.println(deck3.cardDeck.size()+" Cards");
		
		for (Card card : deck3.cardDeck){
			System.out.print("ID: "+card.getCardID());
			System.out.print("   | Rank: "+card.getCardRank());
			System.out.print("   | Point: "+card.getCardPoint());
			System.out.println("   | Point: "+card.getCardSuit());
		}
		
	}
}