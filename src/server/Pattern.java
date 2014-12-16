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

    /**
     * This method compares the incoming cards with the pattern on the table and accepts or declines the new move
     * @param Pattern tablePattern
     * @return boolean patternMatch
     */
    public boolean comparePattern (Pattern tablePattern ){
        boolean levelingOK = (tablePattern.lowestRank < this.lowestRank);
        log.debug("tablePaTTERN lowest Rank is: "+ tablePattern.lowestRank+" this lowest Rank is: "+ this.lowestRank);
        boolean patternMatch = false;
        this.analyzePattern();

        //__catch bombs___
        if(this.isBomb() ){
            levelingOK = (tablePattern.bombIndex < this.bombIndex);             log.debug("leveling of bombs is: " +levelingOK+" tablepattern has bombindex: "+tablePattern.bombIndex+" this has bombindex: "+this.bombIndex);
            this.setBomb();
            if (levelingOK){
                patternMatch = true;
            }                                                                   log.debug("pattern match is: "+patternMatch);
            return patternMatch;
        }

        //__naturally matching patterns are detected here__
        if(this.patternName.equals(tablePattern.patternName) && levelingOK ){   log.debug("incoming Cards naturally is: "+this.patternName+" while tablePattern is: "+tablePattern.patternName+" while leveling is: "+levelingOK);
            patternMatch = true;                                                log.debug("natural matching pattern: "+patternMatch);
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
                patternMatch = true;                                            log.debug("match of alternative pattern without deepening Jokers is : "+patternMatch);
                this.setSequence(playedSequenceDepth);
                return patternMatch;
            }
            if(deepeningOK && ((playedSequenceDepth +1 ) == tablePattern.builtSequenceDepth) ){
                                                                                log.debug("deepening Jokers used - match of alternative pattern is ok ");
                this.setSequence(tablePattern.builtSequenceDepth);
                patternMatch = true;
                return patternMatch;
            }
        }                                                                       log.debug("compare has not found a valid option pattern match is "+ patternMatch);
        return patternMatch;
    }
    /**
     * This method analyzes the incoming Pattern-Object and defines what kind of Pattern it is.
     * @return patternName
     */
    public String analyzePattern() {
        this.setBomb();
        int naturalSequenceDepth = this.isRunOfSequence(this.maxSequenceLength);
        this.setSequence(naturalSequenceDepth);
        return this.patternName;
    }

    /**
     * This method sets the Pattern as bomb and writes the strength in the bombIndex
     */
    public void setBomb(){
        if(this.isBomb() && this.jokerCards.size()>0){
            int calculatedIndex = 0;
            for (Card c : this.jokerCards) {
                calculatedIndex += c.getCardID();
            }
            this.bombIndex = calculatedIndex;

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

    /**
     * This method sets the Patter as Sequence or Set
     * @param sequenceDepth
     */
    public void setSequence(int sequenceDepth){                         // when the sequenceDepth <=0 the Sequence is not valid, else it corresponds to the suitCount
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

        //__Run of Pairs____when suitCount resulting from inSequence is 2
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

        //__Run of 3 of a Kind  ____when suitCount resulting from inSequence is 3
        if (sequenceDepth == 3 && this.rankCount >1 ) {
            this.builtSequenceDepth = 3;
            this.builtSequenceLength = (this.cardCount / this.builtSequenceDepth);
            switch (this.cardCount) {
                case 6: this.setPatternName("2ThreeOfAKind");           break;
                case 9: this.setPatternName("3ThreeOfAKind");           break;
                case 12: this.setPatternName("4ThreeOfAKind");          break;
            }
        }

        //__Run of 4 of a Kind  ____when suitCount resulting from inSequence is 4
        if (sequenceDepth == 4 && this.rankCount >1 ) {
            this.builtSequenceDepth = 4;
            this.builtSequenceLength = (this.cardCount / this.builtSequenceDepth);
            switch (this.cardCount) {
                case 8: this.setPatternName("2FourOfAKind");            break;
                case 12: this.setPatternName("3FourOfAKind");           break;
            }
        }
    }

    /**
     * This method checks if the Pattern is a Sequence or Set.
     * The return value sequenceDepth corresponds to the number of different suits in parallel sequence
     * 0 = no valid sequence, 1=Run of Singles, 2=Run of Pairs, 3=Run of 3ofAKind, 4=Run of 4oAKind
     * It sets the remaining jokers at the end of all checks to the variable deepeningJoker (required for the ambivalent Pattern check)
     * @param lvMaxSequenceLength
     * @return sequenceDepth
     **/
    public int isRunOfSequence (int lvMaxSequenceLength) {
        int sequenceDepth ;
        int remainingJokers = this.jokerCards.size();
        int lvSequenceDepth =0;
        //__Sequence check for each suited List
        for (ArrayList<Card> suitedCards : this.cardsBySuit.values()){
            remainingJokers = this.inSequence(suitedCards, remainingJokers, this.lowestRank, lvMaxSequenceLength);
            lvSequenceDepth ++;
        }
        if (remainingJokers >= 2 ){
            this.deepeningJokers = remainingJokers;
        }
        sequenceDepth = lvSequenceDepth;
        if (remainingJokers < 0) {
            sequenceDepth = remainingJokers;
        }
        return sequenceDepth;
    }

    /**
     * This method is called by isRunOfSequence for each suited List, it checks if the cards in the list are in a sequence and achieving the required sequence length.
     * Jokers are consumed at gaps and at differences between target length and actual length.
     * @return errorTolerance of 2, 1, 0 indicating the remaining jokers or-1 for an invalid sequence
     */
    public static int inSequence (ArrayList<Card> cards, int jokerCount, int lowestRank, int lvMaxSequenceLength) {
        int lengthController = 0;
        int errorTolerance = jokerCount;
        int i = 0;
            for (Card c : cards) {
                if (c.getCardRank() != lowestRank + i) {
                    errorTolerance -= 1;
                    i++;
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
     * This method checks if the Pattern is a bomb of 3-5-7-9 (suited or 4suits) or of 3 jokers and returns a boolean.
     * @return isBomb
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