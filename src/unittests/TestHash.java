package unittests;

import library.Card;
import library.CardDeck;
import server.GameHandler;
import server.Pattern;

import javax.print.DocFlavor;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Created by Riya on 29.11.2014.
 */
public class TestHash {

    @SuppressWarnings("unchecked")
	public static boolean isRunOfSequence (ArrayList<Card> normalCards, ArrayList<Card> jokerCards) {
        boolean isRunOfSequence = false;
        Collections.sort(normalCards);

        HashMap<String, ArrayList<Card>> cardSuits = new HashMap<String, ArrayList<Card>>(5);


        for (Card c : normalCards) {
            if (!cardSuits.containsKey(c.getCardSuit())) {
                cardSuits.put(c.getCardSuit(), new ArrayList<Card>());
            }
           cardSuits.get(c.getCardSuit()).add(c);
        }

        int counter = cardSuits.keySet().size();
        for (ArrayList<Card> cardlist : cardSuits.values()){
            //boolean inSeq = Pattern.allInSequence(cardlist, 0);
            System.out.println(cardlist.get(0).getCardSuit()); // gibt suit name aus
            for (Card c : cardlist) {
                System.out.println(c.getCardID());
            }

        }
        ArrayList<Card> certainSuitCards = new ArrayList<Card>();
        certainSuitCards = cardSuits.get("green");
        for (Card c : certainSuitCards) {
            System.out.println("these are the green cards"+ c.getCardID());
        }


        System.out.println(counter);
        return true;
    }
    public static void main(String[] args) {
        CardDeck lvCardDeck = new CardDeck(3);

        ArrayList<Card> normalCards = new ArrayList<Card>();
        normalCards.add(lvCardDeck.getCardById(30));
        normalCards.add(lvCardDeck.getCardById(50));
        normalCards.add(lvCardDeck.getCardById(70));
        normalCards.add(lvCardDeck.getCardById(81));
        normalCards.add(lvCardDeck.getCardById(83));
        normalCards.add(lvCardDeck.getCardById(74));


        boolean boo = isRunOfSequence(normalCards, new ArrayList<Card>());

    }
}
