package client;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.BorderFactory;

import library.Card;

/**
 * @author felicita.acklin
 * Klasste stellt Karten in Buttons dar.
 *
 */
public class BtnCard extends JButton{

	private Card card;
	public boolean selected = false;
	private String pathImgCard;
	
	public BtnCard(Card card) {
		super();
		pathImgCard = "/gameContent/"+ card.getCardSuit() + "/" + card.getCardName() + ".jpg";
		setIcon(new ImageIcon(BtnCard.class.getResource(pathImgCard)));
		this.card = card;
		drawUnselected();	
	}
	
	/**
	 * Check if card is selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * Set Card as selected or as unselected
	 */
	public void setSelected() {
		this.selected = true;
		drawSelected();
	}
	public void setUnselected() {
		this.selected = false;
		drawUnselected();
	}
	/**
	 * Get Card
	 * @return card
	 */
	public Card getCard(){
		return card;
	}

	/**
	 * Draw unselected or seleted Cards
	 */
	private void drawUnselected() {
		setPreferredSize(new Dimension(80,127));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
		setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
	}
	
	private void drawSelected() {
		setPreferredSize(new Dimension(80,127));
		setOpaque(false);
		setBorderPainted(true);
		setContentAreaFilled(false);
		setBorder(BorderFactory.createLineBorder(Color.RED, 2, true));
	}
	
}
