package client;

import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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
			view.setBounds(0, 0, 1000, 500);
			view.setPreferredSize(new Dimension(1000, 500));
			
			view.btnButton.setIcon(view.iconBeer);
			view.btnButton.setText("Beer!");
			view.btnButton.setPreferredSize(new Dimension(140, 58));
			view.panelImgResult.setPreferredSize(new Dimension(300,260));
			
			view.panelContent.add(view.panelImgResult);
			view.panelImgResult.add(view.imgResult);

		}else{
			view.setBounds(0, 0, 600, 500);
			view.setPreferredSize(new Dimension(600, 500));
			
			ImageIcon repeat = view.iconRepeat;
			view.btnButton.setIcon(new ImageIcon(repeat.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)));
			view.btnButton.setIcon(view.iconRepeat);
			view.btnButton.setText("Continue");
			view.btnButton.setPreferredSize(new Dimension(140, 58));
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
		rankList = getSortedPlayerList();
				
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
	private ArrayList<Player> getSortedPlayerList() {
		ArrayList<Player> rankList = new ArrayList<Player>();
		rankList = handler.gameState.playerList;
		Collections.sort(rankList);
		
		return rankList;
	}
	private void displayWinner(){
		if (IsGameEnd() ){
			view.lblEndGameStatusTitle.setHorizontalAlignment(SwingConstants.CENTER);

			if (getWinner()) {
				view.lblEndGameStatusTitle.setText("Congratulation " + getPlayerName(getPlayer()) +" you're the winner!");
				view.imgResult.setIcon(view.iconWinner);

			} else {
				view.lblEndGameStatusTitle.setText("Sorry  " + getPlayerName(getPlayer()) +" you lost!");
				view.imgResult.setIcon(view.iconLoser);

			}
		} else {
			view.lblEndGameStatusTitle.setText("Gameresult");
			view.lblEndGameStatusTitle.setHorizontalAlignment(SwingConstants.LEFT);

		}
	}
	
	private boolean IsGameEnd() {
		for ( Player p : handler.getGameState().playerList){
			if ( p.getPlayerPoints() > 50 ){	//POINTS => 250
				return true;
			}
		}
		return false;
	}

	public boolean getWinner() {
		boolean winner = false;

		 if (getPlayer().equals(getSortedPlayerList().get(getSortedPlayerList().size() - 1))) // Winner
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
