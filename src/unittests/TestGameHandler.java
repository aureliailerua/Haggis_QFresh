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
         public void testBooleanCardChecks() throws IOException, MaxPlayerException {
            // create carddeck
            CardDeck lvCardDeck = new CardDeck(3);

            //____________Test 1:  Cards are  all same suit but not sameRank or in sequence_____________
            ArrayList<Card> lvCardList = new ArrayList<Card>();
                for (Card c : lvCardDeck.cardDeck) {
                    if (c.getCardID() == 30 ||
                        c.getCardID() == 50 ||
                        c.getCardID() == 90
                    ) {
                        lvCardList.add(c);
                    }
                }
                assertTrue("same Suit wrong",       GameHandler.allSameSuit(lvCardList));
                assertFalse("same Rank wrong",      GameHandler.allSameRank(lvCardList));
                assertFalse("same Sequence wrong",  GameHandler.allInSequence(lvCardList));

            //____________Test 2: Cards are  all same suit, not sameRank AND in sequence_____________
                ArrayList<Card> lvCardList2 = new ArrayList<Card>();
                for (Card c : lvCardDeck.cardDeck) {
                    if (    c.getCardID() == 50   ||
                            c.getCardID() == 60 ||
                            c.getCardID() == 70
                            ) {
                        lvCardList2.add(c);
                    }
                }
                // sort required to check in sequence:
                GameHandler.bubbleSort(lvCardList2);
                assertTrue("same Suit wrong",       GameHandler.allSameSuit(lvCardList2));
                assertFalse("same Rank wrong",      GameHandler.allSameRank(lvCardList2));
                assertTrue("same Sequence wrong",   GameHandler.allInSequence(lvCardList2));

            //____________Test 3: Cards are not same suit, but sameRank, (not in sequence, of course) _____________
                ArrayList<Card> lvCardList3 = new ArrayList<Card>();
                for (Card c : lvCardDeck.cardDeck) {
                    if (    c.getCardID() == 50   ||
                            c.getCardID() == 52 ||
                            c.getCardID() == 54
                            ) {
                        lvCardList3.add(c);
                    }
                }
                // sort required to check in sequence:
                GameHandler.bubbleSort(lvCardList3);
                assertFalse("same Suit wrong",      GameHandler.allSameSuit(lvCardList3));
                assertTrue("same Rank wrong",       GameHandler.allSameRank(lvCardList3));
                assertFalse("same Sequence wrong",  GameHandler.allInSequence(lvCardList3));




    }


    @Test
    public void testSetPattern() throws IOException, MaxPlayerException {
        GameHandler lvGameHandler = new GameHandler();
        CardDeck lvCardDeck = new CardDeck(3);
        System.out.println("see default GameHandler pattern  "+ lvGameHandler.currentPattern);

        //_____Preparing Card Combinations: Pair (two Cards Same Rank)
        ArrayList<Card> twoCardsSameRank = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 50 ||
                    c.getCardID() == 54
                    ) {
                twoCardsSameRank.add(c);
            }
        }

        //_____Preparing Card Combinations: noPattern (three Cards Same Suit)
        ArrayList<Card> threeCardsSameSuit = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 30 ||
                    c.getCardID() == 50 ||
                    c.getCardID() == 90
                    ) {
                threeCardsSameSuit.add(c);
            }
        }

        //_____Preparing Card Combinations: 3 suited Connectors (three Cards, Same Suit, in Sequence)
        ArrayList<Card> threeCardsSameSuitInSequence = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 50 ||
                    c.getCardID() == 60 ||
                    c.getCardID() == 70
                    ) {
                threeCardsSameSuitInSequence.add(c);
            }
        }

        //_____Preparing Card Combinations: 4 suited Connectors (four Cards, Same Suit, in Sequence)
        ArrayList<Card> fourCardsSameSuitInSequence = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 50 ||
                    c.getCardID() == 60 ||
                    c.getCardID() == 70 ||
                    c.getCardID() == 80
                    ) {
                fourCardsSameSuitInSequence.add(c);
            }
        }


        //________Checking the funcion setPattern_________________
        lvGameHandler.setPattern(twoCardsSameRank);
        System.out.println("pattern Pair ? "+lvGameHandler.currentPattern);
        assertTrue("pair check failed ", GameHandler.Pattern.pair == lvGameHandler.currentPattern);

        lvGameHandler.setPattern(threeCardsSameSuit);
        System.out.println(" No pattern ? "+lvGameHandler.currentPattern);
        assertTrue("noPattern check failed ", GameHandler.Pattern.noPattern == lvGameHandler.currentPattern);

        lvGameHandler.setPattern(threeCardsSameSuitInSequence);
        System.out.println(" 3 suited Connectors ? "+lvGameHandler.currentPattern);
        assertTrue("runOfThreeSingles check failed ", GameHandler.Pattern.runOfThreeSingles == lvGameHandler.currentPattern);

    }
}
