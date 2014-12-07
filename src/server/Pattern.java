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
    private static final Logger log = LogManager.getLogger(Server.class.getName());

    //__instance variables__
    public String patternName = null;
    ArrayList<Card> normalCards = new ArrayList<Card>();
    ArrayList<Card> jokerCards = new ArrayList<Card>();
    HashMap<String, ArrayList<Card>> cardsBySuit = new HashMap<String, ArrayList<Card>>(5);
    HashMap<Integer, ArrayList<Card>> cardsByRank = new HashMap<Integer, ArrayList<Card>>(13);
    int cardCount = 0;
    int suitCount = 0;
    int rankCount = 0;
    int lowestRank = 0;
    //int indicatorRunOfSequence = 0;

    //__ Pattern Factory__
    public Pattern(ArrayList<Card> cards) {
        for (Card c : cards) {               //check for Jokers and split the cards accordingly
            if (c.getCardSuit() != "joker") this.normalCards.add(c);
            if (c.getCardSuit() == "joker") this.jokerCards.add(c);
        }
        Collections.sort(this.normalCards);         printCards(this.normalCards);

        Collections.sort(this.jokerCards);          printCards(this.jokerCards);

        this.lowestRank = Collections.min(cards).getCardRank();
        //this.indicatorRunOfSequence = this.isRunOfSequence();
        this.cardCount = cards.size();
        this.setSuitCount();
        log.debug("suitCount is set by Factory as : "+this.suitCount);
        this.setRankCount();
        log.debug("rankCount is set by Factory as : "+this.rankCount);
    }

    public void setSuitCount() {
        //called by factory,  splitting cards by suit in different card lists
        //setting the suitCount and the Hash Map cardsBySuit
        for (Card c : this.normalCards) {
            if (!this.cardsBySuit.containsKey(c.getCardSuit())) {
                this.cardsBySuit.put(c.getCardSuit(), new ArrayList<Card>());
            }
            this.cardsBySuit.get(c.getCardSuit()).add(c);
        }
        this.suitCount = this.cardsBySuit.keySet().size();
    }

    public void setRankCount(){
        //called by factory, splitting cards by rank in different card lists
        //setting the rankCount and the Hash Map cardsByRank
        for (Card c : this.normalCards) {
            if (!this.cardsByRank.containsKey(c.getCardRank())) {
                this.cardsByRank.put(c.getCardRank(), new ArrayList<Card>());
                log.debug("new CardRank added"+c.getCardRank());
            }
            this.cardsByRank.get(c.getCardRank()).add(c);
            log.debug("new Card  added"+c.getCardID());
        }
        this.rankCount = this.cardsByRank.keySet().size();
        log.debug("RankCount is "+this.rankCount);
    }

    public void setPatternName(String lvPatternName){
        this.patternName = lvPatternName;
    }

    public static void printCards(ArrayList<Card> cards ){
        for (Card c : cards) {
            log.debug(" Card Nr. "+ c.getCardID());
        }
    }

    /*
    public static boolean comparePattern (ArrayList<Card> tableCards, ArrayList<Card> newCards, String activePattern ){
        String newPattern = analyzePattern(newCards);
        log.debug(newPattern);
        boolean patternMatch = activePattern.equals(newPattern);
        log.debug(patternMatch);

        return patternMatch;
    }
    */


    public String analyzePattern() {
        this.bombCheck();
        this.setCheck();
        this.sequenceCheck();
        return this.patternName;
    }

    public void bombCheck(){
        if(isBomb(this.normalCards, this.jokerCards)){
            this.setPatternName("bomb");
        }
    }

    public void setCheck(){
        if (this.rankCount == 1) {
            switch (this.cardCount) {
                case 1 : this.setPatternName("single");         break;
                case 2 : this.setPatternName("pair");           break;
                case 3 : this.setPatternName("threeOfAKind");   break;
                case 4 : this.setPatternName("fourOfAKind");    break;
                case 5 : this.setPatternName("fiveOfAKind");    break;
                case 6 : this.setPatternName("SixOfAKind");     break;
                case 7 : this.setPatternName("SevenOfAKind");   break;
            }
        }
    }

    public void sequenceCheck()        {   // when indicatorRunOfSequence smaller or equals 0 the Sequence is not valid, else it corresponds to the suitCount
        int indicatorRunOfSequence = this.isRunOfSequence();
            //__Run of Singles____
            if (indicatorRunOfSequence == 1 ) {
                switch (this.cardCount) {
                    case 3: this.setPatternName("runOfThreeSingles");       break;
                    case 4: this.setPatternName("runOfFourSingles");        break;
                    case 5: this.setPatternName("runOfFiveSingles");        break;
                    case 6: this.setPatternName("runOfSixSingles");         break;
                    case 7: this.setPatternName("runOfSevenSingles");       break;
                    case 8: this.setPatternName("runOfEightSingles");       break;
                    case 9: this.setPatternName("runOfNineSingles");        break;
                    case 10: this.setPatternName("runOfTenSingles");        break;
                    case 11: this.setPatternName("runOfElevenSingles");     break;
                    case 12: this.setPatternName("runOfTwelveSingles");     break;
                    case 13: this.setPatternName("runOfThirteenSingles");   break;
                    case 14: this.setPatternName("runOfFourteenSingles");   break;
                }
            }

            //__Run of Pairs____when suitCount result = 2
            if (indicatorRunOfSequence == 2 ) {
                switch (this.cardCount) {
                    case 4: this.setPatternName("runOfTwoPairs");           break;
                    case 6: this.setPatternName("runOfThreePairs");         break;
                    case 8: this.setPatternName("runOfFourPairs");          break;
                    case 10: this.setPatternName("runOfFivePairs");         break;
                    case 12: this.setPatternName("runOfSixPairs");          break;
                    case 14: this.setPatternName("runOfEightPairs");        break;
                }
            }// end run of pairs

            //__Run of 3 of a Kind  ____when suitCount result = 3
            if (indicatorRunOfSequence == 3 ) {
                switch (this.cardCount) {
                    case 6: this.setPatternName("2ThreeOfAKind");           break;
                    case 9: this.setPatternName("3ThreeOfAKind");           break;
                    case 12: this.setPatternName("4ThreeOfAKind");          break;
                }
            }

            //__Run of 4 of a Kind  ____when suitCount result = 4
            if (indicatorRunOfSequence == 4 ) {
                switch (this.cardCount) {
                    case 8: this.setPatternName("2FourOfAKind");            break;
                    case 12: this.setPatternName("3FourOfAKind");           break;
                }
            }
        }


    /**____ IS RUN OF SEQUENCE____
     * Method checking for all Sequence Patterns, returning an int :
     * 0= no sequence, 1=Run of Singles, 2= Run of Pairs, 3=Run of 3ofAKind, 4= 4oAKind (including jokerCards)
     */
    public int isRunOfSequence () {
        //Collections.sort(this.normalCards);
        int isRunOfSequence ;
        int remainingJokers = this.jokerCards.size();
        /*
        //__splitting cards by suit in different card lists (hash map)
        HashMap<String, ArrayList<Card>> cardSuits = new HashMap<String, ArrayList<Card>>(5);
        for (Card c : this.normalCards) {
            if (!cardSuits.containsKey(c.getCardSuit())) {
                cardSuits.put(c.getCardSuit(), new ArrayList<Card>());
            }
            cardSuits.get(c.getCardSuit()).add(c);
        }
        */

        //__get maximum amount of cards per suit: defines which kind of pattern will be attempted (with jokers)_
        // FIXME if different pattern on table - adjust here (set as instance variable)
        // 2 jokers played with pairs : four of a kind || run of Pairs
        // 2 jokers played with 77 88 : sequence of three pairs - or 2 Three of a Kind etc.
        //
        int amountOfCardsBySuit = 0;
        for (ArrayList<Card> suitedList : this.cardsBySuit.values()) {
            if (suitedList.size() > amountOfCardsBySuit) {
                amountOfCardsBySuit = suitedList.size();
            }
        }
        log.debug("IS RUN OF SEQUENCE : highest amount of Cards by suit "+amountOfCardsBySuit);

        //__Sequence check for each suit__
        for (ArrayList<Card> suitedCards : this.cardsBySuit.values()){
            log.debug("calling inSequence here ");
            remainingJokers = Pattern.inSequence(suitedCards, remainingJokers, this.lowestRank, amountOfCardsBySuit);
        }

        //__setting the return value__
        log.debug("suitCount coming from INSTANCE VAR "+this.suitCount);
        log.debug("remaining Jokers and indicator from Patter.inSequence as int  "+remainingJokers);

        isRunOfSequence = this.suitCount;

        if (remainingJokers < 0) {
            isRunOfSequence = remainingJokers;
            log.debug("if the jokercount was smaller zero is run of Sequence indicator is set here as: "+isRunOfSequence);
        }

       // log.debug("suitCount"+suitCount);
        //log.debug("jokerCount"+jokerCount);
        return isRunOfSequence;
    }



    /**____IN SEQUENCE____
     * int return Check : returns 2, 1, 0 or -1, depending on how many jokers are left at the end of the function (-1 -> false sequence)
     * all in Sequence checks if a regular Sequence is possible including the use of 1 or 2 jokers
     */
    public static int inSequence (ArrayList<Card> cards, int jokerCount, int lowestRank, int amountOfCardsBySuit) {
        //Collections.sort(cards);
        log.debug("inside in Sequence ...");
        int errorTolerance = jokerCount;
        int sequenceBase = lowestRank;
        int i = 0;
        for (Card c : cards) {
                while (c.getCardRank() != sequenceBase + i) {
                    log.debug("gap at Card Nr. " + c.getCardID()+" remaining error Tolerance is "+ errorTolerance);

                    if (errorTolerance <= 0) {
                        return errorTolerance -1 ;
                    }
                    errorTolerance -= 1;
                    log.debug("error Tolerance reduced by one _ now remaining : "+errorTolerance);
                    i++;
                    }
                i++;
        }
        if(cards.size()<amountOfCardsBySuit){ // Sequence has no gap, but is shorter than required - jokers consumed
            int difference = amountOfCardsBySuit - cards.size();
            errorTolerance -= difference;
            log.debug("Sequence too short - difference is "+ difference +" adjusted errorTolerance "+errorTolerance);
        }
        log.debug("in Sequence indicator(OK if not negative)returns : "+errorTolerance);
        return errorTolerance;
    }




    /**____IS BOMB ?____
     * Boolean Check :
     * is Bomb checking 3-5-7-9 suited or all off suit & 2 or 3 jokers played
     */
    public static boolean isBomb ( ArrayList<Card> normalCards, ArrayList<Card> jokerCards) {
        boolean isBomb = true;
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
                case 1 : isBomb = false;    break;
                case 2 : isBomb = true;     break;
                case 3 : isBomb = true;     break;
            }
        } //end of joker bombs
        return isBomb;
    }
}
