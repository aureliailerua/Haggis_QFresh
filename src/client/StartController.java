package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.Container;
import library.CardDeck;
import library.GameState;
import library.GameState.PlayerToken;
import library.Player;
import client.StartView;

public class StartController implements ActionListener,Observer{

	StartView view;
	ServerHandler handler;
	boolean mockup;
	private static final Logger log = LogManager.getLogger( TableController.class.getName() );
	
	public StartController(ServerHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
	}
	
	public void setView(StartView view){
		this.view = view;
	}
	/**public void drawGameState(){
		log.debug("drawing new GameState");
		view.drawGameState(getGameState()); //kommt vom tableView!
		view.getJFrame().revalidate(); 	//setzt Componenten wieder auf validate und aktuallisiert die Layout's, wenn sich attribute geändert haben
		view.getJFrame().repaint();		//aktuallisierte componenten sollen sich "repaint"-en
	}**/
	
	public PlayerToken getToken() {
		return handler.getToken();
	}
	public GameState getGameState(){
		return handler.gameState;
	}
	
	public Player getPlayer(){
		return getGameState().getPlayer(getToken());
	}
	
	@Override
	public void update(Observable o, Object arg) {
		//view.drawGameState(handler.getGameState());
		//view.getJFrame().getContentPane().revalidate();
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
