package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import library.Card;
import library.GameState;
import library.GameState.PlayerToken;
import library.Player;

/**
 * 
 * @author felicita.acklin
 * Klasse enthält alle GUI-Elemente für den Spieltisch und stellt das ganze Spiel dar.
 * Hauptpanels sind: panelTable, panelOpposition (Gegenspieler) und panelPlayer.
 * 
 * Verbesserungsmöglichkeit: Die Hauptpanel panelTable und panelPlayer hätten in eine separate Klasse
 * verschoben werden sollen, so wäre die Lesbarkeit und Struktur der Klasse erhöht. Konnte aber aus 
 * Zeitgründen nicht realisiert werden.
 */

public class TableView extends JFrame implements ActionListener{

	private JFrame frame;
											//Layer
	JPanelOpposition panel1stOpposition;	//1
	JPanel panelTable;						//2
	JPanelOpposition panel2ndOpposition; 	//3
	JPanel panelEmpty;						//3 alternative
	JPanel panelPlayer;						//4
	
	JPanel panelPatternInfo;				//2.1
	JPanel panelTableCards;					//2.2
	
	JPanel panelCardHand; 					//4.1
	JPanel paneClientInfo; 					//4.2
	JPanel panelPlayerKit;					//4.3
	JPanel panelButtonContainer;			//4.4
	
	JPanel panelJoker;						//4.2.1
	JPanel panelBtnPass;					//4.2.2
	JPanel panelStatusBar;					//4.2.3
	JPanel panelBtnPlay;					//4.2.4
	
	ArrayList<BtnCard> btnCardHand;
	ArrayList<BtnCard> btnJocker;
	ArrayList<BtnCard> btnCardTable;
	
	// Components of panelBtnPlay and panelBtnPlay
	protected JButton btnPass; 
	protected JButton btnPlay;

	// Components of panelButtonContainer
	protected JButton btnSort;
	protected JButton btnExit;	
	protected JButton btnRules;	
	JLabel lblPlaceHolder;

	// Components of panelPlayerInfo
	JLabel imgLabelCard;
	JLabel lbCardCount;
	JLabel imgLabelCrown;
	JLabel lbPoint;
	JLabel lbPlayerName;
	
	// Components or variables for separate frame or class methods
	JLabel imgLabelRules;
	JLabel lbPatternInfo;
	JTextArea clientInfo;
	GridBagConstraints cTable;	
	
	Color active;
	Color inactive;
	Color patterninfo = new Color(255,217,102);
	
	Font infotext = new Font("Comic Sans MS", Font.BOLD, 14);
	private boolean sortByID = true;
	
	// Controller
	TableController controller;
	// Logger
	private static final Logger log = LogManager.getLogger( TableView.class.getName() );


	/**
	 * Create the application.
	 */
	public TableView(TableController controller) {
		this.controller = controller;
		
		// Define the path of the images
		String pathImgBackSmall = "/gameContent/back_small.jpg";
		String pathImgCrown = "/gameContent/crown.png";
		String pathImgExitBtn = "/icons/exit.png";
		String pathImgSortBtn = "/icons/sort.png";
		String pathImgRulesBtn ="/icons/rules.png";
		
		// Define Fonts (only use in this classe)
		Font player = new Font("Comic Sans MS", Font.BOLD, 18);
		Font statusbar = new Font("Comic Sans MS", Font.PLAIN, 14);
		Font button = new Font("comic Sans MS", Font.PLAIN, 16);

		
		// Define Color
		active = new Color(147,196,125); 	//Green
		inactive = new Color(234,153,153);	//Red
		
		// Define Frame
		frame = new JFrame("QFresh Haggis Game - Gametable");
		frame.setBounds(0, 0, 1280, 720); 						// x-Position, y-Position, width, height
		frame.setPreferredSize(new Dimension(1280,720));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().setBackground(Color.WHITE);
		
		
		 // ** 1st Opposite Player (1) **
		 // * Class JPanelOpposition create the hole Opposition Player

		panel1stOpposition = new JPanelOpposition(this);
		panel1stOpposition.setOpaque(false);
		panel1stOpposition.setPreferredSize(new Dimension(300, 320));
		frame.getContentPane().add(panel1stOpposition, BorderLayout.WEST);

		

		 // ** Table (Card Desk) (2) **	
		panelTable = new JPanel();		
		panelTable.setOpaque(false);
		panelTable.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panelTable, BorderLayout.CENTER);
		
		// -- Pattern Info (2.1) --
		// Shows what kind of card combination are on the table
		panelPatternInfo = new JPanel();
		panelPatternInfo = new JPanel();		
		panelPatternInfo.setOpaque(false);
		panelTable.add(panelPatternInfo, BorderLayout.NORTH);
		
		lbPatternInfo = new JLabel();
		panelPatternInfo.add(lbPatternInfo);

		// -- Cards on the tabel (2.2) --
		panelTableCards = new JPanel();
		panelTableCards.setOpaque(false);
		panelTable.add(panelTableCards, BorderLayout.CENTER);

		GridBagLayout gbl_panelTableCards = new GridBagLayout();
		cTable = new GridBagConstraints();
		panelTableCards.setLayout(gbl_panelTableCards); 
		btnCardTable = new ArrayList<BtnCard>();
		

		// ** 2nd Opposition Player (3) **
		// * Class JPanelOpposition create the hole Opposition Player
		
		// Identify if a 3rd player is enter the game
		if (controller.getGameState().playerList.size() == 3) {
			log.debug("Playerlist Size: "+ controller.getGameState().playerList.size());
			panel2ndOpposition = new JPanelOpposition(this);
		} else {
			panel2ndOpposition = new JPanelOpposition();
		}
		panel2ndOpposition.setPreferredSize(new Dimension(300, 320));
		panel2ndOpposition.setOpaque(false);
		frame.getContentPane().add(panel2ndOpposition, BorderLayout.EAST);
		

		
		// ** Player (4) **
		// * Create the main player of the frame in center bottom
		panelPlayer = new JPanel();
		panelPlayer.setPreferredSize(new Dimension(1280,380));		//to fix the size of the player panel - joker/cards of opposit stay in placer
		panelPlayer.setOpaque(false);
		panelPlayer.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panelPlayer, BorderLayout.SOUTH);
		
		// -- Card Hand (4.1) --
		panelCardHand = new JPanel();
		panelCardHand.setPreferredSize(new Dimension(1280, 135));
		panelCardHand.setOpaque(false);
		FlowLayout fl_panelCardHand = (FlowLayout) panelCardHand.getLayout();
		fl_panelCardHand.setHgap(0);
		fl_panelCardHand.setVgap(0);
		btnCardHand = new ArrayList<BtnCard>();
		panelPlayer.add(panelCardHand, BorderLayout.NORTH);
		
		// -- ClientInfo Left (4.2) --
		// Shows information about the game for the main player
		paneClientInfo = new JPanel();
		paneClientInfo.setOpaque(false);
		FlowLayout fl_panelClientInfo = (FlowLayout) paneClientInfo.getLayout();
		fl_panelClientInfo.setAlignment(FlowLayout.CENTER);
		fl_panelClientInfo.setVgap(65);
		paneClientInfo.setPreferredSize(new Dimension(300, 120));
		displayClientInfo("");
		panelPlayer.add(paneClientInfo, BorderLayout.WEST);

		// -- Player's Kit (4.3) --
		// Contains panels for joker cards, play and pass button and status board
		panelPlayerKit = new JPanel();
		panelPlayerKit.setOpaque(false);
		panelPlayer.add(panelPlayerKit, BorderLayout.CENTER);
		panelPlayerKit.setLayout(new BorderLayout(0, 0));
		
		
		// --- Jocker's (4.3.1) ---
		panelJoker = new JPanel();
		panelJoker.setPreferredSize(new Dimension(640, 135));
		panelJoker.setOpaque(false);
		FlowLayout fl_panelJoker = (FlowLayout) panelJoker.getLayout();
		fl_panelJoker.setVgap(0);
		fl_panelJoker.setHgap(0);
		btnJocker	= new ArrayList<BtnCard>();
		panelPlayerKit.add(panelJoker, BorderLayout.NORTH);
		
		// --- Pass Area (4.3.2) ---
		panelBtnPass = new JPanel();
		panelBtnPass.setOpaque(false);
		FlowLayout fl_panelBtnPass = (FlowLayout) panelBtnPass.getLayout();
		fl_panelBtnPass.setAlignment(FlowLayout.RIGHT);
		panelPlayerKit.add(panelBtnPass, BorderLayout.WEST);
		panelBtnPass.setPreferredSize(new Dimension(180,50));
		btnPass = new JButton();
		btnPass.setText("Pass");
		btnPass.setFont(button);
		btnPass.setPreferredSize(new Dimension(130, 58));
		btnPass.setBackground(Color.WHITE);
		btnPass.setEnabled(true);
		btnPass.setVisible(true);
		btnPass.addActionListener(controller);
		panelBtnPass.add(btnPass);
		
		// --- Status Bar (3.3.3) ---
		// Contains player name, count of cards (and icon) and points (and icon)
		panelStatusBar = new JPanel();
		panelStatusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelStatusBar.setBackground(inactive);
		panelStatusBar.setPreferredSize(new Dimension(300,120));
		panelPlayerKit.add(panelStatusBar, BorderLayout.CENTER);
		
		GridBagLayout gbl_panelStatusBar = new GridBagLayout();
		GridBagConstraints cStatusBar = new GridBagConstraints();
		panelStatusBar.setLayout(gbl_panelStatusBar);

		// ---- Card Icon ----
		imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource(pathImgBackSmall)));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cStatusBar.gridx = 0;												//x-Coordination in grid
		cStatusBar.gridy = 0;												//y-Coordination in grid
		cStatusBar.ipady = 10;												//height of the cell
		cStatusBar.insets = new Insets(5,40,5,5); 							//Padding from border (top, left, bottom, right)
		panelStatusBar.add(imgLabelCard, cStatusBar);
	
		// ---- Count of cards ----
		lbCardCount= new JLabel("0");
		lbCardCount.setFont(statusbar);
		lbCardCount.setPreferredSize(new Dimension(50,30));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;							//how tho fill the cell: Both (Vertikal & horizontal)
		cStatusBar.gridx = 1;
		cStatusBar.gridy = 0;
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(lbCardCount, cStatusBar);
		
		// ---- Crown icon ----
		imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource(pathImgCrown)));
		imgLabelCrown.setPreferredSize(new Dimension(25,22));
		cStatusBar = new GridBagConstraints();
		cStatusBar.gridx = 2;		
		cStatusBar.gridy = 0;	
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(imgLabelCrown, cStatusBar);
		
		// ---- Amount of points ----
		lbPoint= new JLabel("0");
		lbPoint.setFont(statusbar);
		lbPoint.setPreferredSize(new Dimension(50,30));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.gridx = 3;
		cStatusBar.gridy = 0;
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(lbPoint, cStatusBar);
		
		//---- Display Player Name ----
		lbPlayerName= new JLabel("", JLabel.CENTER);
		lbPlayerName.setFont(player);
		lbPlayerName.setPreferredSize(new Dimension(50,30));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.ipady = 8;
		cStatusBar.gridwidth = 4;										//summary 4 cells to one
		cStatusBar.gridx = 0;
		cStatusBar.insets = new Insets(5,5,5,10);
		panelStatusBar.add(lbPlayerName, cStatusBar);
		 
		// --- Play Area (3.3.4) ---
		panelBtnPlay = new JPanel();
		panelBtnPlay.setOpaque(false);
		FlowLayout fl_panelBtnPlay = (FlowLayout) panelBtnPlay.getLayout();
		fl_panelBtnPlay.setAlignment(FlowLayout.LEFT);
		panelPlayerKit.add(panelBtnPlay, BorderLayout.EAST);
		panelBtnPlay.setPreferredSize(new Dimension(180,50));
		btnPlay = new JButton();
		btnPlay.setText("Play");
		btnPlay.setFont(button);
		btnPlay.setPreferredSize(new Dimension(130, 58));
		btnPlay.setBackground(Color.WHITE);
		btnPlay.setEnabled(true);
		btnPlay.setVisible(true);
		btnPlay.addActionListener(controller);
		panelBtnPlay.add(btnPlay);
		
		// -- Button container (3.4) --
		// Button to sort cards, show rules and exit the game
		panelButtonContainer = new JPanel();
		panelButtonContainer.setOpaque(false);
		panelButtonContainer.setPreferredSize(new Dimension(300,120));
		panelPlayer.add(panelButtonContainer, BorderLayout.EAST);
		GridBagLayout gbl_panelControlContainer = new GridBagLayout(); 
		GridBagConstraints cContainer = new GridBagConstraints();
		panelButtonContainer.setLayout(gbl_panelControlContainer);

		lblPlaceHolder = new JLabel();
		lblPlaceHolder.setPreferredSize(new Dimension (58,58));
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 0;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,5);
		panelButtonContainer.add(lblPlaceHolder,cContainer);
		
		btnSort = new JButton();
		ImageIcon imgSort = new ImageIcon(TableView.class.getResource(pathImgSortBtn));
		btnSort.setIcon(new ImageIcon(imgSort.getImage().getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH)));
		btnSort.setPreferredSize(new Dimension(58,58));
		btnSort.setBackground(Color.WHITE);
		btnSort.addActionListener(this);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.BOTH;
		cContainer.gridx = 1;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,10);
		panelButtonContainer.add(btnSort, cContainer);
		
		btnRules = new JButton();
		btnRules.setIcon(new ImageIcon(TableView.class.getResource(pathImgRulesBtn)));
		btnRules.setPreferredSize(new Dimension (58,58));
		btnRules.setBackground(Color.WHITE);
		btnRules.addActionListener(this);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 2;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,5);
		panelButtonContainer.add(btnRules,cContainer);
		
		btnExit = new JButton();
		ImageIcon imageIconExit = new ImageIcon(TableView.class.getResource(pathImgExitBtn));
		btnExit.setIcon(new ImageIcon(imageIconExit.getImage().getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH)));
		btnExit.setPreferredSize(new Dimension(58,58));
		btnExit.setBackground(Color.WHITE);
		btnExit.addActionListener(controller);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 2;
		cContainer.gridy = 1;
		cContainer.gridwidth = 1;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(12,0,5,5);
		panelButtonContainer.add(btnExit,cContainer);
	}
	
	
	
	
	/**
	 * Set communication between controller and view
	 */
	public void setController(TableController controller){
		this.controller = controller;	
	}
	public JFrame getJFrame(){
		return frame;
	}
	
	/**
	 * Draw and update the game field
	 * @param gameState
	 */
	public void drawGameState(GameState gameState) {
		log.debug("rendering player " + controller.getToken());
		updatePlayerHand(controller.getPlayer());
		updateTable(gameState);
		updatePlayers();
		displayClientInfo(gameState.getClientInfo());
		displayPatternInfo(gameState.getPatternInfo());
	}
	
	/**
	 * Update all components and informations of the players (inkl. opposition player)
	 */
	public void updatePlayers(){
		// Get active player
		GameState.PlayerToken activePlayerToken = controller.getGameState().getActivePlayer();
		
		// set and update main player
		lbPlayerName.setText(getPlayerName(controller.getPlayer()));
		lbCardCount.setText(Integer.toString(getPlayerCardCount(controller.getPlayer())));
		lbPoint.setText(Integer.toString(getPlayerPoints(controller.getPlayer())));
		
		if ( activePlayerToken == controller.getToken()){
			panelStatusBar.setBackground(active);
		}
		else{
			panelStatusBar.setBackground(inactive);
		}
		
		// set and update opposition player
		Player player2 = controller.getNextPlayer(controller.getPlayer());
		panel1stOpposition.updatePlayer(player2,activePlayerToken);				//invoke updatePlayer of Opposition
		log.debug("Player 1 = "+ controller.getPlayer().getToken() + " Player 2 = " +player2.getToken());
			//Update 3rd player only if he exist
		if ( controller.getGameState().playerList.size() == 3){
			Player player3 = controller.getNextPlayer(player2);
			panel2ndOpposition.updatePlayer(player3,activePlayerToken);			//invoke updatePlayer of Opposition
		}

	}
	
	
	/**
	 * Update components on the panelTable
	 * @param gameState
	 */
	public void updateTable(GameState gameState){
		
		panelTableCards.removeAll();
		
		if (gameState.roundList.size() > 0){
			int gridy = 0;
			int gridx = 0;
			for (int i = 0; i < gameState.getTopCards().size(); i++) {
				BtnCard btnCard = new BtnCard(gameState.getTopCards().get(i));

				if (i >=8) { gridy = 1; gridx= 8; }
				btnCardTable.add(btnCard);
				cTable.gridx = i - gridx;
				cTable.gridy = gridy;
				cTable.ipady = 10;
				cTable.insets = new Insets(0,0,0,0);
				panelTableCards.add(btnCard,cTable);
			}
		}
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
	
	/**
	 * Show the pattern name of the cards panelTable
	 * @param pattern
	 */
	public void displayPatternInfo(String pattern) {
		if (!pattern.isEmpty()) {
			lbPatternInfo.setText(pattern);
			lbPatternInfo.setFont(infotext);
			lbPatternInfo.setForeground(Color.BLACK);
			lbPatternInfo.setOpaque(true);
			lbPatternInfo.setBackground(new Color(255,229,153));
			lbPatternInfo.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
			panelPatternInfo.add(lbPatternInfo);
		} else {
			lbPatternInfo.setText("");
			lbPatternInfo.setOpaque(false);
		}
	}

	
	/**
	 * Get player name
	 * @param player
	 * @return Player + playerNumber
	 */
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}

	/**
	 * Get player's card count
	 * @param player
	 * @return
	 */
	public int getPlayerCardCount(Player player){
		int cardCount = player.getPlayerCards().size();	
		return cardCount;
	}

	/**
	 * 	Get player's points
	 * @param player
	 * @return
	 */
	public int getPlayerPoints(Player player){
		int points = player.getPlayerPoints();		
		return points;
	}
	
	/**
	 * Update and sort all cards of the main player (joker and normal cards)
	 * @param player
	 */
	public void updatePlayerHand(Player player){
		panelCardHand.removeAll();
		panelJoker.removeAll();
		panelCardHand.revalidate();
		panelJoker.revalidate();
		frame.getContentPane().revalidate();
		
		// Initial & Sort Cards
		btnCardHand = new ArrayList<BtnCard>();
		btnJocker = new ArrayList<BtnCard>();
		if (sortByID) {								//Sort cards
			Collections.sort(player.getPlayerCards());
		} else {
			Collections.sort(player.getPlayerCards(), Card.CardSuitComparator);
		}
		for( Card card : player.getPlayerCards()){
			BtnCard btnCard = new BtnCard(card);
			btnCard.addActionListener(controller);
			btnCardHand.add(btnCard); 				//Add to ArrayList
			panelCardHand.add(btnCard);	    	
    	}
		
		Collections.sort(player.getPlayerJokers());
		
		for( Card jocker : player.getPlayerJokers()) {
			BtnCard btnCard = new BtnCard(jocker);
			btnCard.addActionListener(controller);
			btnJocker.add(btnCard); 				//Add to ArrayList
			panelJoker.add(btnCard);
		}
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
	}
	
	/**
	 * Shows information form the game state for each main player
	 * @param message
	 */
	public void displayClientInfo(String message) {
			
		paneClientInfo.removeAll();
		paneClientInfo.revalidate();
		frame.getContentPane().revalidate();


		if (!message.isEmpty()) {
			clientInfo = new JTextArea();
			clientInfo.setText("Player Information:\n" + message);
			clientInfo.setFont(infotext);
			clientInfo.setForeground(Color.WHITE);
			clientInfo.setBackground(new Color(118, 165, 175));
			Border border = BorderFactory.createLineBorder(new Color(19, 79, 92), 2, true);
			clientInfo.setBorder(BorderFactory.createCompoundBorder(border, BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			clientInfo.setPreferredSize(new Dimension(250, 100));
			clientInfo.setLineWrap(true);
			clientInfo.setWrapStyleWord(true);
			clientInfo.setEditable(false);
			paneClientInfo.add(clientInfo);
		} 
		paneClientInfo.repaint();
	}
	
	
	/**
	 * Open frame for combination card
	 */
	public void displayRules(){
    	JFrame frameRules = new JFrame ("Haggis Rules");
    	frameRules.setBounds(200, 200, 510, 326); 							// x-Position, y-Position, breite und höhe des Fenster
        frameRules.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
        ImageIcon combinationCard = new ImageIcon(TableView.class.getResource("/gameContent/rules/combination.jpg"));
        JLabel imgLabelRules = new JLabel(new ImageIcon(combinationCard.getImage().getScaledInstance(510, 326,  java.awt.Image.SCALE_SMOOTH)));
		imgLabelRules.setPreferredSize(new Dimension(510,326));
        frameRules.getContentPane().add(imgLabelRules);
        frameRules.pack();
        frameRules.setVisible(true);
	}

	
	/**
	 * Action Performer for Rule and Sort Button
	 */
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btnRules) {
	    	displayRules();
	    }
	    if (e.getSource() == btnSort ){
	    	if (sortByID)
	    		sortByID = false;
	    	else
	    		sortByID = true;
	    	updatePlayerHand(controller.getPlayer());
	    }
	}
}
