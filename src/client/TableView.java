package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;  
import java.util.ArrayList;
import java.awt.Dimension;  

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
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
	JPanel panelTable;				//2
	JPanel panelPlayer;				//3
	
	JPanel panelCardHand; 			//3.1
	JPanel panelEmpty; 				//3.2
	JPanel panelPlayerKit;			//3.3
	JPanel panelControlContainer;	//3.4
	
	JPanel panelJocker;				//3.2.1
	JPanel panelBtnPass;			//3.2.2
	JPanel panelStatusBar;			//3.2.3
	JPanel panelBtnPlay;			//3.2.4

	ArrayList<BtnCard> btnCardHand;
	JButton btnPlay;
    JButton btnPass; 
	JButton btnCardTable;
	JButton btnSort;
	JButton btnExit;
	JButton btnRules;
	JButton btnBet15;
	JButton btnBet30;
	
	JLabel lbCardNr;
	JLabel lbPoint;

	
	ArrayList<BtnCard> btnJocker;
	
	TableController controller;

	private static final Logger log = LogManager.getLogger( Server.class.getName() );
	
	private JPanel panelEmpty_2;
	private JPanel panelFirstOpposite;
	private JPanel panelSecondOpposite;
	private JPanel panelOppositeInfo;
	private JPanel panelOppositeJocker;
	private JPanel panelOppositeCardBack;
	private JButton btnEmptyButton;
	

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
		panelOpposition.setLayout(new BorderLayout(0, 0));
		
		panelFirstOpposite = new JPanel();
		panelOpposition.add(panelFirstOpposite, BorderLayout.WEST);
		panelFirstOpposite.setLayout(new BorderLayout(0, 0));
		
		panelOppositeInfo = new JPanel();
		panelFirstOpposite.add(panelOppositeInfo, BorderLayout.NORTH);
		
		panelOppositeCardBack = new JPanel();
		panelFirstOpposite.add(panelOppositeCardBack, BorderLayout.CENTER);
		
		panelOppositeJocker = new JPanel();
		panelFirstOpposite.add(panelOppositeJocker, BorderLayout.SOUTH);
		
		panelEmpty_2 = new JPanel();
		panelOpposition.add(panelEmpty_2, BorderLayout.CENTER);
		
		panelSecondOpposite = new JPanel();
		panelOpposition.add(panelSecondOpposite, BorderLayout.EAST);
		
		/** 
		 * Card Desk (2)
		 */
		panelTable = new JPanel();
		frame.getContentPane().add(panelTable, BorderLayout.WEST);
		btnCardTable = new JButton();
		btnCardTable.setPreferredSize(new Dimension(80,127));
		btnCardTable.setVisible(false);
		panelTable.add(btnCardTable);
		
		/**
		 * Player (3)
		 */
		panelPlayer = new JPanel();
		frame.getContentPane().add(panelPlayer, BorderLayout.SOUTH);
		panelPlayer.setLayout(new BorderLayout(0, 0));
		
		
		/**
		 * * Player's View *
		 */
		
		// - Card Hand (3.1.N)
		panelCardHand = new JPanel();
		btnCardHand = new ArrayList<BtnCard>();
		panelPlayer.add(panelCardHand, BorderLayout.NORTH);
		
		// - Empty Space Left (3.2.W)
		panelEmpty = new JPanel();
		btnEmptyButton = new JButton("Empty Button");
		panelPlayer.add(panelEmpty, BorderLayout.WEST);
		panelEmpty.add(btnEmptyButton);
		
		// - Player's Kit (3.3.C)
		panelPlayerKit = new JPanel();
		panelPlayer.add(panelPlayerKit, BorderLayout.CENTER);
		panelPlayerKit.setLayout(new BorderLayout(0, 0));
		

		
		// -- Jocker's (3.3.1.C)
		panelJocker = new JPanel();
		btnJocker	= new ArrayList<BtnCard>();
		panelPlayerKit.add(panelJocker, BorderLayout.NORTH);
		
		// --- Pass Area (3.3.2.W)
		panelBtnPass = new JPanel();
		panelPlayerKit.add(panelBtnPass, BorderLayout.WEST);
		btnPass = new JButton();
		btnPass.setText("Passen");
		btnPass.setBackground(Color.GREEN);
		btnPass.setPreferredSize(new Dimension(100,30));
		btnPass.setEnabled(true);
		btnPass.setVisible(true);
		btnPass.addActionListener(this); //generiert Listener
		panelBtnPass.add(btnPass);
		
		// --- Status Bar (3.3.3.C)
		panelStatusBar = new JPanel();
		panelPlayerKit.add(panelStatusBar, BorderLayout.CENTER);
		
		GridBagLayout gbl_panelStatusBar = new GridBagLayout();
		GridBagConstraints cStatusBar = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelStatusBar.setLayout(gbl_panelStatusBar); 		//Layout dem Panelzuweisen!!

		
		JLabel imgLabelCard = new JLabel(new ImageIcon(StatusBar.class.getResource("/gameContent/rueckseite_klein.jpg")));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cStatusBar.gridx = 1;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(imgLabelCard, cStatusBar);
	
		
		lbCardNr= new JLabel("6");
		lbCardNr.setPreferredSize(new Dimension(50,50));
		cStatusBar.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cStatusBar.gridx = 2;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(lbCardNr, cStatusBar);
		
		JLabel imgLabelCrown = new JLabel(new ImageIcon(StatusBar.class.getResource("/gameContent/krone_klein.png")));
		imgLabelCrown.setPreferredSize(new Dimension(20,16));
		cStatusBar.gridx = 3;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(imgLabelCrown, cStatusBar);
		
		lbPoint= new JLabel("30");
		lbPoint.setPreferredSize(new Dimension(50,50));
		cStatusBar.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		//c.weightx = 0.5;
		//c.gridwidth = 1;
		cStatusBar.gridx = 4;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.insets = new Insets(5,0,0,10); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(lbPoint, cStatusBar);
		 
		// --- Play Area (3.3.4.E)
		panelBtnPlay = new JPanel();
		panelPlayerKit.add(panelBtnPlay, BorderLayout.EAST);
		btnPlay = new JButton();
		btnPlay.setText("Play");
		btnPlay.setBackground(Color.GREEN);
		btnPlay.setPreferredSize(new Dimension(100,30));
		btnPlay.setEnabled(true);
		btnPlay.setVisible(true);
		btnPlay.addActionListener(this); //generiert Listener
		panelBtnPlay.add(btnPlay);
		
		// -- Control Container with Buttons (3.4.E)
		panelControlContainer = new JPanel();
		panelPlayer.add(panelControlContainer, BorderLayout.EAST);
		GridBagLayout gbl_panelControlContainer = new GridBagLayout(); 
		GridBagConstraints c = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelControlContainer.setLayout(gbl_panelControlContainer); 		//Layout dem Panelzuweisen!!

		//panelControlContainer.setBorder( BorderFactory.createEmptyBorder( 2, 2, 2, 2 ) );
		//panelControlContainer.setOpaque( true );
		//panelControlContainer.setBackground( Color.WHITE );
		//c.gridwidth = c.REMAINDER;		//comp be last on in its row	
		
		btnSort= new JButton("Sort");
		btnSort.setPreferredSize(new Dimension(50,50));
		btnSort.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		//c.weightx = 0.5;
		//c.gridwidth = 1;
		c.gridx = 1;		//x-Koordinate im Grid
		c.gridy = 0;		//y-Koordinate im Grid
		c.insets = new Insets(5,0,0,10); //Padding vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnSort, c);
		
		btnRules = new JButton("Help");
		btnRules.setPreferredSize(new Dimension(50,50));
		btnRules.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx= 0.5;
		c.gridx = 2;
		c.gridy = 0;
		c.insets = new Insets(5,0,0,5);; //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnRules,c);

		
		btnBet30 = new JButton("30");
		btnBet30.setPreferredSize(new Dimension(40,40));
		btnBet30.addActionListener(this);
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		c.insets = new Insets(10,10,10,0);; //Abstand vom Displayrand (top, left, bottom, right)
		c.anchor = GridBagConstraints.LINE_END;
		panelControlContainer.add(btnBet30, c);
		
		btnBet15 = new JButton("15");
		btnBet15.setPreferredSize(new Dimension(40,40));
		btnBet15.addActionListener(this);
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(10,0,10,20); //Abstand vom Displayrand (top, left, bottom, right)
		c.anchor = GridBagConstraints.LINE_START;
		panelControlContainer.add(btnBet15,c);
		
		btnExit = new JButton("Exit");
		btnExit.setPreferredSize(new Dimension(50,50));
		btnExit.addActionListener(this);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 1;
		c.insets = new Insets(0,0,5,5); //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnExit,c);
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
			btnCardHand.add(btnCard); //Add to ArrayList
			panelCardHand.add(btnCard);	    	
    	}
		
		for( Card jocker : player.getPlayerJokers()) {
			BtnCard btnCard = new BtnCard(jocker);
			btnCard.addActionListener(this);
			btnJocker.add(btnCard); //Add to ArrayList
			panelJocker.add(btnCard);
		}
		
	}

}
