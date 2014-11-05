package client;

import java.awt.Color;
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

public class TableView extends JFrame implements ActionListener{

	private JFrame frame;
	JLabel lblGameTable = new JLabel();
	JPanel panelCardTable = new JPanel();
	JPanel panelCardHand = new JPanel();
	
    JButton btnPlay = new JButton();
    JButton btnPass = new JButton();
	CardTest[] cardHand = new CardTest[4];
	JButton[] btnCardHand = new JButton[cardHand.length];
	JButton btnCardTable = new JButton();
	
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

		frame = new JFrame();
		//frame.setSize(new Dimension(1500,1000));
		frame.setBounds(100, 100, 1500, 900); // x-Position, y-Position, breite und höhe des Fenster
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		lblGameTable.setName("Game Table");
		frame.getContentPane().add(lblGameTable, BorderLayout.NORTH);
		
		frame.getContentPane().add(panelCardTable, BorderLayout.CENTER);
		btnCardTable.setPreferredSize(new Dimension(200,313));
		btnCardTable.setVisible(false);
		panelCardTable.add(btnCardTable);
		
		frame.getContentPane().add(panelCardHand, BorderLayout.SOUTH);

		// ---- TEST Class CardTest -----

  		cardHand[0] = new CardTest(2, 3, 1, "green");
  		cardHand[1] = new CardTest(1, 3, 1, "red");
  		cardHand[2] = new CardTest(3, 4, 1, "green");
  		cardHand[3] = new CardTest(0, 3, 1, "green");

		
		
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
    		btnCardHand[i].addActionListener(this);
    		panelCardHand.add(btnCardHand[i]);
    	}
		
	    btnPlay.setText("Play");
		btnPlay.setBackground(Color.GREEN);
		btnPlay.setPreferredSize(new Dimension(100,30));
		btnPlay.setEnabled(true);
		btnPlay.setVisible(true);
		btnPlay.addActionListener(this); //generiert Listener
		panelCardHand.add(btnPlay);
	    
		btnPass.setText("Passen");
		btnPass.setBackground(Color.GREEN);
		btnPass.setPreferredSize(new Dimension(100,30));
		btnPass.setEnabled(true);
		btnPass.setVisible(true);
		btnPass.addActionListener(this); //generiert Listener
		panelCardHand.add(btnPass);

	}

	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnPlay){
            System.out.println("Button geklickt!");   
        }
        
        for(int i=0; i<12; i++){
			if (btnCardHand[i].equals(e.getSource())){
				//System.out.println("btnCard "+ i +" gedrückts");
	        	btnCardTable.setText(btnCardHand[i].getText());
	    		btnCardTable.setVisible(true);
				
			}
		}
    }


} //class end
	

