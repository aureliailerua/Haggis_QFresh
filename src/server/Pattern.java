package server;

/**
 * Created by Riya on 17.11.2014.
 */
import library.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.util.Arrays;
import java.awt.*;

public class Pattern {
    public String patternName;
    private static final Logger log = LogManager.getLogger(Server.class.getName());

    //__Constructors__
    public Pattern() {
    }
    public Pattern (String lvPatternName) {
        this.patternName = lvPatternName;
    }

    public void setPatternName(String lvPatternName){
        this.patternName = lvPatternName;
    }

    public boolean comparePattern (ArrayList<Card> tableCards, ArrayList<Card> newCards, String activePattern ){
        return true;
    }
    
    public static String analyzePattern(ArrayList<Card> cards) {
        Pattern thePattern = new Pattern();

        //check for Jokers and split the cards accordingly
        ArrayList<Card> normalCards = new ArrayList<Card>();
        ArrayList<Card> jokerCards = new ArrayList<Card>();
        for (Card c : cards) {
            if (c.getCardSuit() != "joker") normalCards.add(c);
            if (c.getCardSuit() == "joker") jokerCards.add(c);
        }
        //setting local variables__
        int cardCount = normalCards.size() + jokerCards.size();

        //__bomb Check__
        if(isBomb(normalCards, jokerCards)){
            thePattern.setPatternName("bomb");
            return thePattern.patternName;
        }

        //______ Set Check______
        if (allSameRank(normalCards)) {
            switch (cardCount) {
                    case 1: thePattern.setPatternName("single");
                        return thePattern.patternName;
                    case 2: thePattern.setPatternName("pair");
                        return thePattern.patternName;
                    case 3: thePattern.setPatternName("threeOfAKind");
                        return thePattern.patternName;
                    case 4: thePattern.setPatternName("fourOfAKind");
                        return thePattern.patternName;
                    case 5:thePattern.setPatternName("fiveOfAKind");
                        return thePattern.patternName;
                    case 6: thePattern.setPatternName("SixOfAKind");
                        return thePattern.patternName;
                    case 7: thePattern.setPatternName("SevenOfAKind");
                        return thePattern.patternName;
            }
        } // end of Set Check

        //______Sequence Check_____
        int suitCount = isRunOfSequence(normalCards, jokerCards); // when suitCount smaller or equals 0 the Sequence is not valid

        //__Run of Singles____when suitCount result = 1
        if (suitCount == 1 ) {
            switch (cardCount) {
                 case 3: thePattern.setPatternName("runOfThreeSingles");
                     return thePattern.patternName;
                 case 4: thePattern.setPatternName("runOfFourSingles");
                     return thePattern.patternName;
                 case 5: thePattern.setPatternName("runOfFiveSingles");
                     return thePattern.patternName;
                 case 6: thePattern.setPatternName("runOfSixSingles");
                     return thePattern.patternName;
                 case 7: thePattern.setPatternName("runOfSevenSingles");
                     return thePattern.patternName;
                 case 8: thePattern.setPatternName("runOfEightSingles");
                     return thePattern.patternName;
                 case 9: thePattern.setPatternName("runOfNineSingles");
                     return thePattern.patternName;
                 case 10: thePattern.setPatternName("runOfTenSingles");
                     return thePattern.patternName;
                 case 11: thePattern.setPatternName("runOfElevenSingles");
                     return thePattern.patternName;
                 case 12: thePattern.setPatternName("runOfTwelveSingles");
                     return thePattern.patternName;
                 case 13: thePattern.setPatternName("runOfThirteenSingles");
                     return thePattern.patternName;
                 case 14: thePattern.setPatternName("runOfFourteenSingles");
                     return thePattern.patternName;
            }
        }// end run of singles

        //__Run of Pairs____when suitCount result = 2
        if (suitCount == 2 ) {
            switch (cardCount) {
                case 4: thePattern.setPatternName("runOfTwoPairs");
                    return thePattern.patternName;
                case 6: thePattern.setPatternName("runOfThreePairs");
                    return thePattern.patternName;
                case 8: thePattern.setPatternName("runOfFourPairs");
                    return thePattern.patternName;
                case 10: thePattern.setPatternName("runOfFivePairs");
                    return thePattern.patternName;
                case 12: thePattern.setPatternName("runOfSixPairs");
                    return thePattern.patternName;
                case 14: thePattern.setPatternName("runOfEightPairs");
                    return thePattern.patternName;
            }
        }// end run of pairs

        //__Run of 3 of a Kind  ____when suitCount result = 3
        if (suitCount == 3 ) {
            switch (cardCount) {
                case 6: thePattern.setPatternName("2ThreeOfAKind");
                    return thePattern.patternName;
                case 9: thePattern.setPatternName("3ThreeOfAKind");
                    return thePattern.patternName;
                case 12: thePattern.setPatternName("4ThreeOfAKind");
                    return thePattern.patternName;
            }
        }// end run of 3 of a Kind

        //__Run of 4 of a Kind  ____when suitCount result = 4
        if (suitCount == 4 ) {
            switch (cardCount) {
                case 8: thePattern.setPatternName("2FourOfAKind");
                    return thePattern.patternName;
                case 12: thePattern.setPatternName("3FourOfAKind");
                    return thePattern.patternName;
            }
        }// end run of 4 of a Kind

        return thePattern.patternName;
    } //end analyze Pattern

    /**
     * Method checking for all Sequence Patterns, returning an int :
     * 0= no sequence, 1=Run of Singles, 2= Run of Pairs, 3=Run of 3ofAKind, 4= 4oAKind (including jokerCards)
     */
    public static int isRunOfSequence (ArrayList<Card> normalCards, ArrayList<Card> jokerCards) {
        Collections.sort(normalCards);
        int isRunOfSequence ;
        int jokerCount = jokerCards.size();
        int lowestRank = Collections.min(normalCards).getCardRank();

        log.debug("jokerCount at beginning "+jokerCount);
        log.debug(lowestRank+" lowest Rank");

        //__splitting cards by suit in different card lists (hash map)
        HashMap<String, ArrayList<Card>> cardSuits = new HashMap<String, ArrayList<Card>>(5);
        for (Card c : normalCards) {
            if (!cardSuits.containsKey(c.getCardSuit())) {
                cardSuits.put(c.getCardSuit(), new ArrayList<Card>());
            }
            cardSuits.get(c.getCardSuit()).add(c);
        }

        //__get maximum amount of cards per suit: defines which kind of pattern will be attempted (with jokers)_ FIXME if different pattern on table - adjust here
        int amountOfCardsBySuit = 0;
        for (ArrayList<Card> cardList : cardSuits.values()) {
            log.debug(cardList.size()+" cards of suit "+ cardList.get(0).getCardSuit());
            if (cardList.size() > amountOfCardsBySuit) {
                amountOfCardsBySuit = cardList.size();
            }
        }
        log.debug("highest amount of Cards "+amountOfCardsBySuit);

        //__Sequence check for each suit__
        for (ArrayList<Card> cardList : cardSuits.values()){
            log.debug("Sequence Check for Suit: "+cardList.get(0).getCardSuit()+" remaining Jokers:"+jokerCount);
            jokerCount = Pattern.inSequence(cardList, jokerCount, lowestRank, amountOfCardsBySuit);
        }

        //__setting the return value__
        int suitCount = cardSuits.keySet().size();
        isRunOfSequence = suitCount;
        if (jokerCount < 0) {
            isRunOfSequence = jokerCount;
        }

        log.debug("suitCount"+suitCount);
        log.debug("jokerCount"+jokerCount);
        return isRunOfSequence;
    }

    /**
     * int return Check : returns 2, 1, 0 or -1, depending on how many jokers are left at the end of the function (-1 -> false sequence)
     * all in Sequence checks if a regular Sequence is possible including the use of 1 or 2 jokers
     */
    public static int inSequence (ArrayList<Card> cards, int jokerCount, int lowestRank, int amountOfCardsBySuit) {
        Collections.sort(cards);
        int errorTolerance = jokerCount;
        int sequenceBase = lowestRank;
        int i = 0;
        for (Card c : cards) {
                while (c.getCardRank() != sequenceBase + i) {
                    System.out.println("gap at Card Nr. " + c.getCardID());
                    if (errorTolerance <= 0) {
                        return errorTolerance -1 ;
                    }
                    errorTolerance -= 1;
                    i++;
                    }
                i++;
        }
        if(cards.size()<amountOfCardsBySuit){ // Sequence has no gap, but is shorter than required - jokers consumed
            int difference = amountOfCardsBySuit - cards.size();
            errorTolerance -= difference;
            System.out.println("Sequence too short - difference is "+ difference +" adjusted errorTolerance "+errorTolerance);
        }
        System.out.println("return value"+errorTolerance);
        return errorTolerance;
    }

    /**
     * Boolean Check :
     * is Bomb checking 3-5-7-9 suited or all off suit & 2 or 3 jokers played
     */
    public static boolean isBomb ( ArrayList<Card> normalCards, ArrayList<Card> jokerCards) {
        boolean isBomb = true;
        System.out.print("xxx to is bomb");
        if (normalCards.size() > 0 && jokerCards.size() > 0 ){
            isBomb = false;
            return isBomb;
        }

        //__bomb sequence of 3-5-7-9
        if(normalCards.size() > 0  ){
            int lowestRank = normalCards.get(0).getCardRank();
            if (normalCards.get(0).getCardRank() == 3 && normalCards.size() ==4 ){
                for (int i = 0; i < normalCards.size(); i++) {
                    if (normalCards.get(i).getCardRank() != lowestRank + i * 2) {
                        isBomb = false;
                    }
                }

                //____splitting cards by suit in different card lists to count suits
                HashMap<String, ArrayList<Card>> cardSuits = new HashMap<String, ArrayList<Card>>(5);
                for (Card c : normalCards) {
                    if (!cardSuits.containsKey(c.getCardSuit())) {
                        cardSuits.put(c.getCardSuit(), new ArrayList<Card>());
                    }
                    cardSuits.get(c.getCardSuit()).add(c);
                }
                //____if Sequence is ok, cards must be all suited or all different suit
                int suitCount = cardSuits.keySet().size();
                if (suitCount == 2 || suitCount == 3){
                    isBomb = false;
                }
            }
            else {
                isBomb = false;
            }
            return isBomb;
        } //end of bomb sequence 3-5-7-9

        //__joker bombs__
        if(jokerCards.size() > 0   ){
            switch (jokerCards.size()){
                case 1 : isBomb = false;
                case 2 : isBomb = true;
                case 3 : isBomb = true;
            }
        } //end of joker bombs
        return isBomb;
    }// end of isBomb


    /**
     * Boolean Check :
     * all same suit
     */
    public static boolean allSameSuit (ArrayList<Card> cards) {
        boolean sameSuit = true;
        String comparableSuit = cards.get(0).getCardSuit();
        for (Card c : cards) {
            if (!c.getCardSuit().equals(comparableSuit)) {
                sameSuit = false;
                break;
            }
        }
        return sameSuit;
    }

    /**
     * Boolean Check :
     * all same rank
     */
    public static boolean allSameRank(ArrayList<Card> normalCards){
        boolean sameRank = true;
        int comparableRank = normalCards.get(0).getCardRank();
        for (Card c : normalCards) {
            if (c.getCardRank() != comparableRank) {
                sameRank = false;
                break;
            }
        }
        return sameRank;
    }
}
