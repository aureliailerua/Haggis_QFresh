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
import server.Pattern;

/**
 * Created by Riya on 24.11.2014
 */
    public class TestPattern {
    @BeforeClass
    public static void setupBeforeClass() throws Exception {
    }

    @Test
    public void testAnalyzePattern() throws IOException, MaxPlayerException {
        //__Checking the SET pattern___
        CardDeck lvCardDeck = new CardDeck(3);

        ArrayList<Card> setCards = new ArrayList<Card>();
        setCards.add(lvCardDeck.getCardById(135));
        setCards.add(lvCardDeck.getCardById(50));
        setCards.add(lvCardDeck.getCardById(52));
        setCards.add(lvCardDeck.getCardById(53));
            String shouldBe4ofAKind = Pattern.analyzePattern(setCards);
            System.out.println("_________End of first Check - return value from setCards "+shouldBe4ofAKind);

        ArrayList<Card> randomCards = new ArrayList<Card>();
        randomCards.add(lvCardDeck.getCardById(33));
        randomCards.add(lvCardDeck.getCardById(135));
        randomCards.add(lvCardDeck.getCardById(73));
        randomCards.add(lvCardDeck.getCardById(83));
            String shouldBeNoPattern = Pattern.analyzePattern(randomCards);
            System.out.println("_________End of noPattern Check - return value random Cards "+shouldBeNoPattern);

        //__Checking the SEQ pattern____
        ArrayList<Card> seqCards = new ArrayList<Card>();
        seqCards.add(lvCardDeck.getCardById(30));
        seqCards.add(lvCardDeck.getCardById(50));
        seqCards.add(lvCardDeck.getCardById(60));
        seqCards.add(lvCardDeck.getCardById(115));
            String shouldBeSequence = Pattern.analyzePattern(seqCards);
            System.out.println("_________End of second Check - return value from seqCards "+shouldBeSequence);

        //_Checking the run of Pair patterns___
        ArrayList<Card> parSeqCards = new ArrayList<Card>();
        parSeqCards.add(lvCardDeck.getCardById(54));
        parSeqCards.add(lvCardDeck.getCardById(50));
        parSeqCards.add(lvCardDeck.getCardById(30));
        parSeqCards.add(lvCardDeck.getCardById(135));
        parSeqCards.add(lvCardDeck.getCardById(135));
        parSeqCards.add(lvCardDeck.getCardById(40));
            String shouldBeParallelSequence = Pattern.analyzePattern(parSeqCards);
            System.out.println("_________End of third Check - return value from parSeqCards -  Pattern "+shouldBeParallelSequence);

        assertTrue("SET check failed ", shouldBe4ofAKind.equals("fourOfAKind"));
        assertTrue("noPattern check failed ", shouldBeNoPattern == null );
        assertTrue("Sequence check failed ", shouldBeSequence.equals("runOfFourSingles"));
        assertTrue("Parallel Sequence check failed ", shouldBeParallelSequence.equals("runOfThreePairs"));

    }

    @Test
    public void testBooleanCardChecks() throws IOException, MaxPlayerException {
        // create card deck
        CardDeck lvCardDeck = new CardDeck(3);

        //____________Test 1:  Cards are  all same suit but not sameRank or in sequence_____________
        ArrayList<Card> lvCardList = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (c.getCardID() == 30 || c.getCardID() == 50 || c.getCardID() == 90 ) {
                lvCardList.add(c);
            }
        }
        assertTrue("same Suit wrong", Pattern.allSameSuit(lvCardList));
        assertFalse("same Rank wrong", Pattern.allSameRank(lvCardList));
        assertFalse("same Sequence wrong", Pattern.allInSequence(lvCardList, 0 ));

        //____________Test 2: Cards are  all same suit, not sameRank AND in sequence_____________
        ArrayList<Card> lvCardList2 = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (c.getCardID() == 50 || c.getCardID() == 60 || c.getCardID() == 70 ) {
                lvCardList2.add(c);
            }
        }
        // sort required to check in sequence:
        GameHandler.bubbleSort(lvCardList2);
        assertTrue("same Suit wrong", Pattern.allSameSuit(lvCardList2));
        assertFalse("same Rank wrong", Pattern.allSameRank(lvCardList2));
        assertTrue("same Sequence wrong", Pattern.allInSequence(lvCardList2, 0));

        //____________Test 3: Cards are not same suit, but sameRank, (not in sequence, of course) _____________
        ArrayList<Card> lvCardList3 = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (c.getCardID() == 50 ||  c.getCardID() == 52 ||  c.getCardID() == 54 ) {
                lvCardList3.add(c);
            }
        }
        // sort required to check in sequence:
        GameHandler.bubbleSort(lvCardList3);
        assertFalse("same Suit wrong", Pattern.allSameSuit(lvCardList3));
        assertTrue("same Rank wrong", Pattern.allSameRank(lvCardList3));
        assertFalse("same Sequence wrong", Pattern.allInSequence(lvCardList3, 0));

        //_____Preparing Card Combinations: Pair (two Cards Same Rank)
        ArrayList<Card> twoCardsSameRank = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 50 ||
                    c.getCardID() == 54 )
                twoCardsSameRank.add(c);
        }

        //_____Preparing Card Combinations: noPattern (three Cards Same Suit)
        ArrayList<Card> threeCardsSameSuit = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 30 ||
                    c.getCardID() == 50 ||
                    c.getCardID() == 90 )
                threeCardsSameSuit.add(c);
        }

        //_____Preparing Card Combinations: 3 suited Connectors (three Cards, Same Suit, in Sequence)
        ArrayList<Card> threeCardsSameSuitInSequence = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 50 ||
                    c.getCardID() == 60 ||
                    c.getCardID() == 70 )
                threeCardsSameSuitInSequence.add(c);
        }

        //_____Preparing Card Combinations: 4 suited Connectors (four Cards, Same Suit, in Sequence)
        ArrayList<Card> fourCardsSameSuitInSequence = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (    c.getCardID() == 52 ||
                    c.getCardID() == 62 ||
                    c.getCardID() == 72 ||
                    c.getCardID() == 82 )
                fourCardsSameSuitInSequence.add(c);
        }

        //________Checking the setPattern function_________________
        assertTrue("pair check failed ", Pattern.analyzePattern(twoCardsSameRank).equals("pair"));
        assertTrue("runOfThreeSingles check failed ", Pattern.analyzePattern(threeCardsSameSuitInSequence).equals("runOfThreeSingles"));
        assertTrue("runOf4Singles check failed ", Pattern.analyzePattern(fourCardsSameSuitInSequence).equals("runOfFourSingles"));
    }
}