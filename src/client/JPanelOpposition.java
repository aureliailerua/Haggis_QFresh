package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import library.GameState.PlayerToken;
import library.Player;

public class JPanelOpposition extends JPanel{
	
	private TableView view;
	String playerName;
	int cardCount;
	int points;
	int bet;
	String gameFildSide;
	String pathImgBack;
	String pathImgBackSmall;
	String pathImgCrown;


	JPanel panelOppInfo;		//1
	JPanel panelOppCards;		//2
	
	JPanel panelOppBet;				//1.1
	JPanel panelOppStatusBar;		//1.2
	
	protected JButton btnBet15;
	protected JButton btnBet30;
	
	JLabel lbCardCount;
	JLabel lbPoint;
	JLabel lbPlayerName;
	JLabel imgLabelCrown;
	JLabel imgLabelCardBack;
	
	Color active;
	Color inactive;
	Color coints;
	
	//	public JPanelOpposition(String oppositionName, int oppositionCardCount, int oppositionPoints, int oppositionBet, String oppositionSide, BtnCard<ArrayList> jocker)  {

	//public JPanelOpposition(TableView view, String oppositionName, int oppositionCardCount, int oppositionPoints, int oppositionBet, String oppositionSide)  {
	public JPanelOpposition(TableView view, int oppositionCardCount, int oppositionPoints, int oppositionBet, String oppositionSide)  {

		this.view = view;
		//playerName = oppositionName;
		cardCount = oppositionCardCount;
		points = oppositionPoints;
		bet = oppositionBet;
		gameFildSide = oppositionSide;
		
		// Initial Color
		active = new Color(147,196,125); 	//Green
		inactive = new Color(234,153,153);	//Red
		coints = new Color(255,217,102);	//Yellow
		
		// Initial path of the Image
		pathImgBack = "/gameContent/back.jpg";
		pathImgBackSmall = "/gameContent/back_small.jpg";
		pathImgCrown = "/gameContent/crown.png";
		
		// Set panel setup
		setLayout(new BorderLayout(0, 0));
		
		// - Opposition Info (1.1.N)
		panelOppInfo = new JPanel();
		panelOppInfo.setOpaque(false);
		add(panelOppInfo, BorderLayout.NORTH);
		
		// -- Bet (1.1.1.w) for Player in at the left side
		if (gameFildSide == "LEFT") {
			setBetPanel();
			panelOppInfo.add(panelOppBet, BorderLayout.WEST);
		}
				
		// -- StatusBar (1.1.2.C)
		panelOppStatusBar = new JPanel();
		panelOppStatusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelOppStatusBar.setBackground(inactive);		//default: incative
		panelOppStatusBar.setPreferredSize(new Dimension(162,80) );
		panelOppInfo.add(panelOppStatusBar, BorderLayout.CENTER);

		GridBagLayout gbl_OppositeInfo = new GridBagLayout();
		GridBagConstraints cOppInfo = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelOppStatusBar.setLayout(gbl_OppositeInfo); 		//Layout dem Panelzuweisen!!

				
		// --- Player Name
		lbPlayerName= new JLabel("", JLabel.CENTER);
		lbPlayerName.setPreferredSize(new Dimension(50,50));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.ipady = 10;
		cOppInfo.weightx = 0.0;
		cOppInfo.gridwidth = 5;
		cOppInfo.gridx = 0;
		cOppInfo.gridy = 0;
		cOppInfo.insets = new Insets(0,10,5,0);
		panelOppStatusBar.add(lbPlayerName, cOppInfo);
				
		// --- Count of Cards
		// ---- Card Icon
		JLabel imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource(pathImgBackSmall)));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;		//Legt fest, wie die zelle durch Comp ausgefüllt werden soll - Both (Vertikal & horizontal)
		cOppInfo.gridx = 0;		//x-Koordinate im Grid
		cOppInfo.gridy = 1;		//y-Koordinate im Grid
		cOppInfo.ipady = 10;
		cOppInfo.insets = new Insets(5,1,5,5); //Padding top, left, bottom, right
		panelOppStatusBar.add(imgLabelCard, cOppInfo);
		
		// ---- Number of Cards
		lbCardCount= new JLabel(Integer.toString(cardCount)); //!! Anpassen!!
		lbCardCount.setPreferredSize(new Dimension(50,50));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.gridx = 1;		
		cOppInfo.gridy = 1;		
		cOppInfo.insets = new Insets(5,5,5,5); //Padding top, left, bottom, right
		panelOppStatusBar.add(lbCardCount, cOppInfo);
				
		// --- Points
		// ---- Crwon Icon
		JLabel imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource(pathImgCrown)));
		imgLabelCrown.setPreferredSize(new Dimension(25,22));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.gridx = 2;		
		cOppInfo.gridy = 1;		
		cOppInfo.insets = new Insets(5,5,5,5);
		panelOppStatusBar.add(imgLabelCrown, cOppInfo);
		
		// ---- Number of Points
		lbPoint= new JLabel(Integer.toString(points));
		lbPoint.setPreferredSize(new Dimension(50,50));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.gridx = 3;
		cOppInfo.gridy = 1;
		cOppInfo.insets = new Insets(5,0,0,1);
		panelOppStatusBar.add(lbPoint, cOppInfo);
		
		// -- Bet (1.1.1.E) for Player in at the right side
		if (gameFildSide == "RIGHT") {
			setBetPanel();
			panelOppInfo.add(panelOppBet, BorderLayout.EAST);
		}
		
		
		// - Opposition Cards (1.2.C)
		panelOppCards = new JPanel();
		panelOppCards.setOpaque(false);
		//panelOppCardBack.setPreferredSize(new Dimension(210,80));
		add(panelOppCards, BorderLayout.CENTER);
		
		GridBagLayout gbl_OppositeCards = new GridBagLayout();
		GridBagConstraints cOppCards = new GridBagConstraints();	
		panelOppCards.setLayout(gbl_OppositeCards);
		
		// --CardBack
		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgBack)));
		imgLabelCardBack.setPreferredSize(new Dimension(24,79));
		imgLabelCardBack.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridwidth = 1;
		cOppCards.gridx = 0;
		cOppCards.gridy = 0;
		cOppCards.anchor = GridBagConstraints.LINE_END;
		// Check for the padding of the card back
		//if (gameFildSide == "LEFT") { cOppCardBack.insets = new Insets(0,20,0,0); }
		panelOppCards.add(imgLabelCardBack, cOppCards);

		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgBack)));
		imgLabelCardBack.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelCardBack.setPreferredSize(new Dimension(24,79));
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 1;
		cOppCards.gridy = 0;
		cOppCards.anchor = GridBagConstraints.LINE_END;
		panelOppCards.add(imgLabelCardBack, cOppCards);
		
		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgBack)));
		imgLabelCardBack.setPreferredSize(new Dimension(50,79));
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 2;
		cOppCards.gridy = 0;
		//if (gameFildSide == "RIGHT") {cOppCardBack.insets = new Insets(0,0,0,20);} //Padding vom Displayrand (top, left, bottom, right)
		panelOppCards.add(imgLabelCardBack, cOppCards);

		
		// -- Jocker
		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource("/gameContent/joker/opp_joker11.jpg")));
		imgLabelCardBack.setPreferredSize(new Dimension(24,40));
		imgLabelCardBack.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelCardBack.setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridwidth = 1;
		cOppCards.gridx = 0;
		cOppCards.gridy = 1;
		cOppCards.anchor = GridBagConstraints.LAST_LINE_START;
		// Check for the padding of the card back
		panelOppCards.add(imgLabelCardBack, cOppCards);

		
		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource("/gameContent/joker/opp_joker12.jpg")));
		imgLabelCardBack.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelCardBack.setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelCardBack.setPreferredSize(new Dimension(24,40));
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 1;
		cOppCards.gridy = 1;
		//cOppCardBack.anchor = GridBagConstraints.LAST_LINE_START;
		panelOppCards.add(imgLabelCardBack, cOppCards);
		
		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource("/gameContent/joker/opp_joker13.jpg")));
		imgLabelCardBack.setPreferredSize(new Dimension(50,40));
		imgLabelCardBack.setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 2;
		cOppCards.gridy = 1;
		cOppCards.anchor = GridBagConstraints.LAST_LINE_START;
		panelOppCards.add(imgLabelCardBack, cOppCards);
		
		displayBorder();	
}
	
	public void setBetPanel() {
		// -- Bet (1.1.1.W)
		panelOppBet = new JPanel();
		panelOppBet.setOpaque(false);
		FlowLayout fl_panelOppositeBet = (FlowLayout) panelOppBet.getLayout();
		fl_panelOppositeBet.setAlignment(FlowLayout.RIGHT);
		panelOppBet.setBackground(Color.WHITE);

				
		if(bet!=0){
			TitledBorder betTitle = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black,2), "Bet");
			betTitle.setTitleJustification(TitledBorder.LEFT);
			panelOppBet.setBorder( betTitle);	
				
			switch(bet) {
				case 15:
					btnBet15 = new JButton();
					ImageIcon imageIcon15 = new ImageIcon(TableView.class.getResource("/gameContent/15bet_active.png"));
					btnBet15.setIcon(new ImageIcon(imageIcon15.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)));
					btnBet15.setBorder(null);
					btnBet15.setPreferredSize(new Dimension(30,30));
					btnBet15.setVisible(true);
					panelOppBet.add(btnBet15);
					break;
				case 30:
					btnBet30 = new JButton();
					ImageIcon imageIcon30 = new ImageIcon(TableView.class.getResource("/gameContent/30bet_active.png"));
					btnBet30.setIcon(new ImageIcon(imageIcon30.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)));
					btnBet30.setBorder(null);
					btnBet30.setPreferredSize(new Dimension(30,30));
					btnBet30.setVisible(true);
					panelOppBet.add(btnBet30);
					break;
			}
		} else {
			panelOppBet.setPreferredSize(new Dimension(45,78));
		}
	}

	public void updatePlayer(Player player, PlayerToken activePlayer) {
		lbPlayerName.setText(view.getPlayerName(player));
		if ( activePlayer == player.getToken()){
			panelOppStatusBar.setBackground(active);
		}
		else{
			panelOppStatusBar.setBackground(inactive);
		}
	}
	
	/**
	 * TestLayoutManager Methode
	 */
	public void displayBorder() {
		panelOppInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppCards.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppBet.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppStatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	}
	
}
