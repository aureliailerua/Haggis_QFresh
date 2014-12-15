package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.Player;
import library.GameState.PlayerToken;

public class JPanelPlayer extends JPanel implements ActionListener{

	JPanel panelCardHand; 					//3.1
	JPanel panelClientInfo; 				//3.2
	JPanel panelPlayerKit;					//3.3
	JPanel panelControlContainer;			//3.4
	
	JPanel panelJoker;						//4.2.1
	JPanel panelBtnPass;					//4.2.2
	JPanel panelStatusBar;					//4.2.3
	JPanel panelBtnPlay;					//4.2.4
	
	ArrayList<BtnCard> btnCardHand;
	ArrayList<BtnCard> btnJocker;
	
	protected JButton btnPlay;
	protected JButton btnPass; 
	protected JButton btnSort;
	protected JButton btnExit;
	protected JButton btnRules;
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
	
	JTextArea clientInfo;
	
	GridBagConstraints cTable;	
	
	Color active;
	Color inactive;
	Font infotext = new Font("Comic Sans MS", Font.BOLD, 14);


	private boolean sortByID = true;
	
	TableController controller;
	TableView view;
	
	private static final Logger log = LogManager.getLogger( JPanelPlayer.class.getName() );

	
	public JPanelPlayer(TableView view, TableController controller)  {
		
		this.controller = controller;
		this.view = view;
		
		
		// Define the path of the images
		String pathImgBackSmall = "/gameContent/back_small.jpg";
		String pathImgCrown = "/gameContent/crown.png";
		String pathImgExitBtn = "/icons/exit.png";
		String pathImgSortBtn = "/icons/sort.png";
		String pathImgRulesBtn ="/icons/rules.png";

		
		// Define Fonts
		Font player = new Font("Comic Sans MS", Font.BOLD, 18);
		Font statusbar = new Font("Comic Sans MS", Font.PLAIN, 14);
		Font button = new Font("comic Sans MS", Font.PLAIN, 16);

		
		// Define Color
		active = new Color(147,196,125); 	//Green
		inactive = new Color(234,153,153);	//Red

		//Setup panel layout
		setLayout(new BorderLayout(0, 0));
		
		// - Card Hand (3.1.N)
		panelCardHand = new JPanel();
		panelCardHand.setPreferredSize(new Dimension(1280, 135));
		panelCardHand.setOpaque(false);
		FlowLayout fl_panelCardHand = (FlowLayout) panelCardHand.getLayout();
		fl_panelCardHand.setHgap(0);
		fl_panelCardHand.setVgap(0);
		btnCardHand = new ArrayList<BtnCard>();
		add(panelCardHand, BorderLayout.NORTH);
		
		// - ClientInfo Left (3.2.W)
		panelClientInfo = new JPanel();
		panelClientInfo.setOpaque(false);
		FlowLayout fl_panelClientInfo = (FlowLayout) panelClientInfo.getLayout();
		fl_panelClientInfo.setAlignment(FlowLayout.CENTER);
		fl_panelClientInfo.setVgap(65);
		panelClientInfo.setPreferredSize(new Dimension(300, 120));
		//displayClientInfo("");
		add(panelClientInfo, BorderLayout.WEST);
	
		// - Player's Kit (3.3.C)
		panelPlayerKit = new JPanel();
		panelPlayerKit.setOpaque(false);
		add(panelPlayerKit, BorderLayout.CENTER);
		panelPlayerKit.setLayout(new BorderLayout(0, 0));
		
		
		// -- Jocker's (3.3.1.C)
		panelJoker = new JPanel();
		panelJoker.setPreferredSize(new Dimension(640, 135));
		panelJoker.setOpaque(false);
		FlowLayout fl_panelJoker = (FlowLayout) panelJoker.getLayout();
		fl_panelJoker.setVgap(0);
		fl_panelJoker.setHgap(0);
		btnJocker	= new ArrayList<BtnCard>();
		panelPlayerKit.add(panelJoker, BorderLayout.NORTH);
		
		// --- Pass Area (3.3.2.W)
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
		
		// --- Status Bar (3.3.3.C)
		panelStatusBar = new JPanel();
		panelStatusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelStatusBar.setBackground(inactive);
		panelStatusBar.setPreferredSize(new Dimension(300,120));
		panelPlayerKit.add(panelStatusBar, BorderLayout.CENTER);
		
		GridBagLayout gbl_panelStatusBar = new GridBagLayout();
		GridBagConstraints cStatusBar = new GridBagConstraints();			//GridBag Grenzen erstellen
		panelStatusBar.setLayout(gbl_panelStatusBar); 						//Layout dem Panelzuweisen!!
	
		// ---- Card Icon
		imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource(pathImgBackSmall)));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cStatusBar.gridx = 0;												//x-Koordinate im Grid
		cStatusBar.gridy = 0;												//y-Koordinate im Grid
		cStatusBar.ipady = 10;												//Höhe der Zelle
		cStatusBar.insets = new Insets(5,40,5,5); 							//Padding vom Displayrand (top, left, bottom, right)
		panelStatusBar.add(imgLabelCard, cStatusBar);
	
		// ---- Count of Cards
		lbCardCount= new JLabel("0");
		lbCardCount.setFont(statusbar);
		lbCardCount.setPreferredSize(new Dimension(50,30));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;							//Wie soll der constrain das feld füllen - Both (Vertikal & horizontal)
		cStatusBar.gridx = 1;
		cStatusBar.gridy = 0;
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(lbCardCount, cStatusBar);
		
		// ---- Crown Icon
		imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource(pathImgCrown)));
		imgLabelCrown.setPreferredSize(new Dimension(25,22));
		cStatusBar = new GridBagConstraints();
		cStatusBar.gridx = 2;		
		cStatusBar.gridy = 0;	
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(imgLabelCrown, cStatusBar);
		
		//---- Display count of points
		lbPoint= new JLabel("0");
		lbPoint.setFont(statusbar);
		lbPoint.setPreferredSize(new Dimension(50,30));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.gridx = 3;
		cStatusBar.gridy = 0;
		cStatusBar.insets = new Insets(5,5,5,5);
		panelStatusBar.add(lbPoint, cStatusBar);
		
		//---- Display Player Name
		lbPlayerName= new JLabel("", JLabel.CENTER);
		lbPlayerName.setFont(player);
		lbPlayerName.setPreferredSize(new Dimension(50,30));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, Color.BLACK));
		cStatusBar = new GridBagConstraints();
		cStatusBar.fill = GridBagConstraints.BOTH;
		cStatusBar.ipady = 8;
		cStatusBar.gridwidth = 4;										//Zellen die zusammengefasst werden
		cStatusBar.gridx = 0;
		cStatusBar.insets = new Insets(5,5,5,10);
		panelStatusBar.add(lbPlayerName, cStatusBar);
		 
		// --- Play Area (3.3.4.E)
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
		
		// -- Control CardContainer with Buttons (3.4.E)
		panelControlContainer = new JPanel();
		panelControlContainer.setOpaque(false);
		panelControlContainer.setPreferredSize(new Dimension(300,120));
		add(panelControlContainer, BorderLayout.EAST);
		GridBagLayout gbl_panelControlContainer = new GridBagLayout(); 
		GridBagConstraints cContainer = new GridBagConstraints();
		panelControlContainer.setLayout(gbl_panelControlContainer);
	
		lblPlaceHolder = new JLabel();
		lblPlaceHolder.setPreferredSize(new Dimension (58,58));
		cContainer.fill = GridBagConstraints.HORIZONTAL;
		cContainer.gridx = 0;
		cContainer.gridy = 0;
		cContainer.gridwidth = 1;
		cContainer.insets = new Insets(5,0,0,5);
		panelControlContainer.add(lblPlaceHolder,cContainer);
		
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
		panelControlContainer.add(btnSort, cContainer);
		
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
		panelControlContainer.add(btnRules,cContainer);
		
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
		panelControlContainer.add(btnExit,cContainer);
		
	}

	public void updatePlayer(Player player, PlayerToken activePlayer) {
		int cardCount = player.getPlayerCards().size();
		int points = player.getPlayerPoints();
		String playername = view.getPlayerName(controller.getPlayer());
					
		log.debug("Updated Player: " + view.getPlayerName(player));
		
		// Update StatusBar
		lbPlayerName.setText(playername);
		lbCardCount.setText(Integer.toString(cardCount));
		lbPoint.setText(Integer.toString(points));
		log.debug("Updated Player: " + view.getPlayerName(player));
	
		// Update Cards
		updatePlayerHand(player);
		
		// Set active Player
		if ( activePlayer == player.getToken()){
			panelStatusBar.setBackground(active);
		}
		else{
			panelStatusBar.setBackground(inactive);
		}
	}
	
	/**
	* Method to get the existing card in my hand (normal and joker) after the a trick
	* @param player
	*/
	public void updatePlayerHand(Player player){
		panelCardHand.removeAll();
		panelJoker.removeAll();
		panelCardHand.revalidate();
		panelJoker.revalidate();
		view.getJFrame().getContentPane().revalidate();
		
		// Initial & Sort Cards
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
		view.getJFrame().getContentPane().revalidate();
		view.getJFrame().getContentPane().repaint();
	}
	/**
	 * Method for displaying reached client informations
	 * @param message
	 */
	public void displayClientInfo(String message) {
		panelClientInfo.removeAll();
		panelClientInfo.revalidate();
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
			panelClientInfo.add(clientInfo);
		} 
		panelClientInfo.repaint();
	}
	
	/**
	 * Method to open the combination rule card
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
	 * Action Performer (Rule and Sort Button)
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

	/**
	 * Method to test the LayoutManager
	 */
	public void displayBorder() {
		panelCardHand.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelClientInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelPlayerKit.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelControlContainer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		
		panelJoker.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelBtnPass.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelStatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelBtnPlay.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	
	}

}
