/**
 * 
 */
package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.*;

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
         public void testLogic() throws IOException, MaxPlayerException {
        // create carddeck
        CardDeck lvCardDeck = new CardDeck(3);
        ArrayList<Card> lvCardList = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (c.getCardID() == 30 ||
                c.getCardID() == 50 ||
                c.getCardID() == 90
            ) {
                lvCardList.add(c);
                System.out.println(c.getCardSuit());
            }
        }

        int cardCount = lvCardList.size();
        boolean allSameSuit    = GameHandler.allSameSuit(lvCardList);
        boolean allSameRank    = GameHandler.allSameRank(lvCardList);
        boolean allInSequence  = GameHandler.allInSequence(lvCardList);

        assertTrue("same Suit wrong", allSameSuit);
        assertFalse("same Rank wrong", allSameRank);
        assertFalse("same Sequence wrong", allInSequence);

        // sort to be sure
        // GameHandler.bubbleSort(lvCardList);

        // tests


    }


}
