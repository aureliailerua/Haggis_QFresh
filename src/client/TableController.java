package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.CardContainer;
import library.CardDeck;
import library.GameState;
import library.GameState.PlayerToken;
import library.Player;
import client.TableView;

/** 
 * @author felicita.acklin / benjamin.indermuehle
 * Klasse handhabt die Datenvearbeitung und Kommunikation zwischen ServerHandler
 * und TableView
 *
 */
public class TableController implements ActionListener,Observer{
	TableView view;
	ServerHandler handler;
	boolean mockup;
	private static final Logger log = LogManager.getLogger( TableController.class.getName() );
	
	public TableController(ServerHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
	}
	/**
	 * Set View
	 * @param view
	 */
	public void setView(TableView view){
		this.view = view;
	}
	/**
	 * Update and redraw the game field in view
	 */
	public void drawGameState(){
		log.debug("drawing new GameState");
		view.drawGameState(getGameState());
		view.getJFrame().revalidate(); 	// set components validate and refresh the layout if some components get new datas
		view.getJFrame().repaint();		// repaint components
	}
	/**
	 * Get all information of the players
	 * @return playerToken
	 */
	public PlayerToken getToken() {
		return handler.getToken();
	}
	/**
	 * Get all information about the game
	 * @return gameState
	 */
	public GameState getGameState(){
		return handler.gameState;
	}
	
	
	/**
	 * Send main players cards
	 * expetion if player want to play without card selection
	 * @param cards
	 * @param buttonCheck
	 */
	private void playCards(ArrayList<Card> cards, String buttonCheck){
		if (((cards.size() != 0) && (buttonCheck.equals("Play"))) || ((cards.size() == 0) && (buttonCheck.equals("Pass")))){
			log.debug("sending cards");
			CardContainer container = new CardContainer(cards);
			handler.send(container);
		} else {
			log.debug("play emtpy card");
			view.displayClientInfo("Please select a card." );
		}
	}
	/**
	 * Get all cards of player
	 * @return normal and joker cards
	 */
	private ArrayList<BtnCard> playerCards(){
		ArrayList<BtnCard> cards = new ArrayList<BtnCard>();
		cards.addAll(view.btnCardHand);
		cards.addAll(view.btnJocker);
		return cards;
	}
	
	/**
	 * Get player informations
	 * @return player
	 */
	public Player getPlayer(){
		return getGameState().getPlayer(getToken());
	}
	/**
	 * Get next player
	 * to deal the player name (player 1) and to deal the active or inactive players
	 * @param player
	 * @return player + 1 (next player)
	 */
	public Player getNextPlayer(Player player) {
		int index = getGameState().playerList.indexOf(player);
		if (index+1 == getGameState().playerList.size()){
			return getGameState().playerList.get(0);
		}
		return getGameState().playerList.get(index+1);
	}
	
	/**
	 * Before new round begins the EndView will be displayed
	 */
	private void checkNewRound() {
		if (handler.getGameState().isNewRound()) {
			drawGameState();								//Update game field before show EndView (Game results)
			EndController endController = new EndController(handler);
			EndView endView = new EndView(endController,view.getJFrame());
			endController.updateView();
			endView.setModal(true);
			endView.pack();
			endView.setVisible(true);
		}
		
	}
	
	/**
	 * Action Performed
	 * for cards, play, pass and exit
	 */
	public void actionPerformed(ActionEvent e) {
        
		if (e.getSource().getClass() == BtnCard.class ){
			for( BtnCard btnCard : playerCards()){
				if ( btnCard == e.getSource()){ 
	        		if (btnCard.isSelected()){
	        			btnCard.setUnselected();
	        		}   else {
	        			btnCard.setSelected();
	        		}
        		}
	        }
		}
        if( e.getSource () ==  view.btnPlay){
           	ArrayList<Card> cards = new ArrayList<Card>();
          	
        	for (BtnCard btnCard : playerCards()) {
        		if(btnCard.isSelected()) {
            		cards.add(btnCard.getCard());
        		}
        	}
    		playCards(cards, "Play");
        }
        
        if(e.getSource() == view.btnPass){
        	ArrayList<Card> emtpyCards = new ArrayList<Card>();
        	for (BtnCard btnCards : playerCards()) {
        		btnCards.setUnselected();
        	}
        	playCards(emtpyCards, "Pass");	//play a emtpy ArrayList, Class Move will check if list is empty or not.
        }
        
        if(e.getSource() == view.btnExit) {
    		System.exit(0);
        }
	}
	
	/**
	 * Observer pattern
	 * check if a new rounds starts and 
	 * observe when game state refresh and revalidate the game field
	 */
	@Override
	public void update(Observable o, Object arg) {
		checkNewRound();
		view.drawGameState(handler.getGameState());
		view.getJFrame().getContentPane().revalidate();
	}

}