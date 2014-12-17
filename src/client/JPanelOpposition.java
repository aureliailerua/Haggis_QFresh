package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.GameState.PlayerToken;
import library.Card;
import library.Player;

/**
 * Klasse stellt alle GUI-Komponenten der Gegenspieler dar.
 * @author felicita.acklin
 *
 */
public class JPanelOpposition extends JPanel{
	
	private TableView view;
	
	JPanel panelOppInfo;			//1
	JPanel panelOppCards;			//2
	JPanel panelOppSpacer;			//3
	
	JPanel panelOppStatusBar;		//1.2
	
	JPanel panelCardBack;			//2.1
	JPanel panelJoker;				//2.2
	
	// Variable and components of panelOppStatusBar
	String playerName;
	int cardCount;
	int points;
	JLabel lbCardCount;
	JLabel lbPoint;
	JLabel lbPlayerName;
	JLabel imgLabelCrown;
	JLabel lbCardBack;
	Color active;
	Color inactive;
	
	// Components and Collection of panelOppCards
	ArrayList<JLabel> jJoker;
	ArrayList<JLabel> jBack;

	//Logger
	private static final Logger log = LogManager.getLogger( JPanelOpposition.class.getName() );
	
	
	public JPanelOpposition() {	}					//Constructor if only 2 player are connected, 3 player should be empty
	
	public JPanelOpposition(TableView view)  {

		this.view = view;

		
		// Define Color
		active = new Color(147,196,125); 	//Green
		inactive = new Color(234,153,153);	//Red
		
		// Define path of the Image
		String pathImgBackSmall = "/gameContent/back_small.jpg";
		String pathImgCrown = "/gameContent/crown.png";
	
		// Define Fonts
		Font player = new Font("Comic Sans MS", Font.BOLD, 15);
		Font statusbar = new Font("Comic Sans MS", Font.PLAIN, 14);
	
		// Setup panel layout
		setLayout(new BorderLayout(0, 0));
		
		// * Opposition Info (1.1) *
		// Contains the status bar
		panelOppInfo = new JPanel();
		panelOppInfo.setOpaque(false);
		add(panelOppInfo, BorderLayout.NORTH);
				
		// -- StatusBar (1.1.2) --
		// Contains player name, card count (and icon) and point (and icon)
		panelOppStatusBar = new JPanel();
		panelOppStatusBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelOppStatusBar.setBackground(inactive);					//added player should be inactive by default
		panelOppStatusBar.setPreferredSize(new Dimension(162,90) );
		panelOppInfo.add(panelOppStatusBar, BorderLayout.CENTER);

		GridBagLayout gbl_OppositeInfo = new GridBagLayout();
		GridBagConstraints cOppInfo = new GridBagConstraints();
		panelOppStatusBar.setLayout(gbl_OppositeInfo);

				
		// --- Player Name ----
		lbPlayerName= new JLabel("", JLabel.CENTER);
		lbPlayerName.setFont(player);
		lbPlayerName.setPreferredSize(new Dimension(50,50));
		lbPlayerName.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.ipady = 10;
		cOppInfo.gridwidth = 5;
		cOppInfo.gridx = 0;	
		cOppInfo.gridy = 0;
		cOppInfo.insets = new Insets(0,0,0,0);
		panelOppStatusBar.add(lbPlayerName, cOppInfo);
				
		// --- Card Icon ---
		JLabel imgLabelCard = new JLabel(new ImageIcon(TableView.class.getResource(pathImgBackSmall)));
		imgLabelCard.setPreferredSize(new Dimension(22,35));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.gridx = 0;		
		cOppInfo.gridy = 1;	
		cOppInfo.ipady = 10;
		cOppInfo.insets = new Insets(5,1,5,5); 
		panelOppStatusBar.add(imgLabelCard, cOppInfo);
		
		// --- Count of Count ---
		lbCardCount= new JLabel("0");
		lbCardCount.setFont(statusbar);
		lbCardCount.setPreferredSize(new Dimension(50,50));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.gridx = 1;		
		cOppInfo.gridy = 1;		
		cOppInfo.insets = new Insets(5,5,5,5);
		panelOppStatusBar.add(lbCardCount, cOppInfo);
				

		// --- Crown icon ---
		JLabel imgLabelCrown = new JLabel(new ImageIcon(TableView.class.getResource(pathImgCrown)));
		imgLabelCrown.setPreferredSize(new Dimension(25,22));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.gridx = 2;		
		cOppInfo.gridy = 1;		
		cOppInfo.insets = new Insets(5,5,5,5);
		panelOppStatusBar.add(imgLabelCrown, cOppInfo);
		
		// --- Amounts of Points ---
		lbPoint= new JLabel("0");
		lbPoint.setFont(statusbar);
		lbPoint.setPreferredSize(new Dimension(50,50));
		cOppInfo = new GridBagConstraints();
		cOppInfo.fill = GridBagConstraints.BOTH;
		cOppInfo.gridx = 3;
		cOppInfo.gridy = 1;
		cOppInfo.insets = new Insets(5,5,5,1);
		panelOppStatusBar.add(lbPoint, cOppInfo);

				
		// * Opposition Cards (1.2) *
		// Contains amount of opposition normal and joker cards
		panelOppCards = new JPanel();
		panelOppCards.setOpaque(false);
		panelOppCards.setPreferredSize(new Dimension(200,100));
		panelOppCards.setLayout(new BorderLayout(0, 0));
		add(panelOppCards, BorderLayout.CENTER);

				
		// -- CardBack (1.2.1) --
		panelCardBack = new JPanel();
		panelOppCards.add(panelCardBack, BorderLayout.NORTH);
		panelCardBack.setOpaque(false);
		panelCardBack.setBorder(null);
		FlowLayout fl_panelCardBack = (FlowLayout) panelCardBack.getLayout();
		fl_panelCardBack.setHgap(0);
		fl_panelCardBack.setVgap(0);
		jBack = new ArrayList<JLabel>();
		
		
		// -- Joker (1.2.1) --
		panelJoker = new JPanel();
		panelOppCards.add(panelJoker, BorderLayout.CENTER);
		panelJoker.setPreferredSize(new Dimension(200,30));
		panelJoker.setOpaque(false);
		panelJoker.setBorder(null);
		FlowLayout fl_panelJoker = (FlowLayout) panelJoker.getLayout();
		fl_panelJoker.setHgap(0);
		fl_panelJoker.setVgap(0);
		jJoker = new ArrayList<JLabel>();


		// * Spacer (1.3) *
		// scaling the rest of the panel
		panelOppSpacer = new JPanel();
		panelOppSpacer.setOpaque(false);
		panelOppSpacer.setPreferredSize(new Dimension(210,80));
		add(panelOppSpacer, BorderLayout.SOUTH);
}
	
	
	

	/**
	 * Update opposition player panel
	 * @param player
	 * @param activePlayer
	 */
	public void updatePlayer(Player player, PlayerToken activePlayer) {
		cardCount = player.getPlayerCards().size();
		points = player.getPlayerPoints();
		
		// Update StatusBar
		lbPlayerName.setText(view.getPlayerName(player));
		log.debug("Updated Player: " + view.getPlayerName(player));
		lbCardCount.setText(Integer.toString(cardCount));
		lbPoint.setText(Integer.toString(points));
		// Update Joker & normal Cards
		updateJoker(player.getPlayerJokers(), player.getPlayerCards().size());
		updateCards(player.getPlayerCards().size());
		
		// Set active Player
		if ( activePlayer == player.getToken()){
			panelOppStatusBar.setBackground(active);
		}
		else{
			panelOppStatusBar.setBackground(inactive);
		}
	}
	
	/**
	 * Update and display the joker cards
	 * @param joker
	 * @param cardCounts
	 */
	public void updateJoker(ArrayList<Card> joker, int cardCounts) {
		log.debug("Repaint Joker Cards");

		panelJoker.removeAll();
		panelJoker.revalidate();
		revalidate();
		
		jJoker = new ArrayList<JLabel>();
		int cardHight = 40;
		if (cardCounts == 0) { cardHight = 79; }
		
		for(int i=0; i < joker.size(); i++) {
			String pathJoker = "/gameContent/joker/opp_joker"+ joker.get(i).getCardRank()+".jpg";
			JLabel lbJoker = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathJoker)));
			
			if (i == joker.size()-1) {
				lbJoker.setPreferredSize(new Dimension(50,cardHight));
			}  else {
				lbJoker.setPreferredSize(new Dimension(24, cardHight));
			}
			lbJoker.setHorizontalAlignment(SwingConstants.LEFT);
			lbJoker.setVerticalAlignment(SwingConstants.BOTTOM);
			lbJoker.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
			jJoker.add(lbJoker);
			panelJoker.add(lbJoker);
		}
		panelJoker.repaint();

	}
	
	/**
	 * Update and display normal cards
	 * 14 card count equates to 7 displayed cards, 
	 * if card counts is less then 7 cards will displays equates to card count
	 * @param cardCount
	 */
	public void updateCards(int cardCount) {
		log.debug("Repaint Joker Cards");
		panelCardBack.removeAll();
		panelCardBack.revalidate();
		revalidate();
		
		int displayCardCount = 0;
		String pathImgBack = "/gameContent/back.jpg";
		jBack = new ArrayList<JLabel>();
		
		if (cardCount > 0){
			if (cardCount >=7) {
				displayCardCount = 7;
			} else if (cardCount < 7) {
				displayCardCount = cardCount;
			}
			
			for(int i=0; i < displayCardCount; i++) {
				lbCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgBack)));
				if (i == (displayCardCount-1)) {
					lbCardBack.setPreferredSize(new Dimension(51,79));
				} else {
					lbCardBack.setPreferredSize(new Dimension(24,79));
				}
				lbCardBack.setHorizontalAlignment(SwingConstants.LEFT);
				lbCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
				jBack.add(lbCardBack);
				panelCardBack.add(lbCardBack);
			}
		}
		panelCardBack.repaint();
	}
	
}
