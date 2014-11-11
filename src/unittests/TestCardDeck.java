package unittests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import library.Card;
import library.CardDeck;

/**
 * @author andreas.denger
 * 
 */
public class TestCardDeck {

	@Test
	public void testCardDeckCreation() throws IOException {
		//========================================================
		//create 2-Player deck
		//========================================================
		CardDeck deck2 = new CardDeck(2);
		
		//get number of yellow/joker cards and total points
		int numYellow = 0;
		int numJoker = 0;
		int numPoints = 0;
		for (Card card : deck2.cardDeck) {
			numPoints += card.getCardPoint();
			if (card.getCardSuit() == "yellow") {
				numYellow++;
			} else if (card.getCardSuit() == "joker"){
				numJoker++;
			}
		}
		
		//checks on 2-Player deck
		assertNotNull("2-Player deck created", deck2);
		assertEquals("42 Cards created", 42, deck2.cardDeck.size());
		assertEquals("Total points in deck wrong", 36, numPoints);
		assertEquals("number of yellow cards wrong", 9, numYellow);
		assertEquals("number of joker cards wrong", 6, numJoker);
		assertEquals("specific card wrong", "joker", deck2.cardDeck.get(40).getCardSuit());
		
		//========================================================
		//create 3-Player deck
		//========================================================
		CardDeck deck3 = new CardDeck(3);
	
		//get number of orange/joker cards and total points
		int numOrange = 0;
		numJoker = 0;
		numPoints = 0;
		for (Card card : deck3.cardDeck) {
			numPoints += card.getCardPoint();
			if (card.getCardSuit() == "orange") {
				numOrange++;
			} else if (card.getCardSuit() == "joker"){
				numJoker++;
			}
		}
		
		//checks on 3-Player deck
		assertNotNull("3-Player deck created", deck3);
		assertEquals("54 Cards created", 54, deck3.cardDeck.size());
		assertEquals("Total points in deck wrong", 50, numPoints);
		assertEquals("number of orange cards wrong", 9, numOrange);
		assertEquals("number of joker cards wrong", 9, numJoker);
		assertEquals("specific card wrong", "joker", deck3.cardDeck.get(52).getCardSuit());
	}
}