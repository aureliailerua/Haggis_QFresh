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

import java.awt.event.ActionListener.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import library.Card;
import library.GameState;
import library.Player;


public class TableView extends JFrame implements ActionListener{

	private JFrame frame;
										//Layer
	JPanel panelOpposition;			//1
	JPanel panelDesk;				//2
	JPanel panelPlayer;				//3
	
	JPanel panelLeer; 				//3.1
	JPanel panelPlayerView;			//3.2
	JPanel panelControlContainer;	//3.3
	
	JPanel panelCardHand; 			//3.2.1
	JPanel panelJocker;				//3.2.2
	JPanel panelPlayerInfo;			//3.2.3
	JPanel panelBtnPass;			//3.2.1
	JPanel panelStatusBar;			//3.2.2
	JPanel panelBtnPlay;			//3.2.2

	ArrayList<BtnCard> btnCardHand;
	JButton btnPlay;
    JButton btnPass; 
	JButton btnCardTable;
	JButton[] btnJocker = new JButton[3];

	//TableController controller;
	
	//JButton btnCardTable = new JButton();
	//JButton[] btnJocker	= new JButton[3];
	TableController controller;

	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//connect View with Controller
					// ! TableView view = new TableView();
					TableView view = new TableView();
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
	public TableView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setName("Haggis - Gametable");
		frame.setBounds(100, 100, 1500, 900); // x-Position, y-Position, breite und höhe des Fenster
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		/** 
		 * Opposite Player (1)
		 */
		panelOpposition = new JPanel();
		frame.getContentPane().add(panelOpposition, BorderLayout.NORTH);
		
		/** 
		 * Card Desk (2)
		 */
		panelDesk = new JPanel();
		frame.getContentPane().add(panelDesk, BorderLayout.CENTER);
		btnCardTable = new JButton();
		btnCardTable.setPreferredSize(new Dimension(80,127));
		btnCardTable.setVisible(false);
		panelDesk.add(btnCardTable);
		
		/**
		 * Player (3)
		 */
		panelPlayer = new JPanel();
		frame.getContentPane().add(panelPlayer, BorderLayout.SOUTH);
		panelPlayer.setLayout(new BorderLayout(0, 0));
		
		
		/**
		 * * Player's View *
		 */
		
		// - Empty Space Left (3.1.W)
		panelLeer = new JPanel();
		panelPlayer.add(panelLeer, BorderLayout.WEST);
		
		// - Player's View (3.2.C)
		panelPlayerView = new JPanel();
		panelPlayer.add(panelPlayerView, BorderLayout.CENTER);
		panelPlayerView.setLayout(new BorderLayout(0, 0));
		
		// -- Card Hand (3.2.1.N)
		panelCardHand = new JPanel();
		btnCardHand = new ArrayList<BtnCard>();
		btnCardTable = new JButton();
		panelPlayerView.add(panelCardHand, BorderLayout.NORTH);
		
		// -- Jocker's (3.2.2.C)
		panelJocker = new JPanel();
		btnJocker	= new JButton[3];
		panelPlayerView.add(panelJocker, BorderLayout.CENTER);
		
		// -- Control Panel (3.2.3.S)
		panelPlayerInfo = new JPanel();
		panelPlayerView.add(panelPlayerInfo, BorderLayout.SOUTH);
		
		// --- Pass Area (3.2.3.1.W)
		panelBtnPass = new JPanel();
		panelPlayerInfo.add(panelBtnPass, BorderLayout.WEST);
		btnPass = new JButton();
		btnPass.setText("Passen");
		btnPass.setBackground(Color.GREEN);
		btnPass.setPreferredSize(new Dimension(100,30));
		btnPass.setEnabled(true);
		btnPass.setVisible(true);
		btnPass.addActionListener(this); //generiert Listener
		panelBtnPass.add(btnPass);
		
		// --- Status Bar (3.2.3.2.C)
		panelStatusBar = new JPanel();
		panelPlayerInfo.add(panelStatusBar, BorderLayout.CENTER);
		 
		// --- Play Area (3.2.3.3.E)
		panelBtnPlay = new JPanel();
		panelPlayerInfo.add(panelBtnPlay, BorderLayout.EAST);
		btnPlay = new JButton();
		btnPlay.setText("Play");
		btnPlay.setBackground(Color.GREEN);
		btnPlay.setPreferredSize(new Dimension(100,30));
		btnPlay.setEnabled(true);
		btnPlay.setVisible(true);
		btnPlay.addActionListener(this); //generiert Listener
		panelBtnPlay.add(btnPlay);
		
		// -- Control Container with Buttons (3.3.E)
		panelControlContainer = new JPanel();
		panelPlayer.add(panelControlContainer, BorderLayout.EAST);
	}
	
	public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnPlay){
            System.out.println("Button geklickt!");   
        }
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

}
