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
        public int lowestRank = 0;
        int highestRank = 0;
        int maxSequenceLength = 0;
        //__after Pattern is built according to table pattern target (possibly with Jokers) determined pattern parameters are stored here:
        int builtSequenceLength = 0;
        int builtSequenceDepth = 0;
        int deepeningJokers =0;
        int bombIndex =0;

    //__ Pattern Factory__
    public Pattern(ArrayList<Card> cards) {
        for (Card c : cards) {
            printCards(cards);
            if ( !  c.getCardSuit().equals("joker")) this.normalCards.add(c);
            if (    c.getCardSuit().equals("joker")) this.jokerCards.add(c);
        }
        Collections.sort(this.normalCards);         printCards(this.normalCards);
        Collections.sort(this.jokerCards);          printCards(this.jokerCards);
        this.lowestRank = Collections.min(cards).getCardRank();
        if (normalCards.size()>0) {
            this.highestRank = Collections.max(normalCards).getCardRank();
            this.maxSequenceLength = (this.highestRank - this.lowestRank +1);
        }
        this.cardCount = cards.size();
        this.setSuitCount();
        this.setRankCount();
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
            }
            this.cardsByRank.get(c.getCardRank()).add(c);
        }
        this.rankCount = this.cardsByRank.keySet().size();
    }

    public void setPatternName(String lvPatternName){
        this.patternName = lvPatternName;
    }

    public static void printCards(ArrayList<Card> cards ){
        for (Card c : cards) {
            log.debug(" Card Nr. "+ c.getCardID());
        }
    }

    public boolean comparePattern (Pattern tablePattern ){
        boolean levelingOK = (tablePattern.lowestRank < this.lowestRank);
        log.debug("tablePaTTERN lowest Rank"+ tablePattern.lowestRank+" this lowest Rank "+ this.lowestRank);
        boolean patternMatch = false;
        this.analyzePattern();
        //__catch bombs___
        if(this.isBomb() && ( tablePattern.bombIndex < this.bombIndex) ) {
            levelingOK = (tablePattern.bombIndex < this.bombIndex); log.debug("leveling of bombs" +levelingOK+" tablepattern bombindex"+tablePattern.bombIndex+" this bombindex "+this.bombIndex);
            //this.setBomb();
            patternMatch = true;
            return patternMatch;
        }

        //__naturally matching patterns are detected here__

        log.debug("incoming Cards naturally is"+this.patternName+" while tablePattern is "+tablePattern.patternName+" while leveling is "+levelingOK);
        if(this.patternName.equals(tablePattern.patternName) && levelingOK ){
            patternMatch = true;
                log.debug("natural matching pattern "+patternMatch);
            return patternMatch;
        }

        //__this adjusts the patternCheck attempting to match the table Pattern (if 2 jokers have been played)
        if(this.jokerCards.size() > 1 && levelingOK) {
            int playedSequenceDepth = this.isRunOfSequence(tablePattern.builtSequenceLength);
            boolean deepeningOK = (this.deepeningJokers == tablePattern.builtSequenceLength);

                log.debug("deep jokers "+this.deepeningJokers +" deepeningOK is "+deepeningOK +" tablePatterns Length: "+tablePattern.builtSequenceLength);
                log.debug("tablePattern built SequenceLength: "+tablePattern.builtSequenceLength);
                log.debug("tablePattern.SequenceDepth :"+ tablePattern.builtSequenceDepth+" table Card Count"+tablePattern.cardCount);
                log.debug("playedPattern built SequenceLength: "+this.builtSequenceLength);
                log.debug("playedSequenceDepth "+playedSequenceDepth);

            if(playedSequenceDepth == tablePattern.builtSequenceDepth) {
                    log.debug("match is OK without deepening jokers");
                patternMatch = true;        log.debug("matching pattern without deepening Jokers"+patternMatch);
                this.setSequence(playedSequenceDepth);
                return patternMatch;
            }
            if(deepeningOK && ((playedSequenceDepth +1 ) == tablePattern.builtSequenceDepth) ){
                log.debug("deepening Jokers used - match is ok ");
                this.setSequence(tablePattern.builtSequenceDepth);
                patternMatch = true;
                return patternMatch;
            }
        }
        log.debug("compare has not found a valid option pattern match is "+ patternMatch);
        return patternMatch;
    }

    public String analyzePattern() {
        this.setBomb();
        int naturalSequenceDepth = this.isRunOfSequence(this.maxSequenceLength); log.debug("maxSequenceLength "+this.maxSequenceLength);
        this.setSequence(naturalSequenceDepth);
        return this.patternName;
    }

    public void setBomb(){
        if(this.isBomb() && this.jokerCards.size()>0){
            //log.debug("is bomb ok - nr. of joker cards "+this.jokerCards.size());
            int calculateIndex = 0;
            for (Card c : this.jokerCards) {
                calculateIndex += c.getCardID();
                //log.debug(bombIndex+" BI ..incremented ");
            }
            this.bombIndex = calculateIndex;

            switch (this.bombIndex) {
                case 240 : this.setPatternName("JQBomb");         break;
                case 250 : this.setPatternName("JKBomb");         break;
                case 260 : this.setPatternName("QKBomb");         break;
                case 375 : this.setPatternName("JQKBomb");        break;
            }
        }
        if(this.isBomb() && this.normalCards.size()>0){
            if(this.suitCount==1){
                this.bombIndex = 999;
                this.setPatternName("suitedBomb");
            }
            else if(this.suitCount==4){
                this.bombIndex = 111;
                this.setPatternName("unsuitedBomb");
            }
        }
    }
/*
    public void setSet(){
        if (this.rankCount == 1 || ( this.jokerCards.size()==1 && this.normalCards.size()==0 )) {
            this.builtSequenceDepth = this.cardCount;
            this.builtSequenceLength = 1;
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
*/
    public void setSequence(int sequenceDepth){  // when the sequenceDepth <=0 the Sequence is not valid, else it corresponds to the suitCount
        if( this.jokerCards.size() == 1 && this.normalCards.size()==0 ){ this.setPatternName("single");  }
        //__set Sets__
        if (this.rankCount == 1 ) {
            this.builtSequenceDepth = this.cardCount;
            this.builtSequenceLength = 1;
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

        //__Run of Singles____
        if (sequenceDepth == 1 && this.rankCount >1 ) {
            this.builtSequenceDepth = 1;
            this.builtSequenceLength = this.cardCount;
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
        if (sequenceDepth == 2  && this.rankCount >1 ) {
            this.builtSequenceDepth = 2;
            this.builtSequenceLength = (this.cardCount / this.builtSequenceDepth);
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
        if (sequenceDepth == 3 && this.rankCount >1 ) {
            this.builtSequenceDepth = 3;
            this.builtSequenceLength = (this.cardCount / this.builtSequenceDepth);
            switch (this.cardCount) {
                case 6: this.setPatternName("2ThreeOfAKind");           break;
                case 9: this.setPatternName("3ThreeOfAKind");           break;
                case 12: this.setPatternName("4ThreeOfAKind");          break;
            }
        }

        //__Run of 4 of a Kind  ____when suitCount (indicatorRun of Sequence) result = 4
        if (sequenceDepth == 4 && this.rankCount >1 ) {
            this.builtSequenceDepth = 4;
            this.builtSequenceLength = (this.cardCount / this.builtSequenceDepth);
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
        int lvSequenceDepth =0;
        //__Sequence check for each suited List
        for (ArrayList<Card> suitedCards : this.cardsBySuit.values()){
            remainingJokers = this.inSequence(suitedCards, remainingJokers, this.lowestRank, lvMaxSequenceLength);
            lvSequenceDepth ++;
        }
        if (remainingJokers >= 2 ){                                          //clumsy to pass the information, if ambivalent patterns are possible
            this.deepeningJokers = remainingJokers;
        }
        isRunOfSequence = lvSequenceDepth;
        if (remainingJokers < 0) {
            isRunOfSequence = remainingJokers;
        }
        return isRunOfSequence;
    }

    /**____IN SEQUENCE ?____
     * int return Check : returns 2, 1, 0 or -1, depending on how many jokers are left at the end of the function (-1 -> false sequence)
     * all in Sequence checks if a regular Sequence is possible including the use of 1 or 2 jokers
     */
    public static int inSequence (ArrayList<Card> cards, int jokerCount, int lowestRank, int lvMaxSequenceLength) {
        int lengthController = 0;
        int errorTolerance = jokerCount;
        int i = 0;
            for (Card c : cards) {
                if (c.getCardRank() != lowestRank + i) {
                    errorTolerance -= 1;
                    i++;
                    //log.debug("gap at Card Nr. " + c.getCardID() + " remaining error Tolerance is " + errorTolerance);
                   // log.debug("error Tolerance reduced by one _ now remaining : " + errorTolerance);
                }
                lengthController ++;
                i++;
            }
            int difference = lvMaxSequenceLength - (lengthController+1);
            if (difference>0){
                errorTolerance = errorTolerance - difference;
            }
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
