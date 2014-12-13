package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.UIManager;

import library.GameState;
import library.Player;
import library.GameState.PlayerToken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EndController implements ActionListener,Observer{

	EndView view;
	ServerHandler handler;
	boolean mockup;
	Client client;
	private static final Logger log = LogManager.getLogger( TableController.class.getName() );
	
	
	public void setEndView(EndView view){
		this.view = view;
	}
	
	public EndController(ServerHandler handler) {
		this.handler = handler;
		handler.addObserver(this);
			
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
	
	public ArrayList<Player> getPlayerList() {
		return handler.gameState.playerList;
	}
	
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}

	public void fillRankTable(){
		/**
		view.model.addRow(new Object[] {"1", "Player3", "250"});
		view.model.addRow(new Object[] {"2", "Player1", "150"});
		view.model.addRow(new Object[] {"3", "Player2", "50"});

		view.model.fireTableDataChanged();
		view.tblRanking.repaint();
		view.getJFrame().repaint();
		
		
		ArrayList<Player> rankList = new ArrayList<Player>();
		rankList = handler.gameState.playerList;
		
		Collections.sort(rankList);
		
		for (int i = 0; i < rankList.size(); i++) {
			String rank = Integer.toString(i + 1);
			String name = getPlayerName(rankList.get(i));
			String points = Integer.toString(rankList.get(i).getPlayerPoints());
			view.model.addRow(new Object[] {rank, name, points});
		}**/
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.btnExit ){
			System.exit(0);
		}
	}



}
