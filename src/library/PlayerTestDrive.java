/**
 * 
 */
package library;

/**
 * @author andreas.denger
 *
 */
public class PlayerTestDrive {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int numPlayers = 3;
		
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		
		CardDeck deck3 = new CardDeck();
		deck3.cardDeck = deck3.buildDeck(numPlayers);
		
		System.out.println("Cards generated : "+deck3.cardDeck.size());
		
		player1.setPlayerCards(deck3.give14Cards());
				
		player2.setPlayerCards(deck3.give14Cards());
		
		player3.setPlayerCards(deck3.give14Cards());
		
		
		player1.setPlayerJokers(deck3.give3Jokers());
		
		player2.setPlayerJokers(deck3.give3Jokers());
		
		player3.setPlayerJokers(deck3.give3Jokers());
		
		System.out.println("************************");
		System.out.println("******* Player 1 *******");
		System.out.println("************************");
		for (Card card : player1.playerCards){
			System.out.print("ID: "+card.getCardID());
			System.out.print("   | Rank: "+card.getCardRank());
			System.out.print("   | Point: "+card.getCardPoint());
			System.out.println("   | Point: "+card.getCardSuit());
		}
		
		System.out.println("************************");
		System.out.println("******* Player 2 *******");
		System.out.println("************************");
		for (Card card : player2.playerCards){
			System.out.print("ID: "+card.getCardID());
			System.out.print("   | Rank: "+card.getCardRank());
			System.out.print("   | Point: "+card.getCardPoint());
			System.out.println("   | Point: "+card.getCardSuit());
		}
		
		System.out.println("************************");
		System.out.println("******* Player 3 *******");
		System.out.println("************************");
		for (Card card : player3.playerCards){
			System.out.print("ID: "+card.getCardID());
			System.out.print("   | Rank: "+card.getCardRank());
			System.out.print("   | Point: "+card.getCardPoint());
			System.out.println("   | Point: "+card.getCardSuit());
		}
		
		System.out.println("******* cards left in deck *******");
		for (Card card : deck3.cardDeck){
			System.out.print("ID: "+card.getCardID());
			System.out.print("   | Rank: "+card.getCardRank());
			System.out.print("   | Point: "+card.getCardPoint());
			System.out.println("   | Point: "+card.getCardSuit());
		}
	}
}
