package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import library.Card;
import library.Container;
import library.CardDeck;
import library.GameState;
import library.GameState.PlayerToken;
import library.Player;
import library.StartContainer;
import client.StartView;

/**
 * @author felicita.acklin / benjamin.indermuehle
 * Klasse verarbeitet Informationen der Client Klasse und überprüft wie viele Player sich eingeloggt haben.
 * Startet das Game, wenn genügend Player angemeldet sind.
 *
 */
public class StartController implements ActionListener,Observer{

	StartView view;
	ServerHandler handler;
	boolean mockup;						//Boolean for Mockup and simple GUI Tests
	Client client;
	private static final Logger log = LogManager.getLogger( TableController.class.getName() );
	
	public StartController(ServerHandler handler,Client client) {
		this.handler = handler;
		this.client = client;
		handler.addObserver(this);
	}
	
	/**
	 * Set View 
	 * @param view
	 */
	public void setView(StartView view){
		this.view = view;
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
	 * Get all informations of dedicated player
	 * @return
	 */
	public Player getPlayer(){
		return getGameState().getPlayer(getToken());
	}
	
	/**
	 * Observe count of player that are join in the game
	 * Display joined player in joinedPlayer table
	 * Enable start button if min. 2 players have joined
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (handler.gameState.getState() == GameState.State.running){
			view.getJFrame().dispose();
			handler.deleteObserver(this);
			client.startGame();
		}
		view.model.setRowCount(0);
		view.joinedPlayer.clear();
		for (Player p: handler.gameState.playerList){
			view.model.addRow(new Object[]{getPlayerName(p)});
		}
		if ( handler.gameState.playerList.size() <2 ){
			view.model.addRow(new Object[]{"Waiting for more players..."});
		}else{
			view.btnStart.setEnabled(true);
		}
		//Update all elements
		view.model.fireTableDataChanged();
		view.tblLogginPlayer.repaint();
		view.getJFrame().repaint();
			
	}
	/**
	 * Get name of player
	 * @param player
	 * @return Player + Number (eg. Player 1)
	 */
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}
	
	/**
	 * Action Performance for start and exit button
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.btnStart ){
			handler.send(new StartContainer());
		}
		
		if(e.getSource() == view.btnExit ){
			System.exit(0);
		}
		
	}
}
