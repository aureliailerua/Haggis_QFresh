package client;

import java.util.Comparator;

public class SortCardRank implements Comparator<BtnCard>{

	@Override
	public int compare(BtnCard c1, BtnCard c2) {
		if(c1.getCard().getCardRank() == c2.getCard().getCardRank()){
			return c1.getCard().getCardSuit().compareTo(c2.getCard().getCardSuit());
		}
		return c1.getCard().getCardRank() - (c2.getCard().getCardRank());	
	}
}



