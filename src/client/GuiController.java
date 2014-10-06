package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import library.GameState;
import library.Move;

public class GuiController implements Observer,ActionListener{
	private Gui gui;
	private ServerHandler handler;
	
	public GuiController(ServerHandler handler){
		this.handler = handler;
		this.handler.addObserver(this);
	}
	public void setGui(Gui gui) {
		this.gui = gui;
		
	}
	/**
	 * initialize gui and fill all fields 
	 */
	public void initialize(){
		gui.initialize();
		gui.setPlayer(String.format("You are Player %s",handler.token.toString()));
		updateTable();
		
	}
	
	/**
	 * udpate all fields of the gui with the gamestate values 
	 */
	private void updateTable(){
		String activePlayer;
		if (handler.gameState.getState() == GameState.State.startup){
			activePlayer = "Game has not started yet";
		}else{
			activePlayer = (String.format("It's player %s's  turn",handler.gameState.getActivePlayer().toString()));
		}
		gui.setCurrentPlayer(activePlayer);
		gui.setLblAddition(String.valueOf(handler.gameState.getNumber()));
		gui.redraw();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 * 
	 * update will be called by the serverHandler when a new GameState is received.
	 *  
	 */
	@Override
	public void update(Observable o, Object arg) {
		updateTable();
		
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * action performed is called by the gui whenever an action is performed
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String additionString = gui.getAddition();
		try {
			int addition = Integer.valueOf(additionString);
			Move move = new Move();
			move.setAddition(addition);
			handler.send(move);
		} catch(NumberFormatException ex){
			gui.setAddition("");
		}
	}

}
