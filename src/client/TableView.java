package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;  
import java.util.ArrayList;
import java.util.Iterator;
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

	
	JPanel panelEmpty_2;			//1.1
	JPanel panelFirstOpposite;		//1.2
	JPanel panelSecondOpposite;		//1.3
	
	JPanel panelOppositeInfo;		//1.1.1
	JPanel panelOppositeJocker;		//1.1.2
	JPanel panelOppositeCardBack;	//1.1.3
	
	
	ArrayList<BtnCard> btnCardHand;
	ArrayList<BtnCard> btnJocker;
	ArrayList<BtnCard> btnCardTable;
	
	JButton btnPlay;
    JButton btnPass; 
	JButton btnSort;
	JButton btnExit;
	JButton btnRules;
	JButton btnBet15;
	JButton btnBet30;
	JButton btnEmptyButton;

	
	JLabel lbCardCount;
	JLabel lbPoint;
	JLabel lbPlayerName;
	JLabel imgLabelCard;
	JLabel imgLabelCrown;
	JLabel imgLabelRules;
	
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
		
		// - 1. Opposite Player (1.1.W)
		panelFirstOpposite = new JPanel();
		panelOpposition.add(panelFirstOpposite, BorderLayout.WEST);
		panelFirstOpposite.setLayout(new BorderLayout(0, 0));
		
		// -- StatusBar (1.1.1N)
		panelOppositeInfo = new JPanel();
		panelFirstOpposite.add(panelOppositeInfo, BorderLayout.NORTH);
				
		GridBagLayout gbl_OppositeInfo = new GridBagLayout();
		GridBagConstraints cOppositeInfo = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelOppositeInfo.setLayout(gbl_OppositeInfo); 		//Layout dem Panelzuweisen!!
		panelOppositeInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppositeInfo.setBackground(Color.RED);
		
		JLabel imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/rueckseite_klein.jpg")));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cOppositeInfo.gridx = 0;		//x-Koordinate im Grid
		cOppositeInfo.gridy = 0;		//y-Koordinate im Grid
		cOppositeInfo.ipady = 10;
		cOppositeInfo.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelOppositeInfo.add(imgLabelCard, cOppositeInfo);
	
		
		lbCardCount= new JLabel("6"); //!! Anpassen!!
		lbCardCount.setPreferredSize(new Dimension(50,50));
		cOppositeInfo.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cOppositeInfo.gridx = 1;		//x-Koordinate im Grid
		cOppositeInfo.gridy = 0;		//y-Koordinate im Grid
		cOppositeInfo.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelOppositeInfo.add(lbCardCount, cOppositeInfo);
		
		JLabel imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/krone_klein.png")));
		imgLabelCrown.setPreferredSize(new Dimension(20,16));
		cOppositeInfo.gridx = 2;		
		cOppositeInfo.gridy = 0;		
		cOppositeInfo.insets = new Insets(5,5,5,5);
		panelOppositeInfo.add(imgLabelCrown, cOppositeInfo);
		
		lbPoint= new JLabel("30");
		lbPoint.setPreferredSize(new Dimension(50,50));
		cOppositeInfo.fill = GridBagConstraints.BOTH;
		//c.weightx = 0.5;
		//c.gridwidth = 1;
		cOppositeInfo.gridx = 3;
		cOppositeInfo.gridy = 0;
		cOppositeInfo.insets = new Insets(5,0,0,10);
		panelOppositeInfo.add(lbPoint, cOppositeInfo);
		
		lbPlayerName= new JLabel("Player 2", JLabel.CENTER);
		lbPlayerName.setPreferredSize(new Dimension(50,50));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		cOppositeInfo.fill = GridBagConstraints.BOTH;
		cOppositeInfo.ipady = 10;
		cOppositeInfo.weightx = 0.0;
		cOppositeInfo.gridwidth = 4;
		cOppositeInfo.gridx = 0;
		cOppositeInfo.gridy = 1;
		cOppositeInfo.insets = new Insets(5,0,0,10);
		panelOppositeInfo.add(lbPlayerName, cOppositeInfo);
		
		// -- CardBack (1.1.2.C)
		panelOppositeCardBack = new JPanel();
		panelFirstOpposite.add(panelOppositeCardBack, BorderLayout.CENTER);
		
		// -- Jocker (1.1.3.S)
		panelOppositeJocker = new JPanel();
		//btnJocker	= new ArrayList<BtnCard>();
		panelFirstOpposite.add(panelOppositeJocker, BorderLayout.SOUTH);
		
		// - Empty Space (1.2.C)
		panelEmpty_2 = new JPanel();
		panelOpposition.add(panelEmpty_2, BorderLayout.CENTER);
		
		// - 2. Opposite Player (1.3.E)
		panelSecondOpposite = new JPanel();
		panelOpposition.add(panelSecondOpposite, BorderLayout.EAST);
		
		
		/** 
		 * Card Desk (2)
		 */
		panelTable = new JPanel();
		btnCardTable = new ArrayList<BtnCard>();
		frame.getContentPane().add(panelTable, BorderLayout.WEST);
		
		
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
		panelPlayerKit.setSize(new Dimension(50,200));
		
		
		// -- Jocker's (3.3.1.C)
		panelJocker = new JPanel();
		btnJocker	= new ArrayList<BtnCard>();
		panelPlayerKit.add(panelJocker, BorderLayout.NORTH);
		
		// --- Pass Area (3.3.2.W)
		panelBtnPass = new JPanel();
		panelPlayerKit.add(panelBtnPass, BorderLayout.WEST);
		panelBtnPass.setSize(new Dimension(650,50));
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
		panelStatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelStatusBar.setBackground(Color.GREEN);
		
		imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/rueckseite_klein.jpg")));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cStatusBar.gridx = 0;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.ipady = 10;
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(imgLabelCard, cStatusBar);
	
		
		lbCardCount= new JLabel("6"); //!! Anpassen!!
		lbCardCount.setPreferredSize(new Dimension(50,50));
		cStatusBar.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cStatusBar.gridx = 1;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(lbCardCount, cStatusBar);
		
		imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/krone_klein.png")));
		imgLabelCrown.setPreferredSize(new Dimension(20,16));
		cStatusBar.gridx = 2;		
		cStatusBar.gridy = 0;		
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(imgLabelCrown, cStatusBar);
		
		lbPoint= new JLabel("30");
		lbPoint.setPreferredSize(new Dimension(50,50));
		cStatusBar.fill = GridBagConstraints.BOTH;
		//c.weightx = 0.5;
		//c.gridwidth = 1;
		cStatusBar.gridx = 3;
		cStatusBar.gridy = 0;
		cStatusBar.insets = new Insets(5,0,0,10);
		panelStatusBar.add(lbPoint, cStatusBar);
		
		lbPlayerName= new JLabel("Player 1", JLabel.CENTER);
		lbPlayerName.setPreferredSize(new Dimension(50,50));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.ipady = 10;
		cStatusBar.weightx = 0.0;
		cStatusBar.gridwidth = 4;
		cStatusBar.gridx = 0;
		cStatusBar.gridy = 1;
		cStatusBar.insets = new Insets(5,0,0,10);
		panelStatusBar.add(lbPlayerName, cStatusBar);
		 
		// --- Play Area (3.3.4.E)
		panelBtnPlay = new JPanel();
		panelPlayerKit.add(panelBtnPlay, BorderLayout.EAST);
		panelBtnPlay.setSize(new Dimension(650,50));
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
		GridBagConstraints cContainer = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelControlContainer.setLayout(gbl_panelControlContainer); 		//Layout dem Panelzuweisen!!

		//panelControlContainer.setBorder( BorderFactory.createEmptyBorder( 2, 2, 2, 2 ) );
		//panelControlContainer.setOpaque( true );
		//panelControlContainer.setBackground( Color.WHITE );
		//c.gridwidth = c.REMAINDER;		//comp be last on in its row	
		
		btnSort = new JButton();
		btnSort.setIcon(new ImageIcon(TableView.class.getResource("/gameContent/sort.png")));
		btnSort.setPreferredSize(new Dimension(68,68));
		btnSort.addActionListener(this);
		cContainer.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		//c.weightx = 0.5;
		//c.gridwidth = 1;
		cContainer.gridx = 1;		//x-Koordinate im Grid
		cContainer.gridy = 0;		//y-Koordinate im Grid
		cContainer.insets = new Insets(5,0,0,10); //Padding vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnSort, cContainer);
		
		btnRules = new JButton();
		btnRules.setIcon(new ImageIcon(TableView.class.getResource("/gameContent/rules.png")));
		btnRules.setPreferredSize(new Dimension (68,68));
		btnRules.addActionListener(this);
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx= 0.5;
		cContainer.gridx = 2;
		cContainer.gridy = 0;
		cContainer.insets = new Insets(5,0,0,5);; //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnRules,cContainer);

		
		btnBet30 = new JButton("30");
		btnBet30.setPreferredSize(new Dimension(40,40));
		btnBet30.addActionListener(this);
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.weightx = 0.5;
		cContainer.gridx = 0;
		cContainer.gridy = 1;
		cContainer.insets = new Insets(10,10,10,0);; //Abstand vom Displayrand (top, left, bottom, right)
		cContainer.anchor = GridBagConstraints.LINE_END;
		panelControlContainer.add(btnBet30, cContainer);
		
		btnBet15 = new JButton("15");
		btnBet15.setPreferredSize(new Dimension(40,40));
		btnBet15.addActionListener(this);
		cContainer.fill = GridBagConstraints.BOTH;
		cContainer.gridx = 1;
		cContainer.gridy = 1;
		cContainer.insets = new Insets(10,0,10,20); //Abstand vom Displayrand (top, left, bottom, right)
		cContainer.anchor = GridBagConstraints.LINE_START;
		panelControlContainer.add(btnBet15,cContainer);
		
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(TableView.class.getResource("/gameContent/home.png")));
		btnExit.setPreferredSize(new Dimension(68,68));
		btnExit.addActionListener(this);
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 2;
		cContainer.gridy = 1;
		cContainer.insets = new Insets(0,0,5,5); //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnExit,cContainer);
	}
	
	
	public void actionPerformed(ActionEvent e) {
        
        for( BtnCard btnSelected : btnCardHand){
        	if(e.getSource() == btnSelected) {
        		if (btnSelected.isSelected() == false){
        			btnSelected.setSelected();
        		}   else if (btnSelected.isSelected() == true) {
        			btnSelected.setUnselected();
        		}
        	}
        }
        
        for( BtnCard btnSelected : btnJocker){
        	if(e.getSource() == btnSelected) {
        		if (btnSelected.isSelected() == false){
        			btnSelected.setSelected();
        		}   else if (btnSelected.isSelected() == true) {
        			btnSelected.setUnselected();
        		}
        	}
        }
        
        if(e.getSource() == btnPlay){
        	for (BtnCard btnSelected : btnCardHand) {
        		if(btnSelected.isSelected() == true) {
            		System.out.print("Yeah you selected something");
        			btnCardTable.add(btnSelected);
            		//panelTable.add(btnSelected);
        		}
        	}
        	for (BtnCard btnSelected : btnJocker) {
        		if(btnSelected.isSelected() == true) {
            		System.out.print("Yeah you selected Jocker");
        			btnCardTable.add(btnSelected);
        		}
        	}
        	
        	for (BtnCard btnChoice : btnCardTable) {
        		//btnChoice.setVisible(true);
        		panelTable.add(btnChoice);
        	}
        }
        
        if(e.getSource() == btnPass){
        	for (BtnCard btnSelected : btnCardHand) {
        		if(btnSelected.isSelected() == true) {
        			btnSelected.setUnselected();
        		}
        	}
        	
        }
        
        if(e.getSource() == btnExit) {
    		System.exit(0);
        }
        
        if (e.getSource() == btnRules) {
        	JFrame frameRules = new JFrame ("Haggis Rules");
        	frameRules.setBounds(200, 200, 510, 326); // x-Position, y-Position, breite und höhe des Fenster
            frameRules.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
            imgLabelRules = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/Kombinationen.jpg")));
    		imgLabelRules.setPreferredSize(new Dimension(510,326));
            frameRules.add(imgLabelRules);
            frameRules.pack();
            frameRules.setVisible(true);
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
