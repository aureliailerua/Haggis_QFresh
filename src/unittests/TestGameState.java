package unittests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import library.*;
import library.GameState.PlayerToken;
import server.MaxPlayerException;
import server.GameHandler;
import server.Round;

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
		
		//sort Player cards
		ArrayList<Card> sortedCards = GameHandler.bubbleSort(gameState.playerList.get(0).getPlayerCards());
		int lastID = 0;
		for (Card card : sortedCards){
			assertTrue("Mistake in Sort-by-ID operation", card.getCardID()>lastID);
			lastID = card.getCardID();
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
	
	@Test
	public void testRoundTickMove() throws IOException, MaxPlayerException {
		//init
		GameState gameState = new GameState();
		gameState.newRound();
		
		//create three players with tokens
		gameState.playerList.add(new Player(GameState.PlayerToken.one));
		gameState.playerList.add(new Player(GameState.PlayerToken.two));
		gameState.playerList.add(new Player(GameState.PlayerToken.three));
		
		//create deck and add build some cardlists
		gameState.activeCardDeck = new CardDeck(gameState.playerList.size());
		for (Player player : gameState.playerList){
			player.setPlayerCards(gameState.activeCardDeck.give14Cards());
			player.setPlayerJokers(gameState.activeCardDeck.give3Jokers());
		}
		ArrayList<Card> lvCards1 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards1.add(gameState.playerList.get(0).getPlayerCards().get(i));
		}
		ArrayList<Card> lvCards2 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards2.add(gameState.playerList.get(0).getPlayerCards().get(i+3));
		}
		ArrayList<Card> lvCards3 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards3.add(gameState.playerList.get(1).getPlayerCards().get(i));
		}
		ArrayList<Card> lvCards4 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards4.add(gameState.playerList.get(2).getPlayerCards().get(i));
		}
		
		ArrayList<Card> lvPass = new ArrayList<Card>();
		
		//commit some moves and check passes 
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(2));
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(3));
		gameState.commitMove(PlayerToken.one, lvCards1);
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(2));
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(3));
		gameState.commitMove(PlayerToken.two, lvPass);
		assertTrue("bla",gameState.getActiveRound().getActiveTick().checkPass(2));
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(3));
		gameState.commitMove(PlayerToken.three, lvPass);
		assertTrue("bla",gameState.getActiveRound().getActiveTick().checkPass(2));
		assertTrue("bla",gameState.getActiveRound().getActiveTick().checkPass(3));
		gameState.commitMove(PlayerToken.one, lvCards2);
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(2));
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(3));
		gameState.commitMove(PlayerToken.two, lvCards3);
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(2));
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(3));
		gameState.commitMove(PlayerToken.three, lvCards4);
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(2));
		assertFalse("bla",gameState.getActiveRound().getActiveTick().checkPass(3));

		//check history and active move
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(0).getCardList(), (lvCards1));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(0).getMovingPlayer(), (PlayerToken.one));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(1).getCardList(), (lvPass));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(1).getMovingPlayer(), (PlayerToken.two));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(2).getCardList(), (lvPass));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(2).getMovingPlayer(), (PlayerToken.three));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(3).getCardList(), (lvCards2));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(3).getMovingPlayer(), (PlayerToken.one));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(4).getCardList(), (lvCards3));
		assertSame("bla",gameState.getActiveRound().getActiveTick().moveList.get(4).getMovingPlayer(), (PlayerToken.two));
		assertSame("bla",gameState.getActiveRound().getActiveTick().getActiveMove().getCardList(), (lvCards4));
		assertSame("bla",gameState.getActiveRound().getActiveTick().getActiveMove().getMovingPlayer(), (PlayerToken.three));
	}
	
	@Test
	public void testTopCards() throws IOException, MaxPlayerException {
		//init
		GameState gameState = new GameState();
		gameState.roundList.add(new Round());
		
		//create three players with tokens
		gameState.playerList.add(new Player(GameState.PlayerToken.one));
		gameState.playerList.add(new Player(GameState.PlayerToken.two));
		gameState.playerList.add(new Player(GameState.PlayerToken.three));
		
		//create deck and add build some cardlists
		gameState.activeCardDeck = new CardDeck(gameState.playerList.size());
		for (Player player : gameState.playerList){
			player.setPlayerCards(gameState.activeCardDeck.give14Cards());
			player.setPlayerJokers(gameState.activeCardDeck.give3Jokers());
		}
		ArrayList<Card> lvCards1 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards1.add(gameState.playerList.get(0).getPlayerCards().get(i));
		}
		ArrayList<Card> lvCards2 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards2.add(gameState.playerList.get(0).getPlayerCards().get(i+3));
		}
		ArrayList<Card> lvCards3 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards3.add(gameState.playerList.get(1).getPlayerCards().get(i));
		}
		ArrayList<Card> lvCards4 = new ArrayList<Card>();
		for (int i = 0; i <3; i++){
			lvCards4.add(gameState.playerList.get(2).getPlayerCards().get(i));
		}
		
		ArrayList<Card> lvPass = new ArrayList<Card>();
		
		//commit some moves and check topcards 
		assertTrue("blah",gameState.getTopCards().isEmpty());
		gameState.commitMove(PlayerToken.one, lvCards1);
		assertSame("blah",gameState.getTopCards(),lvCards1);
		gameState.commitMove(PlayerToken.two, lvPass);
		assertSame("blah",gameState.getTopCards(),lvCards1);
		gameState.commitMove(PlayerToken.three, lvPass);
		assertSame("blah",gameState.getTopCards(),lvCards1);
		gameState.commitMove(PlayerToken.one, lvCards2);
		assertSame("blah",gameState.getTopCards(),lvCards2);

		//new Tick and check topcards
		gameState.newTick();
		assertTrue("blah",gameState.getTopCards().isEmpty());
		gameState.commitMove(PlayerToken.one, lvCards3);
		assertSame("blah",gameState.getTopCards(),lvCards3);
		
		//new round and check topcards
		gameState.newRound();
		assertTrue("blah",gameState.getTopCards().isEmpty());
		gameState.commitMove(PlayerToken.one, lvCards4);
		assertSame("blah",gameState.getTopCards(),lvCards4);


	}
}
