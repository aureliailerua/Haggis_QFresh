package client;

import library.GameState;
import client.TableView;


public class TableController {
	TableView view;
	
	public TableController(TableView view) {
		this.view = view;
		
	}
	private GameState generateMockupGameState() {
		return new GameState();
	}
}
