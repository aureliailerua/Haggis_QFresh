package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Insets;
import java.net.URL;  
import java.util.ArrayList;
import java.awt.Dimension;  

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.Server;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;

import library.Card;
import library.GameState;
import library.Player;

public class TableView_ extends JFrame implements ActionListener{

	private JFrame frame;
	JLabel lblGameTable = new JLabel();
	JPanel panelCardTable = new JPanel();
	JPanel panelCardHand = new JPanel();
	JPanel panelJoker = new JPanel();
	
    JButton btnPlay = new JButton();
    JButton btnPass = new JButton();
	CardTest[] cardHand = new CardTest[4];
	ArrayList<BtnCard> btnCardHand = new ArrayList<BtnCard>();
	JButton btnCardTable = new JButton();
	JButton[] btnJocker	= new JButton[3];
	TableController controller;
	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TableView_ view = new TableView_();
					TableController controller = new TableController(view);

					view.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TableView_() {

		frame = new JFrame();
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
		
		frame.getContentPane().add(panelJoker, BorderLayout.WEST);

		// ---- TEST Class CardTest -----

  				// Icon building
		// ! ImageIcon[] myIcon = new ImageIcon[14];
		// Icon generation
		// ! for(int i=0; i<cardName.length; i++){
			//myIcon[i]= new ImageIcon("Bilder/karte"+(i+1)+".jpg");
			// ! myIcon[i] = new ImageIcon(TableView.class.getResource("/gameContent/gruen03.jpg"));
		//}
				
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
        
        /**for(int i=0; i<12; i++){
			if (btnCardHand[i].equals(e.getSource())){
				//System.out.println("btnCard "+ i +" gedrückts");
	        	btnCardTable.setText(btnCardHand[i].getText());
	    		btnCardTable.setVisible(true);
				
			}
		}
		**/
    }
	
	public void setController(TableController controller){
		this.controller = controller;	
	}
	
	public void drawGameState(GameState gameState) {
		log.debug(controller.getToken());
		Player player = gameState.getPlayer(controller.getToken());
		
		for( Card card : player.getPlayerCards()){
			BtnCard btnCard = new BtnCard(card);
			btnCard.addActionListener(this);
			btnCardHand.add(btnCard);
			panelCardHand.add(btnCard);	    	
    	}
		
	}
	
} //class end
	

