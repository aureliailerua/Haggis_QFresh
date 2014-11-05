package client;

import java.awt.EventQueue;
import java.awt.Insets;
import java.net.URL;  
import java.awt.Dimension;  

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

public class TableView {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableView window = new TableView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TableView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 740, 515);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblGameTable = new JLabel("Game Table");
		frame.getContentPane().add(lblGameTable, BorderLayout.NORTH);
		
		JPanel panelCardTable = new JPanel();
		frame.getContentPane().add(panelCardTable, BorderLayout.CENTER);
		
		JPanel panelCardHand = new JPanel();
		frame.getContentPane().add(panelCardHand, BorderLayout.SOUTH);
		
		// TEST Class CardTest
		CardTest[] cardHand = new CardTest[4];
  		cardHand[0] = new CardTest(2, 3, 1, "green");
  		cardHand[1] = new CardTest(1, 3, 1, "red");
  		cardHand[2] = new CardTest(3, 4, 1, "green");
  		cardHand[3] = new CardTest(0, 3, 1, "green");

		
		
		JButton[] btnCardHand = new JButton[cardHand.length];
		// Icon building
		// ! ImageIcon[] myIcon = new ImageIcon[14];
		// Icon generation
		// ! for(int i=0; i<cardName.length; i++){
			//myIcon[i]= new ImageIcon("Bilder/karte"+(i+1)+".jpg");
			// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		//}
		
	    for(int i = 0; i<btnCardHand.length; i++){
    		btnCardHand[i] = new JButton(cardHand[i].getcardRank() + cardHand[i].getSuit());
    		// ! btnCardHand[i].setIcon(myIcon[i]);
    		btnCardHand[i].setPreferredSize(new Dimension(200,313));
    		btnCardHand[i].setOpaque(false);
    		btnCardHand[i].setBorderPainted(true);
    		btnCardHand[i].setContentAreaFilled(false);
    		panelCardHand.add(btnCardHand[i]);
    	}
		
		
		/**
		 * Initialize the Hand
		 */
		

		
		//http://www.coderanch.com/t/340584/GUI/java/create-JButton-Array
	}

}

