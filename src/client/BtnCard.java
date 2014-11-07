package client;

import java.awt.Dimension;

import javax.swing.*;

public class BtnCard extends JButton{

	private CardTest card;
	
	
	public BtnCard() {
		super();
		
		// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));

		= new JButton(cardHand[i].getcardRank() + cardHand[i].getSuit());
		// ! btnCardHand[i].setIcon(myIcon[i]);
		btnCardHand[i].setPreferredSize(new Dimension(200,313));
		btnCardHand[i].setOpaque(false);
		btnCardHand[i].setBorderPainted(true);
		btnCardHand[i].setContentAreaFilled(false);
		btnCardHand[i].addActionListener(this);
		panelCardHand.add(btnCardHand[i]);
		
	}
	
	
}
