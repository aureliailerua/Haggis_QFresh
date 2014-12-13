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

public class StartController implements ActionListener,Observer{

	StartView view;
	ServerHandler handler;
	boolean mockup;
	Client client;
	private static final Logger log = LogManager.getLogger( TableController.class.getName() );
	
	public StartController(ServerHandler handler,Client client) {
		this.handler = handler;
		this.client = client;
		handler.addObserver(this);
	}
	
	public void setView(StartView view){
		this.view = view;
	}
	
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
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}
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
