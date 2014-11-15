package client;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;

import library.Card;

public class BtnCard extends JButton{

	private Card card;
	public boolean selected = false;
	
	public BtnCard(Card card) {
		super(card.getCardName());
		this.card = card;
		drawUnselected();	
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected() {
		this.selected = true;
		drawSelected();
	}
	public void setUnselected() {
		this.selected = false;
		drawUnselected();
	}
	public Card getCard(){
		return card;
	}

	private void drawUnselected() {
		setPreferredSize(new Dimension(80,127));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
		// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		// ! btnCardHand[i].setIcon(myIcon[i]);
	}
	
	private void drawSelected() {
		setPreferredSize(new Dimension(80,127));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));

		// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		// ! btnCardHand[i].setIcon(myIcon[i]);
	}
	
}
