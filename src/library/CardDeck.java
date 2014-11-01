package library;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author andreas.denger
 * This Class holds the generated CardDeck holding all cards.
 * It also provides the methods to build the Deck, shuffle it
 * and give cards to Players.
 * After distributing the Cards to the Players, 
 * the Haggis Cards remain.
 */
public class CardDeck {
	ArrayList<Card> cardDeck;
	
	public void setcardDeck(ArrayList<Card> cardDeck) {
		this.cardDeck = cardDeck;
	}

	/**
	 * Since all Haggis-Players get 14 numbered cards, regardless of numPlayer,
	 * this method does exaclty that.
	 * Because buildDeck() puts the numbered Cards in the front of the array, 
	 * the first 14 cards can be extracted and returned.
	 * @return ArrayList<Card> with 14 random numbered Cards 
	 */
	public ArrayList<Card> give14Cards() {
		ArrayList<Card> lvCards = new ArrayList<Card>();
		for (int i=0; i < 14; i++){
			lvCards.add(this.cardDeck.get(0));
			this.cardDeck.remove(0);
		}
		return lvCards;
	}//give14Cards
	
	/**
	 * Since all Haggis-Players get 3 Joker cards, regardless of numPlayer,
	 * this method does exaclty that.
	 * Because buildDeck() puts the Joker Cards in the back of the array, 
	 * the last 3 cards cam be extracted and returned.
	 * @return ArrayList<Card> with 3 Joker Cards 
	 */
	public ArrayList<Card> give3Jokers() {
		ArrayList<Card> lvCards = new ArrayList<Card>();
		for (int i=0; i < 3; i++){
			lvCards.add(this.cardDeck.get(this.cardDeck.size()-1));
			this.cardDeck.remove(this.cardDeck.size()-1);
		}
		return lvCards;
	}//give3Jokers

	/**
	 * This method builds the CardDeck according to Haggis rules.
	 * The numbered Cards will be shuffled in the front of the arrayList.
	 * The Jokers will be ordered at the end of the list.
	 * @param numPlayers as Integer, namely 2 or 3
	 * @return Complete Deck in ArrayList<Card> with 36/45 (shuffled) numbered Cards and 6/9 Jokers
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Card> buildDeck(Integer numPlayers){
		// local var's
		int lvSuitCount;
		int lvCardId = 0;
		Card lvNewCard;
		ArrayList<Card> lvNewCardDeck = new ArrayList<Card>();

		//evaluate input and set number of Suits for the new deck
		if (numPlayers == 2 || numPlayers == 3) {
			lvSuitCount = numPlayers + 2;	
		} else {
			throw new IllegalArgumentException("illegal numPlayers, must be 2 or 3");
		}
		
		//build numbered Cards based on suitCount i.e. numPlayers and Haggis-rulebook;
		for (int suitNumber=0; suitNumber < lvSuitCount; suitNumber++){
			for (int cardRank=2; cardRank <= 10; cardRank++){
				lvNewCard = new Card(lvCardId, cardRank, Card.SUITS[suitNumber], cardRank % 2);
				lvNewCardDeck.add(lvNewCard);
				lvCardId++;
			}
		}
		
		//shuffle numbered Cards using Knuth shuffle
		int lvDeckSize = lvNewCardDeck.size(); 
		for (int i=0; i<lvDeckSize; i++){
			int rand = i + (int) (Math.random() * (lvDeckSize-i));
			Collections.swap(lvNewCardDeck, rand, i);
		}
		
		//build JokerCards based on numPlayers
		final int[] fibonacci = {1,1,2,3,5,7};
		for (int i=0; i < numPlayers; i++){
			for (int ii = 0; ii < 3; ii++){
				lvNewCard = new Card(lvCardId, ii + 11, Card.SUITS[5], fibonacci[ii+2]);
				lvNewCardDeck.add(lvNewCard);
				lvCardId++;
			}
		}
		// I realise now, that the cardID is utterly useless. And Beni knew it.
		return lvNewCardDeck;
	}//buildDeck
}//CardDeck
