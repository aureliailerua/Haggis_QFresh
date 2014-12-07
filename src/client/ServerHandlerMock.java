package client;

import java.util.ArrayList;

import library.Card;
import library.CardContainer;
import library.CardDeck;
import library.CardContainer;
import library.GameState;
import library.Player;

import library.Container;
import java.net.Socket;

import server.Move;
import server.Round;
import server.Tick;
import library.GameState.PlayerToken;

public class ServerHandlerMock extends ServerHandler {
	
	
	public ServerHandlerMock(Socket socket){
		super(socket);
		generateMockupGameState();
		token = library.GameState.PlayerToken.one;
		
		
	}
	private void generateMockupGameState() {
		
		this.gameState.playerList = new ArrayList<Player>();
		
		//create three players with tokens
		this.gameState.playerList.add(new Player(GameState.PlayerToken.one));
		this.gameState.playerList.add(new Player(GameState.PlayerToken.two));
		this.gameState.playerList.add(new Player(GameState.PlayerToken.three));
		
		//create deck and add cards / jokers to players
		this.gameState.activeCardDeck = new CardDeck(this.gameState.playerList.size());
		for (Player player : this.gameState.playerList){
			player.setPlayerCards(this.gameState.activeCardDeck.give14Cards());
			player.setPlayerJokers(this.gameState.activeCardDeck.give3Jokers());
		}
	}

	public void sendGameState() {
		super.setChanged();
		super.notifyObservers();
		
	}
	
	@Override
	public void send(Container container){
		CardContainer cardContainer = (CardContainer)container;
		for (Card card: cardContainer.getPlayCards()){
			if (card.getCardSuit() == "joker"){
				this.gameState.getPlayer(token).getPlayerJokers().remove(card);
			}else{
				this.gameState.getPlayer(token).getPlayerCards().remove(card);
			}
		}
		Move move = new Move(token,cardContainer.getPlayCards());
		if (this.gameState.roundList.size() == 0){
			Round startingRound = new Round();
			Tick startingTick = new Tick();
			startingTick.moveList = new ArrayList<Move>();
			startingRound.tickList = new ArrayList<Tick>();
			startingRound.tickList.add(startingTick);
			gameState.roundList = new ArrayList<Round>();
			gameState.roundList.add(startingRound);
		}
		Round round = this.gameState.roundList.get(this.gameState.roundList.size()-1);
		Tick tick = round.tickList.get(round.tickList.size()-1);
		tick.moveList.add(move);
		sendGameState();
	}
}
