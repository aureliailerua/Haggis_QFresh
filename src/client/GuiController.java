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
	public void initialize(){
		gui.initialize();
		gui.setPlayer(String.format("You are Player %s",handler.token.toString()));
		updateTable();
		
	}
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
	@Override
	public void update(Observable o, Object arg) {
		updateTable();
		
	}
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
