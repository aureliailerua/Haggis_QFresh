package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.Container;
import library.CardDeck;
import library.GameState;
import library.GameState.PlayerToken;
import library.Player;
import client.TableView;


public class TableController implements ActionListener,Observer{
	TableView view;
	StartView startview;
	ServerHandler handler;
	boolean mockup;
	private static final Logger log = LogManager.getLogger( TableController.class.getName() );
	
	public TableController(ServerHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
			
	}
	public void setView(TableView view){
		this.view = view;
	}
	
	
	public void setStartView(StartView view){
		this.startview = view;
	}
	
	/*public void drawStartTable() {
		startview.getJFrame();
	}*/
	public void drawGameState(){
		log.debug("drawing new GameState");
		view.drawGameState(getGameState());
		view.getJFrame().revalidate(); 	//setzt Componenten wieder auf validate und aktuallisiert die Layout's, wenn sich attribute geändert haben
		view.getJFrame().repaint();		//aktuallisierte componenten sollen sich "repaint"-en
	}
	
	public PlayerToken getToken() {
		return handler.getToken();
	}
	public GameState getGameState(){
		return handler.gameState;
	}
	private void playCards(ArrayList<Card> cards, String buttonCheck){
		if (((cards.size() != 0) && (buttonCheck.equals("Play"))) || ((cards.size() == 0) && (buttonCheck.equals("Pass")))){
		//if((cards.size() != 0) && (buttonCheck.equals("Play"))) {
			log.debug("sending cards");
			Container container = new Container(cards);
			handler.send(container);
		} else {
			log.debug("play emtpy card");
			view.displayClientInfo("you have not selected any cards! And you shoult play a cards when you push the play button" );
		}
	}
	private ArrayList<BtnCard> playerCards(){
		ArrayList<BtnCard> cards = new ArrayList<BtnCard>();
		cards.addAll(view.btnCardHand);
		cards.addAll(view.btnJocker);
		return cards;
	}
	
	public Player getPlayer(){
		return getGameState().getPlayer(getToken());
	}
	
	/**
	 * Sort Cards
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
        
        if (e.getSource() == view.btnRules) {
        	view.displayRules();
        }
        if (e.getSource() == view.btnSort ){
        }
	}
	
	@Override
	public void update(Observable o, Object arg) {
		view.drawGameState(handler.getGameState());
		view.getJFrame().getContentPane().revalidate();
	}
	public Player getNextPlayer(Player player) {
		// TODO Auto-generated method stub
		int index = getGameState().playerList.indexOf(player);
		if (index+1 == getGameState().playerList.size()){
			return getGameState().playerList.get(0);
		}
		return getGameState().playerList.get(index+1);
	}
	
	

	

}