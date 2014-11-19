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
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.Move;
import server.Round;
import server.Server;
import server.Tick;

import java.awt.event.ActionListener.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

import library.Card;
import library.GameState;
import library.Player;


public class TableView extends JFrame{

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

	JPanel panelBet;				//3.4.1
	
	JPanel panelEmpty_2;			//1.1
	JPanel panelFirstOpposite;		//1.2
	JPanel panelSecondOpposite;		//1.3
	
	JPanel panelOppositeInfo;		//1.1.1
	JPanel panelOppositeJocker;		//1.1.2
	JPanel panelOppositeCardBack;	//1.1.3
	
	JPanel panel1OppositeBet;		//1.1.1.1
	JPanel panel1OppositeStatusBar;	//1.1.1.2
	JPanel panel1OppositeEmpty;		//1.1.1.3
	
	JPanel panel2OppositeBet;		//1.3.1.1
	JPanel panel2OppositeStatusBar;	//1.3.1.2
	JPanel panel2OppositeEmpty;		//1.3.1.3
	
	ArrayList<BtnCard> btnCardHand;
	ArrayList<BtnCard> btnJocker;
	ArrayList<BtnCard> btnCardTable;
	
	protected JButton btnPlay;
	protected JButton btnPass; 
	protected JButton btnSort;
	protected JButton btnExit;
	protected JButton btnRules;
	protected JButton btnBet15;
	protected JButton btnBet30;
	protected JButton btnEmptyButton;

	
	JLabel lbCardCount;
	JLabel lbPoint;
	JLabel lbPlayerName;
	JLabel imgLabelCard;
	JLabel imgLabelCrown;
	JLabel imgLabelRules;
	JLabel lblPlaceHolder;
	JLabel imgLabelCardBack;
	
	TableController controller;

	private static final Logger log = LogManager.getLogger( TableView.class.getName() );


	/**
	 * Create the application.
	 */
	public TableView(TableController controller) {

		this.controller = controller;
		
		frame = new JFrame();
		frame.setName("Haggis - Gametable");
		frame.setBounds(0, 0, 1280, 720); // x-Position, y-Position, breite und höhe des Fenster
		frame.setPreferredSize(new Dimension(1280,720));
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
		
		// -- Opposite Info (1.1.1.N)
		panelOppositeInfo = new JPanel();
		panelFirstOpposite.add(panelOppositeInfo, BorderLayout.NORTH);
		
		// --- Bet (1.1.1.1.W)
		panel1OppositeBet = new JPanel();
		FlowLayout fl_panel1OppositeBet = (FlowLayout) panel1OppositeBet.getLayout();
		fl_panel1OppositeBet.setAlignment(FlowLayout.RIGHT);
		panelOppositeInfo.add(panel1OppositeBet, BorderLayout.WEST);
		
		TitledBorder bet1Title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Bet");
		bet1Title.setTitleJustification(TitledBorder.LEFT);
		panel1OppositeBet.setBorder( bet1Title);		
		btnBet30 = new JButton("30");
		btnBet30.setPreferredSize(new Dimension(40,40));
		btnBet30.setVisible(false);
		panel1OppositeBet.add(btnBet30);

		btnBet15 = new JButton("15");
		btnBet15.setPreferredSize(new Dimension(40,40));
		btnBet15.setVisible(true);
		panel1OppositeBet.add(btnBet15);
		
		
		// -- StatusBar (1.1.1.2.C)
		panel1OppositeStatusBar = new JPanel();
		panel1OppositeStatusBar.setPreferredSize(new Dimension(162,90) );
		panelOppositeInfo.add(panel1OppositeStatusBar, BorderLayout.CENTER);

		GridBagLayout gbl_OppositeInfo = new GridBagLayout();
		GridBagConstraints cOppositeInfo = new GridBagConstraints();	//GridBag Grenzen erstellen
		panel1OppositeStatusBar.setLayout(gbl_OppositeInfo); 		//Layout dem Panelzuweisen!!
		panel1OppositeStatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panel1OppositeStatusBar.setBackground(Color.RED);		//!! Aktiver Player!!!
		
		lbPlayerName= new JLabel("Player 2", JLabel.CENTER);
		lbPlayerName.setPreferredSize(new Dimension(50,50));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		cOppositeInfo.fill = GridBagConstraints.BOTH;
		cOppositeInfo.ipady = 10;
		cOppositeInfo.weightx = 0.0;
		cOppositeInfo.gridwidth = 5;
		cOppositeInfo.gridx = 0;
		cOppositeInfo.gridy = 0;
		cOppositeInfo.insets = new Insets(0,10,5,0);
		panel1OppositeStatusBar.add(lbPlayerName, cOppositeInfo);
		
		JLabel imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/rueckseite_klein.jpg")));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cOppositeInfo = new GridBagConstraints();
		cOppositeInfo.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cOppositeInfo.gridx = 0;		//x-Koordinate im Grid
		cOppositeInfo.gridy = 1;		//y-Koordinate im Grid
		cOppositeInfo.ipady = 10;
		cOppositeInfo.insets = new Insets(5,1,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panel1OppositeStatusBar.add(imgLabelCard, cOppositeInfo);
	
		
		lbCardCount= new JLabel("6"); //!! Anpassen!!
		lbCardCount.setPreferredSize(new Dimension(50,50));
		cOppositeInfo = new GridBagConstraints();
		cOppositeInfo.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cOppositeInfo.gridx = 1;		//x-Koordinate im Grid
		cOppositeInfo.gridy = 1;		//y-Koordinate im Grid
		cOppositeInfo.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panel1OppositeStatusBar.add(lbCardCount, cOppositeInfo);
		
		JLabel imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/krone.png")));
		imgLabelCrown.setPreferredSize(new Dimension(25,22));
		cOppositeInfo = new GridBagConstraints();
		cOppositeInfo.fill = GridBagConstraints.BOTH;
		cOppositeInfo.gridx = 2;		
		cOppositeInfo.gridy = 1;		
		cOppositeInfo.insets = new Insets(5,5,5,5);
		panel1OppositeStatusBar.add(imgLabelCrown, cOppositeInfo);
		
		lbPoint= new JLabel("10");
		lbPoint.setPreferredSize(new Dimension(50,50));
		cOppositeInfo = new GridBagConstraints();
		cOppositeInfo.fill = GridBagConstraints.BOTH;
		cOppositeInfo.gridx = 3;
		cOppositeInfo.gridy = 1;
		cOppositeInfo.insets = new Insets(5,0,0,1);
		panel1OppositeStatusBar.add(lbPoint, cOppositeInfo);
	
		
		// -- CardBack (1.1.2.C)
		panelOppositeCardBack = new JPanel();
		panelFirstOpposite.add(panelOppositeCardBack, BorderLayout.CENTER);
		
		GridBagLayout gbl_OppositeCardBack = new GridBagLayout();
		GridBagConstraints cOppositeCardBack = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelOppositeCardBack.setLayout(gbl_OppositeCardBack); 		//Layout dem Panelzuweisen!!
		
		imgLabelCardBack = new JLabel(new ImageIcon(CardBack.class.getResource("/gameContent/rueckseite.jpg")));
		imgLabelCardBack.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelCardBack.setPreferredSize(new Dimension(24,79));
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppositeCardBack.fill = GridBagConstraints.BOTH;
		cOppositeCardBack.gridwidth = 1;
		cOppositeCardBack.gridx = 0;
		cOppositeCardBack.gridy = 0;
		cOppositeCardBack.anchor = GridBagConstraints.LINE_END;
		cOppositeCardBack.insets = new Insets(0,20,0,0); //Padding vom Displayrand (top, left, bottom, right)
		panelOppositeCardBack.add(imgLabelCardBack, cOppositeCardBack);
		
		imgLabelCardBack = new JLabel(new ImageIcon(CardBack.class.getResource("/gameContent/rueckseite.jpg")));
		imgLabelCardBack.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelCardBack.setPreferredSize(new Dimension(24,79));
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppositeCardBack = new GridBagConstraints();
		cOppositeCardBack.fill = GridBagConstraints.BOTH;
		cOppositeCardBack.gridx = 1;
		cOppositeCardBack.gridy = 0;
		cOppositeCardBack.anchor = GridBagConstraints.LINE_END;
		panelOppositeCardBack.add(imgLabelCardBack, cOppositeCardBack);
		
		imgLabelCardBack = new JLabel(new ImageIcon(CardBack.class.getResource("/gameContent/rueckseite.jpg")));
		imgLabelCardBack.setPreferredSize(new Dimension(50,79));
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppositeCardBack = new GridBagConstraints();
		cOppositeCardBack.fill = GridBagConstraints.BOTH;
		cOppositeCardBack.gridx = 2;
		cOppositeCardBack.gridy = 0;
		panelOppositeCardBack.add(imgLabelCardBack, cOppositeCardBack);
		
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
		FlowLayout fl_panelBtnPass = (FlowLayout) panelBtnPass.getLayout();
		fl_panelBtnPass.setAlignment(FlowLayout.RIGHT);
		panelPlayerKit.add(panelBtnPass, BorderLayout.WEST);
		panelBtnPass.setPreferredSize(new Dimension(300,50));
		btnPass = new JButton();
		btnPass.setText("Pass");
		btnPass.setBackground(Color.GREEN);
		btnPass.setPreferredSize(new Dimension(130, 58));
		btnPass.setEnabled(true);
		btnPass.setVisible(true);
		btnPass.addActionListener(controller); //generiert Listener
		panelBtnPass.add(btnPass);
		
		// --- Status Bar (3.3.3.C)
		panelStatusBar = new JPanel();
		panelStatusBar.setPreferredSize(new Dimension(300,120));
		panelPlayerKit.add(panelStatusBar, BorderLayout.CENTER);
		
		GridBagLayout gbl_panelStatusBar = new GridBagLayout();
		GridBagConstraints cStatusBar = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelStatusBar.setLayout(gbl_panelStatusBar); 		//Layout dem Panelzuweisen!!
		panelStatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelStatusBar.setBackground(Color.GREEN); //!! Aktiver Spieler !!
		
		imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/rueckseite_klein.jpg")));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cStatusBar.gridx = 0;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.ipady = 10;
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(imgLabelCard, cStatusBar);
	
		
		lbCardCount= new JLabel("6"); //!! Anpassen!!
		lbCardCount.setPreferredSize(new Dimension(50,50));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cStatusBar.gridx = 1;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(lbCardCount, cStatusBar);
		
		imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/krone.png")));
		imgLabelCrown.setPreferredSize(new Dimension(25,22));
		cStatusBar = new GridBagConstraints();
		cStatusBar.gridx = 2;		
		cStatusBar.gridy = 0;		
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(imgLabelCrown, cStatusBar);
		
		lbPoint= new JLabel("20");
		lbPoint.setPreferredSize(new Dimension(50,50));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.gridx = 3;
		cStatusBar.gridy = 0;
		cStatusBar.insets = new Insets(5,0,0,10);
		panelStatusBar.add(lbPoint, cStatusBar);
		
		lbPlayerName= new JLabel(controller.getPlayerName(), JLabel.CENTER);
		lbPlayerName.setPreferredSize(new Dimension(50,50));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
		cStatusBar = new GridBagConstraints();
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
		FlowLayout fl_panelBtnPlay = (FlowLayout) panelBtnPlay.getLayout();
		fl_panelBtnPlay.setAlignment(FlowLayout.LEFT);
		panelPlayerKit.add(panelBtnPlay, BorderLayout.EAST);
		panelBtnPlay.setPreferredSize(new Dimension(300,50));
		btnPlay = new JButton();
		btnPlay.setText("Play");
		btnPlay.setBackground(Color.GREEN);
		btnPlay.setPreferredSize(new Dimension(130, 58));
		btnPlay.setEnabled(true);
		btnPlay.setVisible(true);
		btnPlay.addActionListener(controller); //generiert Listener
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
		
		lblPlaceHolder = new JLabel();
		lblPlaceHolder.setPreferredSize(new Dimension (68,68));
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 0;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,5);; //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(lblPlaceHolder,cContainer);
		
		btnSort = new JButton();
		btnSort.setIcon(new ImageIcon(TableView.class.getResource("/icons/sort.png")));
		btnSort.setPreferredSize(new Dimension(68,68));
		btnSort.addActionListener(controller);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cContainer.gridx = 1;		//x-Koordinate im Grid
		cContainer.gridy = 0;		//y-Koordinate im Grid
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,10); //Padding vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnSort, cContainer);
		
		btnRules = new JButton();
		btnRules.setIcon(new ImageIcon(TableView.class.getResource("/icons/rules.png")));
		btnRules.setPreferredSize(new Dimension (68,68));
		btnRules.addActionListener(controller);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 2;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,5);; //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnRules,cContainer);

		panelBet = new JPanel();
		TitledBorder betPlayerTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "Bet");
		betPlayerTitle.setTitleJustification(TitledBorder.LEFT);
		panelBet.setBorder( betPlayerTitle);
		cContainer = new GridBagConstraints();
		cContainer.gridx = 0;
		cContainer.gridy = 1;
		cContainer.gridwidth = 2;
		cContainer.anchor = GridBagConstraints.LINE_END;
		cContainer.insets = new Insets(5,0,0,10); // top, left, bottom, right
		panelControlContainer.add(panelBet, cContainer);
		
		btnBet30 = new JButton("30");
		btnBet30.setPreferredSize(new Dimension(40,40));
		btnBet30.addActionListener(controller);
		panelBet.add(btnBet30);

		btnBet15 = new JButton("15");
		btnBet15.setPreferredSize(new Dimension(40,40));
		btnBet15.addActionListener(controller);
		panelBet.add(btnBet15);
		
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(TableView.class.getResource("/icons/home.png")));
		btnExit.setPreferredSize(new Dimension(68,68));
		btnExit.addActionListener(controller);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 2;
		cContainer.gridy = 1;
		cContainer.gridwidth = 1;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(12,0,5,5); //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnExit,cContainer);
	}
	
	public JFrame getJFrame(){
		return frame;
	}
	public void setController(TableController controller){
		this.controller = controller;	
	}
	
	public void drawGameState(GameState gameState) {
		log.debug("rendering player " + controller.getToken());
		Player player = gameState.getPlayer(controller.getToken());
		updatePlayerHand(player);
		updateTable(gameState);

	}
	public void updateTable(GameState gameState){
		
		panelTable.removeAll();
		if (gameState.roundList.size() > 0){
			Round round = gameState.roundList.get(gameState.roundList.size()-1);
			Tick tick = round.tickList.get(round.tickList.size()-1);
			Move move = tick.moveList.get(tick.moveList.size()-1);
			for (Card card:  move.getCardList()){
				BtnCard btnCard = new BtnCard(card);
				btnCardTable.add(btnCard);
				panelTable.add(btnCard);
			}
		}
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	
	}
	
	public void updatePlayerHand(Player player){
		panelCardHand.removeAll();
		panelJocker.removeAll();
		panelCardHand.revalidate();
		panelJocker.revalidate();
		frame.getContentPane().revalidate();
		
		btnCardHand = new ArrayList<BtnCard>();
		btnJocker = new ArrayList<BtnCard>();
		
		for( Card card : player.getPlayerCards()){
			BtnCard btnCard = new BtnCard(card);
			btnCard.addActionListener(controller);
			btnCardHand.add(btnCard); //Add to ArrayList
			panelCardHand.add(btnCard);	    	
    	}
		
		for( Card jocker : player.getPlayerJokers()) {
			BtnCard btnCard = new BtnCard(jocker);
			btnCard.addActionListener(controller);
			btnJocker.add(btnCard); //Add to ArrayList
			panelJocker.add(btnCard);
		}
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		
	}
	
	public void displayRules(){
    	JFrame frameRules = new JFrame ("Haggis Rules");
    	frameRules.setBounds(200, 200, 510, 326); // x-Position, y-Position, breite und höhe des Fenster
        frameRules.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        JLabel imgLabelRules = new JLabel(new ImageIcon(TableView.class.getResource("/gameContent/Kombinationen.jpg")));
		imgLabelRules.setPreferredSize(new Dimension(510,326));
        frameRules.getContentPane().add(imgLabelRules);
        frameRules.pack();
        frameRules.setVisible(true);
	}
}
