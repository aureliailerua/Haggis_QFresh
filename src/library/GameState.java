package library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import org.apache.logging.log4j.*;

import server.MaxPlayerException;
import server.Move;
import server.Round;
import server.Tick;


/**
 * @author benjamin.indermuehle / andreas.denger
 * This is a prototype class and needs to be changed.
 * in the current state it manages a number internally
 */
public class GameState extends Observable implements Serializable {

	public static int MAX_PLAYERS =2;
	public enum PlayerToken{ one,two,three };
	public enum State { startup, running, end};
	
	private PlayerToken activePlayer;
	private State state;
	private int numPlayers;
	private int number;
	
	public ArrayList<Player> playerList;
	public CardDeck activeCardDeck;
	public ArrayList<Round> roundList;
	
	private String activePattern;
	
	private static final Logger log = LogManager.getLogger( GameState.class.getName() );
	
	public GameState(){
		this.numPlayers = 0;
		this.number= 0;
		this.activePlayer = PlayerToken.one;
		this.state = State.startup;	
		this.playerList = new ArrayList<Player>();
		this.roundList = new ArrayList<Round>();
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
		setChanged();
	}

	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
		setChanged();
	}

	public int getNumPlayers() {
		return numPlayers;
	}
	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}
	
	public Round getActiveRound(){
		return roundList.get(roundList.size()-1);
	}

	public PlayerToken makeMove(){
		return PlayerToken.one;
	}
	
	/**
	 * Allow max 3 players to join, return token for each player if the game is full a MaxPlayerException will be thrown 
	 * @return PlayerToken
	 * @throws MaxPlayerException 
	 */
	public PlayerToken addPlayer() throws MaxPlayerException{
		
		if (this.numPlayers == GameState.MAX_PLAYERS){
			throw new MaxPlayerException();
		}
		PlayerToken token = null;
		switch(this.numPlayers){
			case 0:
				token = GameState.PlayerToken.one;
				break;
			case 1: 
				token = GameState.PlayerToken.two;
				break;
			case 2: 
				token = GameState.PlayerToken.three;
				break;
			default:
				return null;
		}
		this.playerList.add(new Player(token));
		System.out.println("Player created: "+playerList.get(playerList.size()-1).getToken());
		this.numPlayers = this.numPlayers+1;
		return token;
	}
	
	public Object getActivePlayer() {
		return activePlayer;
	}
	
	public void setActivePlayer(PlayerToken token) {
		activePlayer = token;
		
	}
	
	public void removePlayer(){
		numPlayers = numPlayers-1;
		
	}
	
	public void commitMove(PlayerToken lvToken, ArrayList<Card> lvCards){
		getActiveRound().getActiveTick().addMove(new Move(lvToken,lvCards));
		for (Player p : playerList){
			if (p.getToken().equals(lvToken)){
				p.removeCardsFromPlayer(lvCards);
			}
		}
		
	}

	public void rejectMove(){
		//TODO ..
	}
	
	public boolean checkEndRound(){
		//TODO ..
		return false;
	}
	
	public void newRound(){
		//TODO set RoundWinner		
		roundList.add(new Round());
		setActivePattern("");
	}
	
	public boolean checkEndTick(){
		return (getActiveRound().getActiveTick().checkPass(this.numPlayers));
	}
	
	public void newTick(){
		//TODO set TickWinner
		getActiveRound().addNewTick();
		setActivePattern("");
	}

	public boolean checkMove(ArrayList<Card> lvCards) {
		
		//TODO many checks and set pattern
		// - card count
		// - lowest card
		// - analyze pattern
		// - compare pattern
		//return true (commmit) or false (reject)
	
		return true;
	}

	/**
	 * @return the activePattern
	 */
	public String getActivePattern() {
		return activePattern;
	}

	/**
	 * @param activePattern the activePattern to set
	 */
	public void setActivePattern(String activePattern) {
		this.activePattern = activePattern;
	}
	
	
	public ArrayList<Card> getTopCards(){
		ArrayList<Move> lvMoveList = getActiveRound().getActiveTick().moveList;
		for (int i =  lvMoveList.size()-1; i >=0; i--){
			if (!lvMoveList.get(i).getCardList().isEmpty()){
				return lvMoveList.get(i).getCardList();
			}
		}
		return new ArrayList<Card>();
	}
}