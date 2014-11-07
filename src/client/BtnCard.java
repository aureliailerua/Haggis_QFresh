package client;

import java.awt.Dimension;
import java.awt.*;

import javafx.scene.paint.Color;

import javax.swing.*;
import javax.swing.border.Border;

import library.Card;

public class BtnCard extends JButton{

	private Card card;
	public boolean selected;
	
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

	private void drawUnselected() {
		setPreferredSize(new Dimension(80,127));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		//setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		// ! btnCardHand[i].setIcon(myIcon[i]);
	}
	
	private void drawSelected() {
		setPreferredSize(new Dimension(80,127));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		// ! btnCardHand[i].setIcon(myIcon[i]);
	}
	
}
