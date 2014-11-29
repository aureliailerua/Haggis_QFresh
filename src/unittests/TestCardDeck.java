package unittests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

import server.GameHandler;
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
		
		//get single cards from deck
		ArrayList<Card> lvSingleCards = new ArrayList<Card>();
		lvSingleCards.add(deck3.getCardById(43));
		lvSingleCards.add(deck3.getCardById(54));
		lvSingleCards.add(deck3.getCardById(60));
		assertEquals("wrong CardById", 43, lvSingleCards.get(0).getCardID());
		assertEquals("wrong CardById", 54, lvSingleCards.get(1).getCardID());
		assertEquals("wrong CardById", 60, lvSingleCards.get(2).getCardID());
		
		//get cardlist from deck
		ArrayList<Card> lvMoreCards = deck3.getCardListById(new int[]{54,43,60});
		//GameHandler.bubbleSort(lvMoreCards);
		Collections.sort(lvMoreCards);
		assertEquals("wrong CardById", 43, lvMoreCards.get(0).getCardID());
		assertEquals("wrong CardById", 54, lvMoreCards.get(1).getCardID());
		assertEquals("wrong CardById", 60, lvMoreCards.get(2).getCardID());	
		
		//
		ArrayList<Card> lvSuitCards = deck3.getCardListById(new int[]{51,60,50});
		//GameHandler.bubbleSort(lvMoreCards);
		Collections.sort(lvSuitCards, Card.CardSuitComparator);
		assertEquals("wrong CardById", 50, lvSuitCards.get(0).getCardID());
		assertEquals("wrong CardById", 60, lvSuitCards.get(1).getCardID());
		assertEquals("wrong CardById", 51, lvSuitCards.get(2).getCardID());	
		
		//checks on 3-Player deck
		assertNotNull("3-Player deck created", deck3);
		assertEquals("54 Cards created", 54, deck3.cardDeck.size());
		assertEquals("Total points in deck wrong", 50, numPoints);
		assertEquals("number of orange cards wrong", 9, numOrange);
		assertEquals("number of joker cards wrong", 9, numJoker);
		assertEquals("specific card wrong", "joker", deck3.cardDeck.get(52).getCardSuit());
	}
}
