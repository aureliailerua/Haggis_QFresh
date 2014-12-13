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
    public void testComparePattern() throws IOException, MaxPlayerException {
        CardDeck lvCardDeck = new CardDeck(3);

        //____________Compare 7777 with 8JJJ _____
        ArrayList<Card> fourSeven = new ArrayList<Card>();                        // Standard patterns
        fourSeven.add(lvCardDeck.getCardById(70));
        fourSeven.add(lvCardDeck.getCardById(73));
        fourSeven.add(lvCardDeck.getCardById(72));
        fourSeven.add(lvCardDeck.getCardById(71));
        Pattern tablePattern = new Pattern (fourSeven);
        String basePat_result  =   tablePattern.analyzePattern();
        log.debug("table Pattern results in: "+basePat_result);

        ArrayList<Card> jjj8 = new ArrayList<Card>();
        jjj8.add(lvCardDeck.getCardById(82));
        jjj8.add(lvCardDeck.getCardById(72));
        jjj8.add(lvCardDeck.getCardById(125));
        jjj8.add(lvCardDeck.getCardById(135));
        Pattern incomingPattern = new Pattern(jjj8);
        boolean matchOK = incomingPattern.comparePattern(tablePattern);
        log.debug("match OK ? "+matchOK);
        //______________________________________________________
        assertTrue("7777 vs. 78JJ ", matchOK == false);




        //____________Compare set___four of a kind___
        ArrayList<Card> set4oaK = new ArrayList<Card>();                        // Standard patterns
        set4oaK.add(lvCardDeck.getCardById(135));
        set4oaK.add(lvCardDeck.getCardById(50));
        set4oaK.add(lvCardDeck.getCardById(52));
        set4oaK.add(lvCardDeck.getCardById(53));
            Pattern tablePattern2 = new Pattern (set4oaK);
            String basePat2_result  =   tablePattern2.analyzePattern();
            log.debug("table Pattern results in: "+basePat2_result);

        ArrayList<Card> set4oaK2 = new ArrayList<Card>();
        set4oaK2.add(lvCardDeck.getCardById(64));
        set4oaK2.add(lvCardDeck.getCardById(60));
        set4oaK2.add(lvCardDeck.getCardById(62));
        set4oaK2.add(lvCardDeck.getCardById(63));
            Pattern incomingPattern2 = new Pattern(set4oaK2);
            boolean matchOK2 = incomingPattern2.comparePattern(tablePattern2);
            log.debug("match OK ? "+matchOK2);
        //______________________________________________________
        assertTrue("555J vs 6666 ", matchOK2 == true);


        //Testing Ambivalent Patterns table: 777 888  played: 77 88 JJ
        ArrayList<Card> three7three8 = new ArrayList<Card>();
        three7three8.add(lvCardDeck.getCardById(74));
        three7three8.add(lvCardDeck.getCardById(72));
        three7three8.add(lvCardDeck.getCardById(71));
        three7three8.add(lvCardDeck.getCardById(84));
        three7three8.add(lvCardDeck.getCardById(82));
        three7three8.add(lvCardDeck.getCardById(81));
            Pattern ambiTable = new Pattern(three7three8);
            ambiTable.analyzePattern();
            log.debug("ambiTable should be two 3 of a kind "+ambiTable.patternName);

        ArrayList<Card> two7two8twoJ = new ArrayList<Card>();
        two7two8twoJ.add(lvCardDeck.getCardById(74));
        two7two8twoJ.add(lvCardDeck.getCardById(72));
        two7two8twoJ.add(lvCardDeck.getCardById(82));
        two7two8twoJ.add(lvCardDeck.getCardById(84));
        two7two8twoJ.add(lvCardDeck.getCardById(115));
        two7two8twoJ.add(lvCardDeck.getCardById(125));
            Pattern nextPattern = new Pattern (two7two8twoJ);
            log.debug(nextPattern.analyzePattern()+"-> resulting from analyze 7788JJ");
            boolean ambiOk = nextPattern.comparePattern(ambiTable);
            log.debug("ambi OK ? "+ambiOk+" result ambiTable with newPattern name is "+nextPattern.patternName);
        //______________________________________________________
        assertTrue("777888 vs 7788JJ ", matchOK2 == true);

        //__compare 789 suited with 8JJ__
        ArrayList<Card> runOfThree = new ArrayList<Card>();
        runOfThree.add(lvCardDeck.getCardById(72));
        runOfThree.add(lvCardDeck.getCardById(82));
        runOfThree.add(lvCardDeck.getCardById(92));
            Pattern j = new Pattern(runOfThree);
            String j_result = j.analyzePattern();
            log.debug("_________compare 789 suited with 8JJ first pattern :"+j_result);
        ArrayList<Card> twoJoker8 = new ArrayList<Card>();
        twoJoker8.add(lvCardDeck.getCardById(115));
        twoJoker8.add(lvCardDeck.getCardById(125));
        twoJoker8.add(lvCardDeck.getCardById(81));
            Pattern i = new Pattern(twoJoker8);
            String i_result = i.analyzePattern();
            log.debug("_________compare 789 suited with 8JJ second pattern :"+i_result);
            boolean result = i.comparePattern(j);
            log.debug("result of compare 789 with 8JJ" +result);
        //______________________________________________________
        assertTrue("777888 vs 7788JJ ", result == true);


    }

    @Test
    public void testAnalyzePattern() throws IOException, MaxPlayerException {

        //__Checking the SET pattern___
        CardDeck lvCardDeck = new CardDeck(3);

        ArrayList<Card> unsuitedSequenceBomb = new ArrayList<Card>();
        unsuitedSequenceBomb.add(lvCardDeck.getCardById(30));
        unsuitedSequenceBomb.add(lvCardDeck.getCardById(51));
        unsuitedSequenceBomb.add(lvCardDeck.getCardById(72));
        unsuitedSequenceBomb.add(lvCardDeck.getCardById(93));
        Pattern z  = new Pattern (unsuitedSequenceBomb);
        String z_result =   z.analyzePattern();
        log.debug("__ b Pattern result : "+z_result);
        log.debug("_________End of Z Check - return value from unsuited Sequence Bomb : "+z_result);

        ArrayList<Card> bombCards = new ArrayList<Card>();
        bombCards.add(lvCardDeck.getCardById(115));
        bombCards.add(lvCardDeck.getCardById(125));
        bombCards.add(lvCardDeck.getCardById(135));
            Pattern a = new Pattern (bombCards);

            String a_result  =   a.analyzePattern();
            log.debug("__a Pattern result : "+a_result);
            log.debug("_________End of A Check - return value from Joker Bomb : "+a_result);

        ArrayList<Card> bombSequenceCards = new ArrayList<Card>();
        bombSequenceCards.add(lvCardDeck.getCardById(30));
        bombSequenceCards.add(lvCardDeck.getCardById(50));
        bombSequenceCards.add(lvCardDeck.getCardById(70));
        bombSequenceCards.add(lvCardDeck.getCardById(90));
            Pattern b  = new Pattern (bombSequenceCards);
            String b_result =   b.analyzePattern();
            log.debug("__ b Pattern result : "+b_result);
            log.debug("_________End of B Check - return value from Sequence Bomb : "+b_result);

        ArrayList<Card> setCards = new ArrayList<Card>();
        setCards.add(lvCardDeck.getCardById(135));
        setCards.add(lvCardDeck.getCardById(50));
        setCards.add(lvCardDeck.getCardById(52));
        setCards.add(lvCardDeck.getCardById(53));
            Pattern c = new Pattern(setCards);
            String c_result = c.analyzePattern();
            log.debug("_________End of C Check - return value from setCards 4oaK:  "+c_result);


        ArrayList<Card> randomCards = new ArrayList<Card>();
        randomCards.add(lvCardDeck.getCardById(32));
        randomCards.add(lvCardDeck.getCardById(135));
        randomCards.add(lvCardDeck.getCardById(73));
        randomCards.add(lvCardDeck.getCardById(83));
            Pattern d = new Pattern(randomCards);
            String d_result = d.analyzePattern();
            log.debug("_________End of D Check - return value random Cards "+d_result);

        //__Checking the SEQ pattern____
        ArrayList<Card> seqCards = new ArrayList<Card>();
        seqCards.add(lvCardDeck.getCardById(30));
        seqCards.add(lvCardDeck.getCardById(50));
        seqCards.add(lvCardDeck.getCardById(60));
        seqCards.add(lvCardDeck.getCardById(115));
            Pattern e = new Pattern(seqCards);
            String e_result = e.analyzePattern();
            log.debug("_________End of E Check - return value from seqCards "+e_result);

        //_Checking the run of Pair patterns___
        ArrayList<Card> parSeqCards = new ArrayList<Card>();
        parSeqCards.add(lvCardDeck.getCardById(54));
        parSeqCards.add(lvCardDeck.getCardById(50));
        parSeqCards.add(lvCardDeck.getCardById(30));
        parSeqCards.add(lvCardDeck.getCardById(44));
        parSeqCards.add(lvCardDeck.getCardById(34));
        parSeqCards.add(lvCardDeck.getCardById(40));
            Pattern f = new Pattern(parSeqCards);
            String f_result = f.analyzePattern();
            log.debug("_________End of F Check - return value from parallel sequence Cards run of three pairs:  "+f_result);


        //_Checking the run of Pair patterns___
        ArrayList<Card> notSequenceRun = new ArrayList<Card>();
        notSequenceRun.add(lvCardDeck.getCardById(64));
        notSequenceRun.add(lvCardDeck.getCardById(50));
        notSequenceRun.add(lvCardDeck.getCardById(115));
        notSequenceRun.add(lvCardDeck.getCardById(125));
        notSequenceRun.add(lvCardDeck.getCardById(34));
        notSequenceRun.add(lvCardDeck.getCardById(40));
            Pattern g = new Pattern(notSequenceRun);
            String g_result = g.analyzePattern();
            log.debug("_________End of G Check - return value from parSeqNoJokersCards -  Pattern should be null now sequence base is not the same - "+g_result);


        ArrayList<Card> twoJokerCards = new ArrayList<Card>();
        twoJokerCards.add(lvCardDeck.getCardById(115));
        twoJokerCards.add(lvCardDeck.getCardById(125));
            Pattern h = new Pattern(twoJokerCards);
            String h_result = h.analyzePattern();
            log.debug("_________End of H Check - r return value from two Joker Bomb :"+h_result);

        //_________________________
        ArrayList<Card> twoJoker8 = new ArrayList<Card>();
        twoJoker8.add(lvCardDeck.getCardById(115));
        twoJoker8.add(lvCardDeck.getCardById(125));
        twoJoker8.add(lvCardDeck.getCardById(81));
        Pattern i = new Pattern(twoJoker8);
            String i_result = i.analyzePattern();
            log.debug("_________End of I Check - r return value from runOfThree singles :"+i_result);

        ArrayList<Card> runOfThree = new ArrayList<Card>();
        runOfThree.add(lvCardDeck.getCardById(72));
        runOfThree.add(lvCardDeck.getCardById(82));
        runOfThree.add(lvCardDeck.getCardById(92));
        Pattern j = new Pattern(runOfThree);
        String j_result = j.analyzePattern();

        log.debug("_________End of J Check - 789 :"+j_result);


        //_________________________
        assertTrue("unsuitedBomb check failed ", z_result.equals("unsuitedBomb"));
        assertTrue("suitedBomb check failed ", a_result.equals("JQKBomb"));
        assertTrue("Sequence suitedBomb check failed ", b_result.equals("suitedBomb"));
        assertTrue("fourOfAKind check failed ", c_result.equals("fourOfAKind"));
        assertTrue("noPattern check failed ", d_result.equals("nix"));
        assertTrue("Sequence check failed ", e_result.equals("runOfFourSingles"));
        assertTrue("runOfThreePairs check failed ", f_result.equals("runOfThreePairs"));
        assertTrue("nix check failed 3.5 check ", g_result.equals("nix"));
        assertTrue("JQBomb check failed ", h_result.equals("JQBomb"));
        assertTrue("threeOfAKind failed ", i_result.equals("threeOfAKind"));
        assertTrue("runOfThreeSingles failed ", j_result.equals("runOfThreeSingles"));


    }
}