package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
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
	
	public void updateView() {
		fillRankTable();
		displayWinner();
		if (IsGameEnd()){
			view.btnButton.setIcon(view.iconExit);
			view.btnButton.setPreferredSize(new Dimension(58, 58));
		}else{
			view.btnButton.setText("Continue");
			view.btnButton.setPreferredSize(new Dimension(130, 58));
		}
	}
	
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
	
		
		ArrayList<Player> rankList = new ArrayList<Player>();
		rankList = handler.gameState.playerList;
		
		Collections.sort(rankList);
		
		for (int i = rankList.size()-1; i >= 0; i--) {
			String rank = Integer.toString(rankList.size()-i);
			String name = getPlayerName(rankList.get(i));
			String points = Integer.toString(rankList.get(i).getPlayerPoints());
			view.model.addRow(new Object[] {rank, name, points});
		}
		
		view.model.fireTableDataChanged();
		view.tblRanking.repaint();
		view.repaint();
		
	}
	private void displayWinner(){
		if (IsGameEnd() ){
			if (getWinner()) {
				view.lblEndGameStatusTitle.setText("Congratulation " + getPlayerName(getPlayer()) +" you're the winner!");
				view.imgResult = new JLabel(view.iconWinner);
				
			} else {
				view.imgResult = new JLabel(view.iconLoser);
				view.lblEndGameStatusTitle.setText("Sorry  " + getPlayerName(getPlayer()) +" you lost!");
			}
		} else {
			view.lblEndGameStatusTitle.setText("Gameresult");
		}
	}
	
	private boolean IsGameEnd() {
		for ( Player p : handler.getGameState().playerList){
			if ( p.getPlayerPoints() > 40 ){
				return true;
			}
		}
		return false;
	}

	public boolean getWinner() {
		boolean winner = false;

		 if (getPlayer().equals(getPlayerList().get(0))) // Winner
		{
			winner = true;
		}
		return winner;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == view.btnButton ){
			if (IsGameEnd()){
				if (view.btnButton.getText().equals("Continue")) {

				}
				System.exit(0);
			}else{
				view.dispose();
			}
				
		}
	}



}
