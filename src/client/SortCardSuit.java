package client;

import java.util.Comparator;

public class SortCardSuit implements Comparator<BtnCard>{

	@Override
	public int compare(BtnCard c1, BtnCard c2) {
			if(c1.getCard().getCardSuit() == c2.getCard().getCardSuit()){
				return c1.getCard().getCardRank() - (c2.getCard().getCardRank());
			}
			return c1.getCard().getCardSuit().compareTo(c2.getCard().getCardSuit());		
	}

}
	
	/**
		
		int suitO1 = Arrays.asList(c1.SUITS).indexOf(c1.getCardSuit());
		int suitO2 = Arrays.asList(c2.SUITS).indexOf(c2.getCardSuit());
		 
		if (suitO1 < suitO2) {
			 return -1;
		} else if (suitO1 == suitO2) {
			return c1.getCardRank() - (c2.getCardRank());
			// return 0;
		} else if (suitO1 < suitO2) {
			 return 1;
		}
	return -1;*/
