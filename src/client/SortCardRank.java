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
/*public int compare(Card o1, Card o2) {
// TODO Auto-generated method stub
int rankO1 = o1.getCardRank();
int rankO2 = o2.getCardRank();

if (rankO1 < rankO2) {
	 return -1;
} else if (rankO1 == rankO2) {
	 return 0;
} else if (rankO1 < rankO2) {
	 return 1;
}
return -1;
}*/


