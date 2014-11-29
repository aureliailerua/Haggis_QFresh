package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.net.URL;  
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import javax.swing.border.EtchedBorder;
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
import library.GameState.PlayerToken;
import library.Player;


public class TableView extends JFrame implements ActionListener{

	private JFrame frame;
										//Layer
	JPanelOpposition panel1stOpposition;	//1W
	JPanel panelTable;				//2 C
	JPanelOpposition panel2stOpposition; 	//3E
	JPanel panelPlayer;				//4 N
	
	JPanel panelCardHand; 			//3.1
	JPanel panelEmpty; 				//3.2
	JPanel panelPlayerKit;			//3.3
	JPanel panelControlContainer;	//3.4
	
	JPanel panelJocker;				//3.2.1
	JPanel panelBtnPass;			//3.2.2
	JPanel panelStatusBar;			//3.2.3
	JPanel panelBtnPlay;			//3.2.4

	JPanel panelBet;				//3.4.1
	
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
	JLabel imgPlaceholder;
	
	GridBagConstraints cTable;	

	
	String pathImgBack;
	String pathImgBackSmall;
	String pathImgCrown;
	String pathImgHomeBtn;
	String pathImgSortBtn;
	String pathImgRulesBtn;
	
	Color active;
	Color inactive;
	Color coints;
	
	private boolean sortByID = true;
	
	TableController controller;

	private static final Logger log = LogManager.getLogger( TableView.class.getName() );


	/**
	 * Create the application.
	 */
	public TableView(TableController controller) {

		this.controller = controller;
		
		// Initial the path of the images
		pathImgBack = "/gameContent/back.jpg";
		pathImgBackSmall = "/gameContent/back_small.jpg";
		pathImgCrown = "/gameContent/crown.png";
		pathImgHomeBtn = "/icons/home.png";
		pathImgSortBtn = "/icons/sort.png";
		pathImgRulesBtn ="/icons/rules.png";
		
		// Initial Color
		active = new Color(147,196,125); 	//Green
		inactive = new Color(234,153,153);	//Red
		coints = new Color(255,217,102);	//Yellow
		
		//bgt String pathImgBackground = "/icons/wood_table.jpg";
		frame = new JFrame();
		frame.setName("Haggis - Gametable");
		frame.setBounds(0, 0, 1280, 720); // x-Position, y-Position, breite und höhe des Fenster
		frame.setPreferredSize(new Dimension(1280,720));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().setBackground(Color.WHITE);
		//Background Image Test
		//bgt JLabel lblBackground = new JLabel(new ImageIcon(pathImgBackground));
		//bgt JLabel lblBackground = new JLabel(new ImageIcon(TableView.class.getResource(pathImgBackground)));
		//bgt JPanel panelBG = new JPanel();
		//bgt panelBG.add(lblBackground);
		//bgt frame.getContentPane().add(panelBG);
		
		/** 
		 * 1st Opposite Player (1.W)
		 */
		panel1stOpposition = new JPanelOpposition(this, "Player 2", 8, 120, 30, "LEFT");
		panel1stOpposition.setOpaque(false);
		panel1stOpposition.setPreferredSize(new Dimension(300, 320));
		frame.getContentPane().add(panel1stOpposition, BorderLayout.WEST);
		//bgt panelBG.add(panel1stOpposition, BorderLayout.WEST);
		
		
		/**
		 * Table (Card Desk) (2.C)
		 */		
		panelTable = new JPanel();
		//FlowLayout fl_panelTable = (FlowLayout) panelTable.getLayout();
		//fl_panelTable.setHgap(0);
		//fl_panelTable.setVgap(0);
		
		GridBagLayout gbl_panelTable = new GridBagLayout();
		cTable = new GridBagConstraints();
		panelTable.setLayout(gbl_panelTable); 		
		panelTable.setOpaque(false);
		frame.getContentPane().add(panelTable, BorderLayout.CENTER);
		btnCardTable = new ArrayList<BtnCard>();
		//bgt panelBG.add(panelTable, BorderLayout.CENTER);
		
		/**
		 * 2nd Opposition Player (3.E)
		 */
		panel2stOpposition = new JPanelOpposition(this, "Player 3", 10, 150, 0, "RIGHT");
		panel2stOpposition.setPreferredSize(new Dimension(300, 320));
		panel2stOpposition.setOpaque(false);
		frame.getContentPane().add(panel2stOpposition, BorderLayout.EAST);
		//bgt panelBG.add(panel2stOpposition, BorderLayout.EAST);
		
		/**
		 * Player (4.N)
		 */
		panelPlayer = new JPanel();
		panelPlayer.setOpaque(false);
		panelPlayer.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(panelPlayer, BorderLayout.SOUTH);
		//bgt panelBG.add(panelPlayer, BorderLayout.SOUTH);
		
		// - Card Hand (3.1.N)
		panelCardHand = new JPanel();
		panelCardHand.setOpaque(false);
		FlowLayout fl_panelCardHand = (FlowLayout) panelCardHand.getLayout();
		fl_panelCardHand.setHgap(0);
		fl_panelCardHand.setVgap(0);
		btnCardHand = new ArrayList<BtnCard>();
		panelPlayer.add(panelCardHand, BorderLayout.NORTH);
		
		// - Empty Space Left (3.2.W)
		panelEmpty = new JPanel();
		panelEmpty.setOpaque(false);
		imgPlaceholder = new JLabel("");
		imgPlaceholder.setPreferredSize(new Dimension(200, 30));
		panelPlayer.add(panelEmpty, BorderLayout.WEST);
		panelEmpty.add(imgPlaceholder);
		
		// - Player's Kit (3.3.C)
		panelPlayerKit = new JPanel();
		panelPlayerKit.setOpaque(false);
		panelPlayer.add(panelPlayerKit, BorderLayout.CENTER);
		panelPlayerKit.setLayout(new BorderLayout(0, 0));
		//panelPlayerKit.setSize(new Dimension(50,200));
		
		
		// -- Jocker's (3.3.1.C)
		panelJocker = new JPanel();
		panelJocker.setOpaque(false);
		FlowLayout fl_panelJocker = (FlowLayout) panelJocker.getLayout();
		fl_panelJocker.setVgap(0);
		fl_panelJocker.setHgap(0);
		btnJocker	= new ArrayList<BtnCard>();
		panelPlayerKit.add(panelJocker, BorderLayout.NORTH);
		
		// --- Pass Area (3.3.2.W)
		panelBtnPass = new JPanel();
		panelBtnPass.setOpaque(false);
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
		panelStatusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelStatusBar.setBackground(inactive); //!! Aktiver Spieler !!
		panelStatusBar.setPreferredSize(new Dimension(300,120));
		panelPlayerKit.add(panelStatusBar, BorderLayout.CENTER);
		
		GridBagLayout gbl_panelStatusBar = new GridBagLayout();
		GridBagConstraints cStatusBar = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelStatusBar.setLayout(gbl_panelStatusBar); 		//Layout dem Panelzuweisen!!

		
		imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource(pathImgBackSmall)));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cStatusBar.gridx = 0;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.ipady = 10;
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(imgLabelCard, cStatusBar);
	
		
		lbCardCount= new JLabel("6"); //!! Anpassen!!
		lbCardCount.setPreferredSize(new Dimension(50,30));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cStatusBar.gridx = 1;		//x-Koordinate im Grid
		cStatusBar.gridy = 0;		//y-Koordinate im Grid
		cStatusBar.insets = new Insets(5,5,5,5); //Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(lbCardCount, cStatusBar);
		
		imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource(pathImgCrown)));
		imgLabelCrown.setPreferredSize(new Dimension(25,22));
		cStatusBar = new GridBagConstraints();
		cStatusBar.gridx = 2;		
		cStatusBar.gridy = 0;		
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(imgLabelCrown, cStatusBar);
		
		lbPoint= new JLabel("20");
		lbPoint.setPreferredSize(new Dimension(50,30));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.gridx = 3;
		cStatusBar.gridy = 0;
		cStatusBar.insets = new Insets(5,0,0,10);
		panelStatusBar.add(lbPoint, cStatusBar);
		
		lbPlayerName= new JLabel("", JLabel.CENTER);
		lbPlayerName.setPreferredSize(new Dimension(50,30));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.ipady = 8;
		cStatusBar.weightx = 0.0;
		cStatusBar.gridwidth = 4;
		cStatusBar.gridx = 0;
		cStatusBar.gridy = 1;
		cStatusBar.insets = new Insets(5,0,0,10);
		panelStatusBar.add(lbPlayerName, cStatusBar);
		 
		// --- Play Area (3.3.4.E)
		panelBtnPlay = new JPanel();
		panelBtnPlay.setOpaque(false);
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
		panelControlContainer.setOpaque(false);
		panelPlayer.add(panelControlContainer, BorderLayout.EAST);
		GridBagLayout gbl_panelControlContainer = new GridBagLayout(); 
		GridBagConstraints cContainer = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelControlContainer.setLayout(gbl_panelControlContainer); 		//Layout dem Panelzuweisen!!

		lblPlaceHolder = new JLabel();
		lblPlaceHolder.setPreferredSize(new Dimension (58,58));
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 0;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,5);; //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(lblPlaceHolder,cContainer);
		
		btnSort = new JButton();
		btnSort.setIcon(new ImageIcon(TableView.class.getResource(pathImgSortBtn)));
		btnSort.setPreferredSize(new Dimension(58,58));
		btnSort.addActionListener(this);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cContainer.gridx = 1;		//x-Koordinate im Grid
		cContainer.gridy = 0;		//y-Koordinate im Grid
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,10); //Padding vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnSort, cContainer);
		
		btnRules = new JButton();
		btnRules.setIcon(new ImageIcon(TableView.class.getResource(pathImgRulesBtn)));
		btnRules.setPreferredSize(new Dimension (58,58));
		btnRules.addActionListener(this);
		cContainer = new GridBagConstraints();
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 2;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,5);; //Abstand vom Displayrand (top, left, bottom, right)
		panelControlContainer.add(btnRules,cContainer);

		panelBet = new JPanel();
		panelBet.setOpaque(false);
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
		btnBet30.setPreferredSize(new Dimension(35,35));
		btnBet30.addActionListener(controller);
		panelBet.add(btnBet30);

		btnBet15 = new JButton("15");
		btnBet15.setPreferredSize(new Dimension(35,35));
		btnBet15.addActionListener(controller);
		panelBet.add(btnBet15);
		
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(TableView.class.getResource(pathImgHomeBtn)));
		btnExit.setPreferredSize(new Dimension(58,58));
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
		updatePlayerHand(controller.getPlayer());
		updateTable(gameState);
		updatePlayers();

	}
	public void updateTable(GameState gameState){
		
		panelTable.removeAll();
		if (gameState.roundList.size() > 0){
			int i = 0;
			for (Card card:  gameState.getTopCards()){
				BtnCard btnCard = new BtnCard(card);
				btnCardTable.add(btnCard);
				//panelTable.add(btnCard);
				cTable.gridx = i++;		//x-Koordinate im Grid
				cTable.gridy = 0;		//y-Koordinate im Grid
				cTable.ipady = 10;
				cTable.insets = new Insets(0,0,0,0); //Padding vom Displayrand (top, left, bottom, right)
				panelTable.add(btnCard,cTable);
				i++;
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
		if (sortByID) {
			Collections.sort(player.getPlayerCards());
		} else {
			Collections.sort(player.getPlayerCards(), Card.CardSuitComparator);
		}
		for( Card card : player.getPlayerCards()){
			BtnCard btnCard = new BtnCard(card);
			btnCard.addActionListener(controller);
			btnCardHand.add(btnCard); //Add to ArrayList
			panelCardHand.add(btnCard);	    	
    	}
		
		Collections.sort(player.getPlayerJokers());
		
		for( Card jocker : player.getPlayerJokers()) {
			BtnCard btnCard = new BtnCard(jocker);
			btnCard.addActionListener(controller);
			btnJocker.add(btnCard); //Add to ArrayList
			panelJocker.add(btnCard);
		}
		frame.getContentPane().revalidate();
		frame.getContentPane().repaint();
		
	}
	public void updatePlayers(){
		lbPlayerName.setText(getPlayerName(controller.getPlayer()));
		GameState.PlayerToken activePlayerToken = controller.getGameState().getActivePlayer();
		if ( activePlayerToken == controller.getToken()){
			panelStatusBar.setBackground(active);
		}
		else{
			panelStatusBar.setBackground(inactive);
		}
		Player player2 = controller.getNextPlayer(controller.getPlayer());
		panel1stOpposition.updatePlayer(player2,activePlayerToken);
		log.debug("Player 1 = "+ controller.getPlayer().getToken() + " Player 2 = " +player2.getToken());
		if ( controller.getGameState().playerList.size() == 3){
			Player player3 = controller.getNextPlayer(player2);
			panel2stOpposition.updatePlayer(player3,activePlayerToken);
		}
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
	
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}
}
