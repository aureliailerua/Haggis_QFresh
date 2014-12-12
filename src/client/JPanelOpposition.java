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
	
	JPanel panelCardBack;
	JPanel panelJoker;
	String playerName;
	String gameFildSide;
	
	GridBagConstraints cJoker;
	
	int cardCount;
	int points;
	int bet;
	boolean noneCards;


	protected JButton btnBet15;
	protected JButton btnBet30;
	
	JLabel lbCardCount;
	JLabel lbPoint;
	JLabel lbPlayerName;
	JLabel imgLabelCrown;
	JLabel lbCardBack;
	JLabel[] imgLabelJoker = new JLabel[3];
	ArrayList<JLabel> jJoker;
	ArrayList<JLabel> jBack;


	String[] pathJoker = new String[4];
	
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
		//String pathImgBack = "/gameContent/back.jpg";
		String pathImgBackSmall = "/gameContent/back_small.jpg";
		String pathImgCrown = "/gameContent/crown.png";

		pathJoker[0] = "/gameContent/joker/opp_joker11.jpg";
		pathJoker[1] = "/gameContent/joker/opp_joker12.jpg";
		pathJoker[2] = "/gameContent/joker/opp_joker13.jpg";
		pathJoker[3] = "/gameContent/joker/opp_empty.png";
		
		// Define Fonts
		Font player = new Font("Comic Sans MS", Font.BOLD, 15);
		Font statusbar = new Font("Comic Sans MS", Font.PLAIN, 14);
		
		// Initial joker array

		imgLabelJoker[0] = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathJoker[0])));
		imgLabelJoker[1] = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathJoker[1])));
		imgLabelJoker[2] = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathJoker[2])));
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
		panelOppCards.setPreferredSize(new Dimension(200,100));
		panelOppCards.setLayout(new BorderLayout(0, 0));
		add(panelOppCards, BorderLayout.CENTER);

				
		// --CardBack (1.2.1.N)
		
		panelCardBack = new JPanel();
		panelOppCards.add(panelCardBack, BorderLayout.NORTH);
		//panelCardBack.setPreferredSize(new Dimension(200,80));
		panelCardBack.setOpaque(false);
		panelCardBack.setBorder(null);
		FlowLayout fl_panelCardBack = (FlowLayout) panelCardBack.getLayout();
		fl_panelCardBack.setHgap(0);
		fl_panelCardBack.setVgap(0);
		jBack = new ArrayList<JLabel>();
		
		
		// -- Joker (1.2.1.C)
		panelJoker = new JPanel();
		panelOppCards.add(panelJoker, BorderLayout.CENTER);
		panelJoker.setPreferredSize(new Dimension(200,30));
		panelJoker.setOpaque(false);
		panelJoker.setBorder(null);
		FlowLayout fl_panelJoker = (FlowLayout) panelJoker.getLayout();
		fl_panelJoker.setHgap(0);
		fl_panelJoker.setVgap(0);
		jJoker = new ArrayList<JLabel>();


		// - Spacer (1.3.S)
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
		
		// Update StatusBar
		lbPlayerName.setText(view.getPlayerName(player));
		log.debug("Updated Player: " + view.getPlayerName(player));
		lbCardCount.setText(Integer.toString(cardCount));
		lbPoint.setText(Integer.toString(points));
		// Update Joker
		updateJoker(player.getPlayerJokers());
		updateCards(player.getPlayerCards().size());
		
		// Set active Player
		if ( activePlayer == player.getToken()){
			panelOppStatusBar.setBackground(active);
		}
		else{
			panelOppStatusBar.setBackground(inactive);
		}
		
	}
	
	public void updateJoker(ArrayList<Card> joker) {
		log.debug("Repaint Joker Cards");

		panelJoker.removeAll();
		panelJoker.revalidate();
		revalidate();
		
		jJoker = new ArrayList<JLabel>();
		// FUNKTIONIERT NOCH NICHT!
		int cardHight = 40;
		if (noneCards) { cardHight = 79; }
		
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
	
	public void updateCards(int cardCount) {
		log.debug("Repaint Joker Cards");
		panelCardBack.removeAll();
		panelCardBack.revalidate();
		revalidate();
		
		noneCards = false;
		String pathImgBack = "/gameContent/back.jpg";
		jBack = new ArrayList<JLabel>();
		
		// FUNKTIONIERT NOCH NICHT
		if (cardCount == 0){
			noneCards = true;
		} else {
			for(int i=0; i < (cardCount/2); i++) {
				lbCardBack = new JLabel(new ImageIcon(JPanelOpposition.class.getResource(pathImgBack)));
				if (i == (cardCount/2)-1) {
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
	
	/**
	 * TestLayoutManager Method
	 */
	public void displayBorder() {
		panelOppInfo.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppCards.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppBet.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelOppStatusBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		//panelCardBack.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		//panelJoker.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

	}
	
}
