package server;

/**
 * Created by Riya on 17.11.2014.
 */
import library.Card;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


public class Pattern {
    //public enum PatternName {noPattern, single, pair, threeOfAKind, fourOfAKind, fiveOfAKind, sixOfAKind}
    public String patternName;

    //__Constructors__
    public Pattern() {
    }
    public Pattern (String lvPatternName) {
        this.patternName = lvPatternName;
    }

    public void setPatternName(String lvPatternName){
        this.patternName = lvPatternName;
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
        int jokerCount = jokerCards.size();

        boolean allSameRank = allSameRank(normalCards);                     //System.out.println("allSameRank "+allSameRank);
        int cardCount = normalCards.size() + jokerCards.size();
        if (allSameRank){
                //______ Set Check______
                switch (cardCount) {
                    case 1: thePattern.setPatternName("single");            break;
                    case 2: thePattern.setPatternName("pair");              break;
                    case 3: thePattern.setPatternName("threeOfAKind");      break;
                    case 4: thePattern.setPatternName("fourOfAKind");       break;
                    case 5: thePattern.setPatternName("fiveOfAKind");       break;
                    case 6: thePattern.setPatternName("SixOfAKind");        break;
                }
        }
        else{ //if allSameRank is false
                //_________Sequence Check_____
                boolean allSameSuit = allSameSuit(normalCards);             //System.out.println("allSameSuit "+allSameSuit);
                boolean allInSequence;
                if (allSameSuit) {
                    allInSequence = allInSequence(normalCards, jokerCount); //System.out.println("allInSequence "+allInSequence);

                    if (allSameSuit && allInSequence) {
                        switch (cardCount) {
                            case 3: thePattern.setPatternName("runOfThreeSingles");                    break;
                            case 4: thePattern.setPatternName("runOfFourSingles");                     break;
                            case 5: thePattern.setPatternName("runOfFiveSingles");                     break;
                            case 6: thePattern.setPatternName("runOfSixSingles");                      break;
                            case 7: thePattern.setPatternName("runOfSevenSingles");                    break;
                            case 8: thePattern.setPatternName("runOfEightSingles");                    break;
                        }
                    }
                }
                else { //if allSameSuit is false
                    //______Run of Pairs Check_____
                    boolean isRunOfPairs = isRunOfPairs(normalCards, jokerCards);   //System.out.println("isRunOfPairs "+isRunOfPairs);
                    if (isRunOfPairs) {
                        switch (cardCount) {
                            case 4: thePattern.setPatternName("runOfTwoPairs");                        break;
                            case 6: thePattern.setPatternName("runOfThreePairs");                      break;
                            case 8: thePattern.setPatternName("runOfFourPairs");                       break;
                        }
                    }
                }
        }
        System.out.println("in analyzePattern : "+thePattern.patternName);
        return thePattern.patternName;
    }

    /**
     * Boolean Check :
     * checks for runs of pairs via two scenarios - with or without jokerCards
     */
    public static boolean isRunOfPairs (ArrayList<Card> normalCards, ArrayList<Card> jokerCards) {
        boolean isRunOfPairs = false;
        //sort Cards and split Cards into two separate lists
        GameHandler.bubbleSort(normalCards);
       //___Splitting Cards by suit___
        ArrayList<Card> yinSuitCards = new ArrayList<Card>();
        ArrayList<Card> yangSuitCards = new ArrayList<Card>();
        String yinSuit = normalCards.get(0).getCardSuit();
        String yangSuit = null;
        for (Card c : normalCards) {
            if (c.getCardSuit().equals(yinSuit)) {
                yinSuitCards.add(c);
            } else {
                if (yangSuitCards.size() == 0) {
                    yangSuit = c.getCardSuit();
                    yangSuitCards.add(c);
                } else {
                    if (c.getCardSuit().equals(yangSuit)) {
                        yangSuitCards.add(c);
                    } else {
                        System.out.println("third suit detected");
                        isRunOfPairs = false;
                        break;
                    }
                }

            }
        } //__end of splitting cards by suit

        System.out.println("cards after splitting by suit");
        System.out.println("jokerCards");
        for (Card c : jokerCards){System.out.println(c.getCardID());}
        System.out.println("yin Cards");
        for (Card c : yinSuitCards){System.out.println(c.getCardID());}
        System.out.println("yang Cards");
        for (Card c : yangSuitCards){System.out.println(c.getCardID());}

        boolean sameSuitYin = allSameSuit(yinSuitCards);
        boolean sameSuitYang = allSameSuit(yangSuitCards);

        if (jokerCards.size() == 0 ) {                  //simple version without jokers
            boolean inSequenceOdds = allInSequence(yinSuitCards, 0);
            boolean inSequenceEvens = allInSequence(yangSuitCards, 0);

            if (inSequenceOdds && inSequenceEvens && sameSuitYin && sameSuitYang) {
                isRunOfPairs = true;
            }
        }
        else {                                          //extended version with jokers
            boolean allInParallelSequence = allInParallelSequence(yinSuitCards, yangSuitCards, jokerCards);
                if (allInParallelSequence && sameSuitYin && sameSuitYang) {
                    isRunOfPairs = true;
                }
        }
        return isRunOfPairs;
    }

    /**
     * Boolean Check :
     * all in Sequence checks if a regular Sequence is possible including the use of 1 or 2 jokers
     */
    public static boolean allInSequence (ArrayList<Card> cards, int jokerCount){
        GameHandler.bubbleSort(cards);
        boolean inSequence = true;
        int sequenceBase = cards.get(0).getCardRank();
        int i = 0;
        for (Card c : cards) {
            if (c.getCardRank()   != sequenceBase + i) {
                if(jokerCount == 0) {  System.out.println("simple sequence inside first if break at "+c.getCardID());
                    inSequence = false;               break;
                }
                else { //if there are jokers..
                    if (c.getCardRank() == sequenceBase + i + 1) {
                        System.out.println("1 Joker used " + c.getCardID());
                        jokerCount = -1;
                        i++;
                    } else {
                        if (jokerCount == 2 && c.getCardRank() == sequenceBase + i + 2) {
                            System.out.println("2 Jokers used " + c.getCardID());
                            jokerCount = -2;
                            i = +2;
                        } else {
                            inSequence = false;
                            break;
                        }
                    }
                }
            }
            i++;
        }
        return inSequence;
    }

    /**
     * Boolean Check :
     * all in parallel Sequence checks if a run of pairs is possible including the use of 1 or 2 jokers
     */
    public static boolean allInParallelSequence (ArrayList<Card> yinCards, ArrayList<Card> yangCards, ArrayList<Card> jokerCards){
        boolean inParallelSequence= true;
        int jokerCount = jokerCards.size();
        int sequenceBase = Math.min(yinCards.get(0).getCardRank(), yangCards.get(0).getCardRank());
        int i = 0;
        for (Card c : yinCards) {
            System.out.println("Next Card "+c.getCardID()+" Jokers left "+ jokerCount);

            if (c.getCardRank()   != sequenceBase + i) {
                if(jokerCount <= 0) {   System.out.println("inside if yinCards - condition failed - break");
                    inParallelSequence = false;               break;
                }
                else { //if there are jokers..
                    if (c.getCardRank() == sequenceBase + i + 1) {
                        System.out.println("1 Joker used at yinCard " + c.getCardID());
                        jokerCount-- ;
                        i++;
                    } else {
                        if (jokerCount >= 2 && c.getCardRank() == sequenceBase + i + 2) {
                            System.out.println("2 Jokers used at yinCard " + c.getCardID());
                            jokerCount = jokerCount -2;
                            i = i+2;
                        } else { System.out.println("last else in yinCards - condition failed - break");
                            inParallelSequence = false;
                            break;
                        }
                    }
                }
            }
            i++;
        }

        //Reset the counter for yangCards
        i = 0;
        for (Card c : yangCards) {
            System.out.println("Next Card "+c.getCardID()+" Jokers left "+ jokerCount);
            if (c.getCardRank()   != sequenceBase + i) {
                if(jokerCount <= 0) { System.out.println("inside if yangCards - condition failed - break");
                    inParallelSequence = false;               break;
                }
                else { //if there are jokers..
                    if (c.getCardRank() == sequenceBase + i + 1) {
                        System.out.println("1 Joker used at yangCard " + c.getCardID());

                        jokerCount-- ;
                        System.out.println("Jokers left"+ jokerCount);
                        i++;
                    } else {
                        if (jokerCount >= 2 && c.getCardRank() == sequenceBase + i + 2) {
                            System.out.println("2 Jokers used at yangCard " + c.getCardID());
                            jokerCount = jokerCount-2;
                            System.out.println("Jokers left "+ jokerCount);
                            i = i+2;
                        } else { System.out.println("last else in yangCards - condition failed - break");
                            inParallelSequence = false;
                            break;
                        }
                    }
                }
            }
            i++;
        }
        return inParallelSequence;
    }

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
