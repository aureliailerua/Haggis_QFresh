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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import server.GameHandler;
import server.MaxPlayerException;
import server.Pattern;
import server.Server;

/**
 * Created by Riya on 24.11.2014
 */
    public class TestPattern {
    private static final Logger log = LogManager.getLogger(Server.class.getName());

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
            log.debug("_________End of first Check - return value from setCards "+shouldBe4ofAKind);

        ArrayList<Card> randomCards = new ArrayList<Card>();
        randomCards.add(lvCardDeck.getCardById(32));
        randomCards.add(lvCardDeck.getCardById(135));
        randomCards.add(lvCardDeck.getCardById(73));
        randomCards.add(lvCardDeck.getCardById(83));
            String shouldBeNoPattern = Pattern.analyzePattern(randomCards);
            log.debug("_________End of noPattern Check - return value random Cards "+shouldBeNoPattern);

        //__Checking the SEQ pattern____
        ArrayList<Card> seqCards = new ArrayList<Card>();
        seqCards.add(lvCardDeck.getCardById(30));
        seqCards.add(lvCardDeck.getCardById(50));
        seqCards.add(lvCardDeck.getCardById(60));
        seqCards.add(lvCardDeck.getCardById(115));
            String shouldBeSequence = Pattern.analyzePattern(seqCards);
            log.debug("_________End of second Check - return value from seqCards "+shouldBeSequence);

        //_Checking the run of Pair patterns___
        ArrayList<Card> parSeqCards = new ArrayList<Card>();
        parSeqCards.add(lvCardDeck.getCardById(54));
        parSeqCards.add(lvCardDeck.getCardById(50));
        parSeqCards.add(lvCardDeck.getCardById(30));
        parSeqCards.add(lvCardDeck.getCardById(44));
        parSeqCards.add(lvCardDeck.getCardById(34));
        parSeqCards.add(lvCardDeck.getCardById(40));
            String shouldBeParallelSequence = Pattern.analyzePattern(parSeqCards);
            log.debug("_________End of third Check - return value from parSeqCards -Pattern: "+shouldBeParallelSequence);

        //_Checking the run of Pair patterns___
        ArrayList<Card> notSequenceRun = new ArrayList<Card>();
        notSequenceRun.add(lvCardDeck.getCardById(64));
        notSequenceRun.add(lvCardDeck.getCardById(50));
        notSequenceRun.add(lvCardDeck.getCardById(115));
        notSequenceRun.add(lvCardDeck.getCardById(125));
        notSequenceRun.add(lvCardDeck.getCardById(34));
        notSequenceRun.add(lvCardDeck.getCardById(40));
        String shouldBeNoSequenceRun = Pattern.analyzePattern(notSequenceRun);
        log.debug("_________End of 3.5th Check - return value from parSeqNoJokersCards -  Pattern should be null now sequence base is not the same - "+shouldBeNoSequenceRun);



        ArrayList<Card> bombCards = new ArrayList<Card>();
        bombCards.add(lvCardDeck.getCardById(115));
        bombCards.add(lvCardDeck.getCardById(125));
        bombCards.add(lvCardDeck.getCardById(135));
            String shouldBeBomb = Pattern.analyzePattern(bombCards);
            log.debug("_________End of fourth Check - return value from Joker Bomb : "+shouldBeBomb);




        ArrayList<Card> bombSequenceCards = new ArrayList<Card>();
        bombSequenceCards.add(lvCardDeck.getCardById(30));
        bombSequenceCards.add(lvCardDeck.getCardById(50));
        bombSequenceCards.add(lvCardDeck.getCardById(70));
        bombSequenceCards.add(lvCardDeck.getCardById(90));
           String shouldBeSequenceBomb = Pattern.analyzePattern(bombSequenceCards);
            log.debug("_________End of fifth Check - return value from Sequence Bomb : "+shouldBeSequenceBomb);

        ArrayList<Card> twoJokerCards = new ArrayList<Card>();
        twoJokerCards.add(lvCardDeck.getCardById(115));
        twoJokerCards.add(lvCardDeck.getCardById(125));
            String shouldBeTwoJokerBomb = Pattern.analyzePattern(twoJokerCards);
            log.debug("_________End of sixth Check - return value from two Joker Bomb : "+shouldBeTwoJokerBomb);


        assertTrue("SET check failed ", shouldBe4ofAKind.equals("fourOfAKind"));
        assertTrue("noPattern check failed ", shouldBeNoPattern == null );
        assertTrue("Sequence check failed ", shouldBeSequence.equals("runOfFourSingles"));
        assertTrue("Parallel Sequence check failed ", shouldBeParallelSequence.equals("runOfThreePairs"));
        assertTrue("shouldBeNoSequenceRun failed 3.5 check ", shouldBeNoSequenceRun == null);
        assertTrue("Joker Bomb check failed ", shouldBeBomb.equals("bomb"));
        assertTrue("Sequence Bomb check failed ", shouldBeSequenceBomb.equals("bomb"));
        assertTrue("Two Joker Bomb check failed ", shouldBeTwoJokerBomb.equals("bomb"));
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


        //____________Test 2: Cards are  all same suit, not sameRank AND in sequence_____________
        ArrayList<Card> lvCardList2 = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (c.getCardID() == 50 || c.getCardID() == 60 || c.getCardID() == 70 ) {
                lvCardList2.add(c);
            }
        }
        // sort required to check in sequence:
        Collections.sort(lvCardList2);
        assertTrue("same Suit wrong", Pattern.allSameSuit(lvCardList2));
        assertFalse("same Rank wrong", Pattern.allSameRank(lvCardList2));


        //____________Test 3: Cards are not same suit, but sameRank, (not in sequence, of course) _____________
        ArrayList<Card> lvCardList3 = new ArrayList<Card>();
        for (Card c : lvCardDeck.cardDeck) {
            if (c.getCardID() == 50 ||  c.getCardID() == 52 ||  c.getCardID() == 54 ) {
                lvCardList3.add(c);
            }
        }
        // sort required to check in sequence:
        Collections.sort(lvCardList3);
        assertFalse("same Suit wrong", Pattern.allSameSuit(lvCardList3));
        assertTrue("same Rank wrong", Pattern.allSameRank(lvCardList3));


        ArrayList<Card> twoCardsSameRank = new ArrayList<Card>();
        twoCardsSameRank.add(lvCardDeck.getCardById(50));
        twoCardsSameRank.add(lvCardDeck.getCardById(54));
        String onePair = Pattern.analyzePattern(twoCardsSameRank);
        System.out.println("should be a pair: "+ onePair);



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
        System.out.println("save the pair !!!! "+Pattern.allSameRank(twoCardsSameRank));

        //assertTrue("pair check failed ", onePair.equals("pair"));
        assertTrue("runOfThreeSingles check failed ", Pattern.analyzePattern(threeCardsSameSuitInSequence).equals("runOfThreeSingles"));
        assertTrue("runOf4Singles check failed ", Pattern.analyzePattern(fourCardsSameSuitInSequence).equals("runOfFourSingles"));
    }
}