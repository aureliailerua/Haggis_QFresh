package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

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

/**
 * 
 * @author felicita.acklin
 * TableView is responsible for the hole game table.
 */

public class TableView extends JFrame {

	private JFrame frame;
											//Layer
	JPanelOpposition panel1stOpposition;	//1W
	JPanelTable panelTable;					//2 C
	JPanelOpposition panel2ndOpposition; 	//3E
	JPanel panelEmpty;						//3E alternative
	JPanelPlayer panelPlayer;				//4 N
	
	TableController controller;

	private static final Logger log = LogManager.getLogger( TableView.class.getName() );


	/**
	 * Create the application.
	 */
	public TableView(TableController controller) {

		this.controller = controller;
		
		frame = new JFrame("QFresh Haggis Game - Gametable");
		frame.setBounds(0, 0, 1280, 720); 						// x-Position, y-Position, breite und höhe des Fenster
		frame.setPreferredSize(new Dimension(1280,720));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().setBackground(Color.WHITE);
		
		
		/** 
		 * 1st Opposite Player (1.W)
		*/
		panel1stOpposition = new JPanelOpposition(this);
		panel1stOpposition.setOpaque(false);
		panel1stOpposition.setPreferredSize(new Dimension(300, 320));
		frame.getContentPane().add(panel1stOpposition, BorderLayout.WEST);

		
		/**
		 * Table (Card Desk) (2.C)
		 */		
		panelTable = new JPanelTable(this);	
		panelTable.setOpaque(false);
		frame.getContentPane().add(panelTable, BorderLayout.CENTER);
		
		/**
		 * 2nd Opposition Player (3.E)
		*/
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
		
		/**
		 * Player (4.N)
		 */
		panelPlayer = new JPanelPlayer(this, controller);
		panelPlayer.setPreferredSize(new Dimension(1280,380));		//to fix the size of the player panel - joker/cards of opposit stay in placer
		panelPlayer.setOpaque(false);
		frame.getContentPane().add(panelPlayer, BorderLayout.SOUTH);
	}
	
	
	
	/**
	 * Method for the communication between controller and view
	 * 
	 */
	public void setController(TableController controller){
		this.controller = controller;	
	}
	public JFrame getJFrame(){
		return frame;
	}
	
	/**
	 * Method to (re-)draw the game field
	 * @param gameState
	 */
	public void drawGameState(GameState gameState) {
		log.debug("rendering player " + controller.getToken());
		updatePlayers();
		panelTable.updateTable(gameState);
		panelPlayer.displayClientInfo(gameState.getClientInfo());
		panelTable.displayPatternInfo(gameState.getPatternInfo());
	}


	
	/**
	 * Method to set player name and how is the active player
	 */
	public void updatePlayers(){
		GameState.PlayerToken activePlayerToken = controller.getGameState().getActivePlayer();

		Player player1 = controller.getPlayer();
		panelPlayer.updatePlayer(player1, activePlayerToken);
		
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
	 * Method to define player name
	 * @param player
	 * @return Player + playerNumber
	 */
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}
	

	
	/**
	 * Method to test the LayoutManager
	 */
	public void displayBorder() {
		panel1stOpposition.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelTable.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panel2ndOpposition.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		panelPlayer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
	}
	
	
}
