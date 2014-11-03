package unittests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;
import library.*;
import library.GameState.PlayerToken;
import server.MaxPlayerException;

/**
 * @author andreas.denger
 *
 */
public class TestGameState {

	@BeforeClass
	public static void setupBeforeClass() throws Exception {

	}
	
	@Test
	public void testPlayerCreation() throws IOException, MaxPlayerException {
		//init
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
		
		//check if successful
		assertEquals("Number of player in playerList wrong", 3, gameState.playerList.size());
		assertSame("token wrong", PlayerToken.one, gameState.playerList.get(0).getToken());
		assertSame("token wrong", PlayerToken.two, gameState.playerList.get(1).getToken());
		assertSame("token wrong", PlayerToken.three, gameState.playerList.get(2).getToken());
		assertEquals("Number of Haggis cards wrong", 3, gameState.activeCardDeck.cardDeck.size());
		for (Player player : gameState.playerList){
			assertEquals("number of cards wrong", 14, player.getPlayerCards().size());
			assertEquals("number of cards wrong", 3, player.getPlayerJokers().size());
		}
	}
}
