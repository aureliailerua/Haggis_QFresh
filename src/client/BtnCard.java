package client;

import java.awt.Dimension;
import javax.swing.*;

import library.Card;

public class BtnCard extends JButton{

	private CardTest card;
	public boolean selected;
	
	public BtnCard(CardTest card) {
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

	private void drawUnselected() {
		setPreferredSize(new Dimension(200,313));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		// ! btnCardHand[i].setIcon(myIcon[i]);
	}
	
	private void drawSelected() {
		setPreferredSize(new Dimension(200,313));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		// ! btnCardHand[i].setIcon(myIcon[i]);
	}
	
}
