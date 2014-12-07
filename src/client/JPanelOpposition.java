package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.GameState.PlayerToken;
import library.Card;
import library.Player;

public class JPanelOpposition extends JPanel{
	
	private TableView view;
	
	JPanel panelOppInfo;			//1
	JPanel panelOppCards;			//2
	JPanel panelOppSpacer;			//3
	
	JPanel panelOppBet;				//1.1
	JPanel panelOppStatusBar;		//1.2
	
	String playerName;
	String gameFildSide;
	
	int cardCount;
	int points;
	int bet;

	protected JButton btnBet15;
	protected JButton btnBet30;
	
	JLabel lbCardCount;
	JLabel lbPoint;
	JLabel lbPlayerName;
	JLabel imgLabelCrown;
	JLabel imgLabelCardBack;
	JLabel[] imgLabelJoker = new JLabel[4];
	//ArrayList<JLabel> imgLabelJoker = new ArrayList<JLabel>();

	Color active;
	Color inactive;
	
	private static final Logger log = LogManager.getLogger( JPanelOpposition.class.getName() );
	
	public JPanelOpposition() {
		
	}
	
	public JPanelOpposition(TableView view, String oppositionSide)  {

		this.view = view;
		gameFildSide = oppositionSide;
		
		// Initial Color
		active = new Color(147,196,125); 	//Green
		inactive = new Color(234,153,153);	//Red
		
		// Initial path of the Image
		String pathImgBack = "/gameContent/back.jpg";
		String pathImgBackSmall = "/gameContent/back_small.jpg";
		String pathImgCrown = "/gameContent/crown.png";
		
		// Define Fonts
		Font player = new Font("Comic Sans MS", Font.BOLD, 15);
		Font statusbar = new Font("Comic Sans MS", Font.PLAIN, 14);
		//Font button = new Font("comic Sans MS", Font.PLAIN, 16);
		
		// Initial joker array
		imgLabelJoker[0] = new JLabel(new ImageIcon(JPanelOpposition.class.getResource("/gameContent/joker/opp_joker11.jpg")));
		imgLabelJoker[1] = new JLabel(new ImageIcon(JPanelOpposition.class.getResource("/gameContent/joker/opp_joker12.jpg")));
		imgLabelJoker[2] = new JLabel(new ImageIcon(JPanelOpposition.class.getResource("/gameContent/joker/opp_joker13.jpg")));
		
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
		panelOppStatusBar.setPreferredSize(new Dimension(162,90) );
		panelOppInfo.add(panelOppStatusBar, BorderLayout.CENTER);

		GridBagLayout gbl_OppositeInfo = new GridBagLayout();
		GridBagConstraints cOppInfo = new GridBagConstraints();	//GridBag Grenzen erstellen
		panelOppStatusBar.setLayout(gbl_OppositeInfo); 		//Layout dem Panelzuweisen!!

				
		// --- Player Name
		lbPlayerName= new JLabel("", JLabel.CENTER);
		lbPlayerName.setFont(player);
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
		//lbCardCount= new JLabel(Integer.toString(cardCount)); //!! Anpassen!!
		lbCardCount= new JLabel("0");
		lbCardCount.setFont(statusbar);
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
		//lbPoint= new JLabel(Integer.toString(points));
		lbPoint= new JLabel("0");
		lbPoint.setFont(statusbar);
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
		panelOppCards.setPreferredSize(new Dimension(210,90));
		add(panelOppCards, BorderLayout.CENTER);
		
		GridBagLayout gbl_OppositeCards = new GridBagLayout();
		GridBagConstraints cOppCards = new GridBagConstraints();
		panelOppCards.setLayout(gbl_OppositeCards);
		
		// --CardBack
		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgBack)));
		imgLabelCardBack.setPreferredSize(new Dimension(24,79));
		imgLabelCardBack.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards.anchor = GridBagConstraints.NORTH;
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridwidth = 1;
		cOppCards.gridx = 0;
		cOppCards.gridy = 0;
		cOppCards.anchor = GridBagConstraints.NORTH;
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
		cOppCards.anchor = GridBagConstraints.NORTH;
		panelOppCards.add(imgLabelCardBack, cOppCards);
		
		imgLabelCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgBack)));
		imgLabelCardBack.setPreferredSize(new Dimension(50,79));
		imgLabelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 2;
		cOppCards.gridy = 0;
		cOppCards.anchor = GridBagConstraints.NORTH;
		//if (gameFildSide == "RIGHT") {cOppCardBack.insets = new Insets(0,0,0,20);} //Padding vom Displayrand (top, left, bottom, right)
		panelOppCards.add(imgLabelCardBack, cOppCards);

		
		// -- Jocker
		
		imgLabelJoker[0].setPreferredSize(new Dimension(24,40));
		imgLabelJoker[0].setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelJoker[0].setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelJoker[0].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridwidth = 1;
		cOppCards.gridx = 0;
		cOppCards.gridy = 1;
		panelOppCards.add(imgLabelJoker[0], cOppCards);

		imgLabelJoker[1].setPreferredSize(new Dimension(24,40));
		imgLabelJoker[1].setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelJoker[1].setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelJoker[1].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 1;
		cOppCards.gridy = 1;
		panelOppCards.add(imgLabelJoker[1], cOppCards);
		
		imgLabelJoker[2].setPreferredSize(new Dimension(50,40));
		imgLabelJoker[2].setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelJoker[2].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 2;
		cOppCards.gridy = 1;
		panelOppCards.add(imgLabelJoker[2], cOppCards);
	
		
		//drawJoker();

		/*
		imgLabelJoker = new JLabel(new ImageIcon(JPanelOpposition.class.getResource("/gameContent/joker/opp_joker12.jpg")));
		imgLabelJoker.setHorizontalAlignment(SwingConstants.LEFT);
		imgLabelJoker.setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelJoker.setPreferredSize(new Dimension(24,40));
		imgLabelJoker.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 1;
		cOppCards.gridy = 1;
		panelOppCards.add(imgLabelJoker, cOppCards);
		
		imgLabelJoker = new JLabel();
		imgLabelJoker.setPreferredSize(new Dimension(50,40));
		imgLabelJoker.setVerticalAlignment(SwingConstants.BOTTOM);
		imgLabelJoker.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		cOppCards = new GridBagConstraints();
		cOppCards.fill = GridBagConstraints.BOTH;
		cOppCards.gridx = 2;
		cOppCards.gridy = 1;
		cOppCards.anchor = GridBagConstraints.LAST_LINE_START;
		panelOppCards.add(imgLabelJoker, cOppCards);*/
		
		panelOppSpacer = new JPanel();
		panelOppSpacer.setOpaque(false);
		panelOppSpacer.setPreferredSize(new Dimension(210,80));
		add(panelOppSpacer, BorderLayout.SOUTH);
		
		displayBorder();	
}
	
	/**
	 * NOT IN USE AT THE MOMENT!!!
	 */
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
	

	/**
	 * Update opposition player panel
	 * @param player
	 * @param activePlayer
	 */
	public void updatePlayer(Player player, PlayerToken activePlayer) {
		cardCount = player.getPlayerCards().size() + player.getPlayerJokers().size();
		points = player.getPlayerPoints();
		
		lbPlayerName.setText(view.getPlayerName(player));
		lbCardCount.setText(Integer.toString(cardCount));
		lbPoint.setText(Integer.toString(points));
		updateJoker(player.getPlayerJokers());
		
		if ( activePlayer == player.getToken()){
			panelOppStatusBar.setBackground(active);
		}
		else{
			panelOppStatusBar.setBackground(inactive);
		}
	}
	
	public void updateJoker(ArrayList<Card> joker) {
		ArrayList<JLabel> lblJoker = new ArrayList<JLabel>();  
		//for (Card j : joker) {
		for (int i=0; i<joker.size(); i++) {
			log.debug("updatejoker:"+ joker.get(i).getCardName() + joker.get(i).getCardRank());
			String pathImgJoker = "/gameContent/"+ joker.get(i).getCardSuit() + "/" + joker.get(i).getCardName() + ".jpg";
			lblJoker.add(new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgJoker))));
			log.debug("fill lbljoker:" + lblJoker.get(i));
		}
	}
	
/*
	public ArrayList<JLabel> updateJoker(ArrayList<Card> joker) {
		
		for (Card card : joker) {
			String pathImgJoker = "/gameContent/"+ card.getCardSuit() + "/" + card.getCardName() + ".jpg";
			imgLabelJoker.add(new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgJoker))));
		}
		return imgLabelJoker;
	}

	public void drawJoker() {
	
		panelJocker.removeAll();
		panelJocker.revalidate();
		
		for (int i=0; i < imgLabelJoker.size(); i++) {
		//for (JLabel joker: imgLabelJoker) {
				imgLabelJoker.get(i).setPreferredSize(new Dimension(24,40));
				imgLabelJoker.get(i).setHorizontalAlignment(SwingConstants.LEFT);
				imgLabelJoker.get(i).setVerticalAlignment(SwingConstants.BOTTOM);
				imgLabelJoker.get(i).setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				GridBagConstraints cOppCards = new GridBagConstraints();
				cOppCards.fill = GridBagConstraints.BOTH;
				cOppCards.gridx = i;
				cOppCards.gridy = 1;
				panelOppCards.add(imgLabelJoker.get(i), cOppCards);

		}
	}*/

	
	/**
	 * TestLayoutManager Method
	 */
	public void displayBorder() {
		panelOppInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppCards.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppBet.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppStatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	}
	
}
