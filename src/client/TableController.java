package client;

import java.util.ArrayList;

import library.CardDeck;
import library.GameState;
import library.GameState.PlayerToken;
import library.Player;
import client.TableView;


public class TableController {
	TableView view;
	PlayerToken token;
	GameState gameState;
	
	public TableController(TableView view) {
		this.view = view;
		view.setController(this);
		token = PlayerToken.one; // FIXME this is a mockup
		gameState = generateMockupGameState();
		
		view.drawGameState(gameState);
	}
	
	public PlayerToken getToken() {
		return token;
	}

	private GameState generateMockupGameState() {
		GameState gameState = new GameState();
		gameState.playerList = new ArrayList<Player>();
		
		//create three players with tokens
		gameState.playerList.add(new Player(GameState.PlayerToken.one));
		gameState.playerList.add(new Player(GameState.PlayerToken.two));
		gameState.playerList.add(new Player(GameState.PlayerToken.three));
		
		//create deck and add cards / jokers to players
		gameState.activeCardDeck = new CardDeck(gameState.playerList.size());
		for (Player player : gameState.playerList){
			player.setPlayerCards(gameState.activeCardDeck.give14Cards());
			player.setPlayerJokers(gameState.activeCardDeck.give3Jokers());
		}
		return gameState;
	}
}
