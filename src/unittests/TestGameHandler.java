/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import library.Card;
import library.CardDeck;
import org.junit.BeforeClass;
import org.junit.Test;

import server.GameHandler;
import server.MaxPlayerException;

/**
 * @author andreas.denger
 *
 */
public class TestGameHandler {
	@BeforeClass
	public static void setupBeforeClass() throws Exception {

	}

	@Test
	public void testPlayerCreation() throws IOException, MaxPlayerException {
		// create carddeck
		CardDeck lvCardDeck = new CardDeck(3);

		// create cardlist for test
		ArrayList<Card> lvCardList = new ArrayList<Card>();
		lvCardList.add(lvCardDeck.cardDeck.get(0));
		lvCardList.add(lvCardDeck.cardDeck.get(0));
		lvCardList.add(lvCardDeck.cardDeck.get(0));

		// sort to be sure
		GameHandler.bubbleSort(lvCardList);

		// tests

	}
}
