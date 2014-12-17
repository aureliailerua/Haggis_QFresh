package client;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import library.GameState;
import library.Player;
import library.GameState.PlayerToken;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/** 
 * @author felicita.acklin
 * Klasse stellt die Daten für die EndView bereitet und bereitet
 * das Resultatsfenster und die Resultatstabelle entsprechend auf.
 * Passt EndView an Runden- oder Spielende an (Button und Anzeigebild)
 *
 */

public class EndController implements ActionListener,Observer{

	EndView view;
	ServerHandler handler;
	boolean mockup;
	Client client;
	private static final Logger log = LogManager.getLogger( EndController.class.getName() );
	
	
	public EndController(ServerHandler handler) {
		this.handler = handler;
		handler.addObserver(this);			
	}
	
	/**
	 * SetEndView
	 * @param view
	 */
	public void setEndView(EndView view){
		this.view = view;
	}

	/**
	 * Get all information about the players
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
	 * Get information about dedicated player
	 * @return player
	 */
	public Player getPlayer(){
		return getGameState().getPlayer(getToken());
	}

	/**
	 * Get the player list and its points
	 * @return ArrayList playerList
	 */
	public ArrayList<Player> getPlayerList() {
		return handler.gameState.playerList;
	}
	
	/**
	 * Get the name of a player
	 * @param player
	 * @return Player+Number (exp. Player 1)
	 */
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}
	
	/**
	 * Update EndView when round ends 
	 * if round finished show continue button, if game is finished show the winner/loser image and beer button (exit=
	 */
	public void updateView() {
		fillRankTable();
		displayWinner();
		if (IsGameEnd()){
			view.setBounds(50, 50, 1000, 500);
			view.setPreferredSize(new Dimension(1000, 500));
			
			view.setTitle("QFresh Haggis Game - Game Results");
			view.btnButton.setIcon(view.iconBeer);
			view.btnButton.setText("Beer!");
			view.btnButton.setPreferredSize(new Dimension(140, 58));
			view.panelImgResult.setPreferredSize(new Dimension(300,260));
			
			view.panelContent.add(view.panelImgResult);
			view.panelImgResult.add(view.imgResult);

		}else{
			view.setBounds(50, 50, 600, 500);
			view.setPreferredSize(new Dimension(600, 500));
			
			view.setTitle("QFresh Haggis Game - Intermediate Results");
			ImageIcon repeat = view.iconRepeat;
			view.btnButton.setIcon(new ImageIcon(repeat.getImage().getScaledInstance(30, 30,  java.awt.Image.SCALE_SMOOTH)));
			view.btnButton.setIcon(view.iconRepeat);
			view.btnButton.setText("Continue");
			view.btnButton.setPreferredSize(new Dimension(140, 58));
		}
	}
	
	/**
	 * Sort the player list according points 
	 * @return rankList
	 */
	private ArrayList<Player> getSortedPlayerList() {
		ArrayList<Player> rankList = new ArrayList<Player>();
		rankList = handler.gameState.playerList;
		Collections.sort(rankList);
		
		return rankList;
	}
	
	/**
	 * Set Game End
	 * @return isGameEnd
	 */
	private boolean IsGameEnd() {
		for ( Player p : handler.getGameState().playerList){
			if ( p.getPlayerPoints() > 50 ){	//POINTS => 250
				return true;
			}
		}
		return false;
	}

	/**
	 * Get last player in sorted rankList, how is equal to the winner
	 * @return winner
	 */
	public boolean getWinner() {
		boolean winner = false;

		 if (getPlayer().equals(getSortedPlayerList().get(getSortedPlayerList().size() - 1))) // Winner
		{
			winner = true;
		}
		return winner;
	}

	/**
	 * Create the content for the result table
	 */
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
	
	/**
	 * Display title and image for winner or loser
	 */
	private void displayWinner(){
		if (IsGameEnd() ){
			view.lbEndGameStatusTitle.setHorizontalAlignment(SwingConstants.CENTER);

			if (getWinner()) {
				view.lbEndGameStatusTitle.setText("Congratulation " + getPlayerName(getPlayer()) +" you're the winner!");
				view.imgResult.setIcon(view.iconWinner);

			} else {
				view.lbEndGameStatusTitle.setText("Sorry  " + getPlayerName(getPlayer()) +" you lost!");
				view.imgResult.setIcon(view.iconLoser);

			}
		} else {
			view.lbEndGameStatusTitle.setText("Gameresult");
			view.lbEndGameStatusTitle.setHorizontalAlignment(SwingConstants.LEFT);

		}
	}

	
	@Override
	public void update(Observable o, Object arg) {
		
	}

	/**
	 * Action Performed
	 */
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
