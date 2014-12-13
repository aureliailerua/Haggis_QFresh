package server;
/**
 * Created by Riya on 17.11.2014.
 */
import library.Card;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Pattern implements Serializable{
    private static final Logger log = LogManager.getLogger(Pattern.class.getName());

    //__instance variables__
        public String patternName = "nix";
        ArrayList<Card> normalCards = new ArrayList<Card>();
        ArrayList<Card> jokerCards = new ArrayList<Card>();
        HashMap<String, ArrayList<Card>> cardsBySuit = new HashMap<String, ArrayList<Card>>(5);
        HashMap<Integer, ArrayList<Card>> cardsByRank = new HashMap<Integer, ArrayList<Card>>(13);
        int cardCount = 0;
        int suitCount = 0;
        int rankCount = 0;
        int lowestRank = 0;
        int highestRank = 0;
        int maxSequenceLength = 0;
        int maxSequenceDepth= 0;


    //__ Pattern Factory__
    public Pattern(ArrayList<Card> cards) {
        for (Card c : cards) {
            if ( ! c.getCardSuit().equals("joker")) this.normalCards.add(c);
            if ( c.getCardSuit().equals("joker")) this.jokerCards.add(c);
        }
        Collections.sort(this.normalCards);         printCards(this.normalCards);
        Collections.sort(this.jokerCards);          printCards(this.jokerCards);
        if (normalCards.size()>0){
            this.lowestRank = Collections.min(normalCards).getCardRank();
            this.highestRank = Collections.max(normalCards).getCardRank();
            this.maxSequenceLength = (this.highestRank - this.lowestRank +1);
        }

        this.cardCount = cards.size();
        this.setSuitCount();
        this.setRankCount();

            log.debug("suitCount is set by Factory as : "+this.suitCount);
            log.debug("rankCount is set by Factory as : "+this.rankCount);
            log.debug("maxSequenceLength is set by Factory as : highest card "+this.highestRank+" minus lowest"+this.lowestRank+" plus one equals"+this.maxSequenceLength);
    }


    public void setSuitCount() {
        for (Card c : this.normalCards) {
            if (!this.cardsBySuit.containsKey(c.getCardSuit())) {
                this.cardsBySuit.put(c.getCardSuit(), new ArrayList<Card>());
            }
            this.cardsBySuit.get(c.getCardSuit()).add(c);
        }
        this.suitCount = this.cardsBySuit.keySet().size();
    }

    public void setRankCount(){
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

    public boolean comparePatternTEMP (Pattern tablePattern ) {
        boolean levelingOK;
        boolean patternMatch = false;

        //__naturally matching patterns are detected here__
        this.analyzePattern();                                                 log.debug("incoming Cards analyzed result naturally in "+this.patternName);
        if(tablePattern.lowestRank < this.lowestRank){  levelingOK = true;     log.debug("leveling ok ? "+levelingOK);}
        if(this.patternName.equals(tablePattern.patternName) && tablePattern.lowestRank < this.lowestRank) {
            patternMatch = true;
            return patternMatch;
        }
        return patternMatch;
    }



    public boolean comparePattern (Pattern tablePattern ){
                boolean levelingOK;
        boolean patternMatch = false;

        //__naturally matching patterns are detected here__
        this.analyzePattern();                                                 log.debug("incoming Cards analyzed result naturally in "+this.patternName);
        if(tablePattern.lowestRank < this.lowestRank){  levelingOK = true;     log.debug("leveling ok ? "+levelingOK);}
        if(this.patternName.equals(tablePattern.patternName) && tablePattern.lowestRank < this.lowestRank){
            patternMatch = true;
            return patternMatch;
        }

        /*
        int sequenceLength = tablePattern.maxCardsBySuit;
        int sequenceDepth = tablePattern.isRunOfSequence(sequenceLength);

        */

        //__this adjusts the patternCheck attempting to match the table Pattern (if 2 jokers have been played)
        if(this.jokerCards.size() > 1) {
            int tableSequenceDepth = tablePattern.isRunOfSequence(tablePattern.maxSequenceLength);                          //retrieve the sequence target value from table pattern
            if (tableSequenceDepth > 0) {
                int playedSequenceDepth = this.isRunOfSequence(this.maxSequenceLength);                                     // redundant - if both depth are the same, the patterns would match naturally
                //calling isRunOfSequence with the target Depth from the table cards
                int attemptSequenceDepth = this.isRunOfSequence(tableSequenceDepth);                                        //analyzing if the sequence can be build in sufficient depth
                log.debug("COMPARE table sequence depth: " + tableSequenceDepth +" played sequence depth: "+playedSequenceDepth+" attempting sequence Depth"+attemptSequenceDepth);

                if (tableSequenceDepth == attemptSequenceDepth){                                                            //if the built match is ok, set the new Patterns name (make sure adjusted patterns get their instance variables set correctly as they differ from analyze pattern)
                    this.setSequence(attemptSequenceDepth);
                    this.maxSequenceLength = tablePattern.maxSequenceLength;
                    this.maxSequenceDepth = attemptSequenceDepth;
                }
            }
        }//end of playedCards had at least 2 Jokers
        return patternMatch;
    }

    public String analyzePattern() {
        this.setBomb();
        this.setSet();
        int sequenceDepth = this.isRunOfSequence(this.maxSequenceLength);
        this.setSequence(sequenceDepth);
        return this.patternName;
    }

    public void setBomb(){
        if(this.isBomb()){
            this.setPatternName("bomb");
        }
    }

    public void setSet(){
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

    public void setSequence(int sequenceDepth){  // when the sequenceDepth <=0 the Sequence is not valid, else it corresponds to the suitCount
        //int indicatorRunOfSequence = this.isRunOfSequence();
            //__Run of Singles____
            if (sequenceDepth == 1 ) {
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

            //__Run of Pairs____when suitCount (indicatorROS) result = 2
            if (sequenceDepth == 2 ) {
                switch (this.cardCount) {
                    case 4: this.setPatternName("runOfTwoPairs");           break;
                    case 6: this.setPatternName("runOfThreePairs");         break;
                    case 8: this.setPatternName("runOfFourPairs");          break;
                    case 10: this.setPatternName("runOfFivePairs");         break;
                    case 12: this.setPatternName("runOfSixPairs");          break;
                    case 14: this.setPatternName("runOfEightPairs");        break;
                }
            }

            //__Run of 3 of a Kind  ____when suitCount (indicatorROS) result = 3
            if (sequenceDepth == 3 ) {
                switch (this.cardCount) {
                    case 6: this.setPatternName("2ThreeOfAKind");           break;
                    case 9: this.setPatternName("3ThreeOfAKind");           break;
                    case 12: this.setPatternName("4ThreeOfAKind");          break;
                }
            }

            //__Run of 4 of a Kind  ____when suitCount (indicatorROS) result = 4
            if (sequenceDepth == 4 ) {
                switch (this.cardCount) {
                    case 8: this.setPatternName("2FourOfAKind");            break;
                    case 12: this.setPatternName("3FourOfAKind");           break;
                }
            }
        }
    /**____ IS RUN OF SEQUENCE____
     * checking for sequences, returning an int meaning 0 = no sequence, 1=Run of Singles,
     * 2= Run of Pairs, 3=Run of 3ofAKind, 4= 4oAKind (including jokerCards)
     */
    public int isRunOfSequence (int lvMaxSequenceLength) {                  // when the indicatorRunOfSequence <=0 the Sequence is not valid, else it corresponds to the suitCount
        int isRunOfSequence ;
        int remainingJokers = this.jokerCards.size();

        //__Sequence check for each suited List
        for (ArrayList<Card> suitedCards : this.cardsBySuit.values()){
                log.debug("calling inSequence here ");
            remainingJokers = this.inSequence(suitedCards, remainingJokers, this.lowestRank, lvMaxSequenceLength);
        }

        //__setting the return value__
            log.debug("suitCount coming from INSTANCE VAR "+this.suitCount);
            log.debug("remaining Jokers and indicator from Patter.inSequence as int  "+remainingJokers);

        isRunOfSequence = this.suitCount;
        if (remainingJokers == 2 ){}
        if (remainingJokers < 0) {
            isRunOfSequence = remainingJokers;
                log.debug("if the jokerCount was smaller zero is run of Sequence indicator is set here as: "+isRunOfSequence);
        }
        return isRunOfSequence;
    }

    /**____IN SEQUENCE ?____
     * int return Check : returns 2, 1, 0 or -1, depending on how many jokers are left at the end of the function (-1 -> false sequence)
     * all in Sequence checks if a regular Sequence is possible including the use of 1 or 2 jokers
     */
    public static int inSequence (ArrayList<Card> cards, int jokerCount, int lowestRank, int lvMaxSequenceLength) {
            log.debug("inside in Sequence ... first print cards as they come in");
            printCards(cards);
        int lengthController = 0;
        int errorTolerance = jokerCount;
        //for (int i = 0; i < lvMaxSequenceLength; i++){
        int i = 0;
            for (Card c : cards) {
                if (c.getCardRank() != lowestRank + i) {
                    errorTolerance -= 1;
                    i++;
                    log.debug("gap at Card Nr. " + c.getCardID() + " remaining error Tolerance is " + errorTolerance);
                    log.debug("error Tolerance reduced by one _ now remaining : " + errorTolerance);
                }
                lengthController ++;
                i++;
            }
            int difference = lvMaxSequenceLength - (lengthController+1);
                log.debug("difference " +difference+"maxSequenceLength "+lvMaxSequenceLength+"length Controller "+lengthController);
            if (difference>0){
                errorTolerance = errorTolerance - difference;
            }
        //}
        return errorTolerance;
    }


    /**____IS BOMB ?____
     * Boolean Check :
     * checks for sequence 3-5-7-9 (suited or 4suits) and 2 or 3 jokers played
     */
    public boolean isBomb () {
        boolean isBomb = true;
        if (this.normalCards.size() > 0 && this.jokerCards.size() > 0 ){
            isBomb = false;
            return isBomb;
        }

        //__bomb sequence of 3-5-7-9 - to be a bomb cards must be all suited or all different suit
        if(this.normalCards.size() > 0  ){
            int lowestRank = this.normalCards.get(0).getCardRank();
            if (this.normalCards.get(0).getCardRank() == 3 && this.normalCards.size() ==4 ){
                for (int i = 0; i < this.normalCards.size(); i++) {
                    if (this.normalCards.get(i).getCardRank() != lowestRank + i * 2) {
                        isBomb = false;
                    }
                }
                if (this.suitCount == 2 || this.suitCount == 3){
                    isBomb = false;
                }
            }
            else {
                isBomb = false;
            }
            return isBomb;
        }

        //__joker bombs__
        if(this.jokerCards.size() > 0   ){
            switch (this.jokerCards.size()){
                case 1 : isBomb = false;    break;
                case 2 : isBomb = true;     break;
                case 3 : isBomb = true;     break;
            }
        }
        return isBomb;
    }
}
