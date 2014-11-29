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
	GameState gameState;
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
	public void drawGameState(){
		log.debug("drawing new GameState");
		view.drawGameState(gameState);
		view.getJFrame().revalidate(); 	//setzt Componenten wieder auf validate und aktuallisiert die Layout's, wenn sich attribute geändert haben
		view.getJFrame().repaint();		//aktuallisierte componenten sollen sich "repaint"-en
	}
	
	public PlayerToken getToken() {
		return handler.getToken();
	}
	
	private void playCards(ArrayList<Card> cards){
		log.debug("sending cards");
		Container container = new Container(cards);
		handler.send(container);
	}
	private ArrayList<BtnCard> playerCards(){
		ArrayList<BtnCard> cards = new ArrayList<>();
		cards.addAll(view.btnCardHand);
		cards.addAll(view.btnJocker);
		return cards;
	}
	public String getPlayerName(){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(handler.getToken()); //?
		return name+playerNum;
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
    		playCards(cards);
        }
        
        if(e.getSource() == view.btnPass){
        	for (BtnCard btnCards : playerCards()) {
        		btnCards.setUnselected();
        	}
        	
        }
        
        if(e.getSource() == view.btnExit) {
    		System.exit(0);
        }
        
        if (e.getSource() == view.btnRules) {
        	view.displayRules();
        }
        /** Not finish yet, but also not working...**/
        //int clickCount;
        if (e.getSource() == view.btnSort ){
        
        	//!! ArrayList.sort(playerCards, new SortCardSuit());
        }
	}
	
	@Override
	public void update(Observable o, Object arg) {
		view.drawGameState(handler.getGameState());
		view.getJFrame().getContentPane().revalidate();
	}
	

}