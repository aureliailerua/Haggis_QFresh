package library;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author andreas.denger
 * Class holds the generated CardDeck holding all cards.
 * After distributing of the Cards to the Players, 
 * the Haggis Cards remain.
 */
public class CardDeck {
	ArrayList<Card> cardDeck;
	
	public void setcardDeck(ArrayList<Card> cardDeck) {
		this.cardDeck = cardDeck;
	}

	public ArrayList<Card> give14Cards() {
		ArrayList<Card> lvCards = new ArrayList<Card>();
		for (int i=0; i < 14; i++){
			lvCards.add(this.cardDeck.get(0));
			this.cardDeck.remove(0);
		}
		return lvCards;
	}//give14Cards
	
	public ArrayList<Card> give3Jokers() {
		ArrayList<Card> lvCards = new ArrayList<Card>();
		for (int i=0; i < 3; i++){
			lvCards.add(this.cardDeck.get(this.cardDeck.size()-1));
			this.cardDeck.remove(this.cardDeck.size()-1);
		}
		return lvCards;
	}//giveJokers


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
			throw new IllegalArgumentException("illegal numPlayers for buildDeck(numPlayers)");
		}
		
		//build numbered Cards based on suitCount i.e. numPlayers and Haggis-rulebook;
		for (int suitNumber=0; suitNumber < lvSuitCount; suitNumber++){
			for (int cardRank=2; cardRank <= 10; cardRank++){
				lvNewCard = new Card();
				lvNewCard.setCardID(lvCardId);
				lvNewCard.setCardRank(cardRank);
				lvNewCard.setCardSuit(Card.SUITS[suitNumber]);
				lvNewCard.setCardPoint(cardRank % 2);
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
				lvNewCard = new Card();
				lvNewCard.setCardID(lvCardId);
				lvNewCard.setCardRank(ii + 11);
				lvNewCard.setCardSuit(Card.SUITS[5]);
				lvNewCard.setCardPoint(fibonacci[ii+2]);
				lvNewCardDeck.add(lvNewCard);
				lvCardId++;
			}
		}
		// I realise now, that the cardID is utterly useless. And Beni knew it.
		return lvNewCardDeck;
	}//buildDeck
}//CardDeck
