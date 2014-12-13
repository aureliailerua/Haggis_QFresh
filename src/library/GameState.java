	package library;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

import org.apache.logging.log4j.*;

import server.MaxPlayerException;
import server.Move;
import server.Pattern;
import server.Round;
import server.Tick;


/**
 * @author benjamin.indermuehle / andreas.denger
 * 
 */
public class GameState extends Observable implements Serializable {

	public static int MAX_PLAYERS =3;
	public enum PlayerToken{ one,two,three };
	public enum State { startup, running, end};
	
	private PlayerToken activePlayer;
	private State state;
	private int numPlayers;
	private int number;
	private String clientInfo = "";
	private boolean newRound; 
	
	private Pattern activePattern;
	
	public ArrayList<Player> playerList;
	public CardDeck activeCardDeck;
	public ArrayList<Round> roundList;
	
	
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
		log.debug("Player created: "+playerList.get(playerList.size()-1).getToken());
		this.numPlayers = this.numPlayers+1;
		this.setChanged();
		return token;
	}
	
	public PlayerToken getActivePlayer() {
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
		setChanged();
	}

	public void rejectMove(){
		setChanged();
	}
	
	public boolean checkEndRound(){
		if (numPlayers == 2){
			log.debug("Check for PLAYER being finished - "
					+"   one: "+playerList.get(0).isFinished()
					+"   two: "+playerList.get(1).isFinished());
			if (playerList.get(0).isFinished() || playerList.get(1).isFinished()){
				return true;
			}
		} else if (numPlayers == 3){
			log.debug("Check for PLAYER being finished - "
					+"   one: "+playerList.get(0).isFinished()
					+"   two: "+playerList.get(1).isFinished()
					+"   three: "+playerList.get(2).isFinished());
			if (playerList.get(0).isFinished() && playerList.get(1).isFinished()){
				return true;
			} else if (playerList.get(0).isFinished() && playerList.get(2).isFinished()){
				return true;
			} else if (playerList.get(1).isFinished() && playerList.get(2).isFinished()){
				return true;
			}
		} else {
			throw new IllegalArgumentException("numPlayers not as expected");
		}
		return false;
	}
	
	public void endActiveRound(){
		//extracting remaining Cards of the "losing" Player and add the Points to the roundWinner
		for (Player lvPlayer : playerList){
			ArrayList<Card> lvCombinedList = new ArrayList<Card>();
			lvCombinedList.addAll(lvPlayer.getPlayerCards());
			lvCombinedList.addAll(lvPlayer.getPlayerCards());
			if (lvCombinedList.size()>0){
				log.debug("ROUND - remaining Cards of Player "+lvPlayer.getToken()+": "+lvCombinedList.size());
				log.debug("ROUND - adding remaining Cards to roundWinner Player "+ getActiveRound().getRoundWinner());

				for (Card lvCard : lvCombinedList){
					getPlayerObject(getActiveRound().getRoundWinner()).addPoints(lvCard.getCardPoint());
					log.debug("ROUND - Counting Points - ID: "+lvCard.getCardID()+" Points: "+lvCard.getCardPoint());
				}
			}
		}
		
		//extracting Haggis-Cards  and add the Points to the roundWinner
		log.debug("ROUND - size of Haggis stack: "+activeCardDeck.getCardDeck().size());
		log.debug("ROUND - adding Haggis to roundWinner Player "+ getActiveRound().getRoundWinner());
		log.debug("ROUND - Points of Player "+getActiveRound().getRoundWinner()+" before Haggis: "+ getPlayerObject(getActiveRound().getRoundWinner()).getPlayerPoints());
		for (Card lvCard : activeCardDeck.getCardDeck()){
			getPlayerObject(getActiveRound().getRoundWinner()).addPoints(lvCard.getCardPoint());
			log.debug("ROUND - Counting Points - ID: "+lvCard.getCardID()+" Points: "+lvCard.getCardPoint());
		}

		log.debug("ROUND - Points of Player "+getActiveRound().getRoundWinner()+" after Haggis: "+ getPlayerObject(getActiveRound().getRoundWinner()).getPlayerPoints());
	}
	
	public void newRound(){
		roundList.add(new Round());
//TODO RESET ACTIVE PATTERN???
		activeCardDeck = new CardDeck(getNumPlayers());
		
		for (Player player : playerList){
			player.setPlayerCards(activeCardDeck.give14Cards());
			player.setPlayerJokers(activeCardDeck.give3Jokers());
		}
	}
	
	public boolean checkEndTick(){
		if (getActiveRound().getRoundWinner()!=null){
			return (getActiveRound().getActiveTick().checkPass(playerList.size()-1));
		}
		return (getActiveRound().getActiveTick().checkPass(playerList.size()));
	}
	
	public void endActiveTick(){
		//work out tickwinner
		ArrayList<Move> lvMoveList = getActiveRound().getActiveTick().moveList;
		PlayerToken lvTickWinner = null;
		for (int i =  lvMoveList.size()-1; i >=0; i--){
			if (!lvMoveList.get(i).getCardList().isEmpty()){
				lvTickWinner = lvMoveList.get(i).getMovingPlayer();
				getActiveRound().getActiveTick().setTickWinner(lvTickWinner);
				log.debug("TICK - setTickWinner Player "+ lvTickWinner);
				break;
			}
		}
		
		//add cardpoints to tickwinner
		log.debug("TICK - Points of Player "+lvTickWinner+" before counting: "+ getPlayerObject(lvTickWinner).getPlayerPoints());
		for (Move lvMove : lvMoveList){
			for (Card lvCard : lvMove.getCardList()){
				getPlayerObject(lvTickWinner).addPoints(lvCard.getCardPoint());
				log.debug("TICK - Counting Points - ID: "+lvCard.getCardID()+" Points: "+lvCard.getCardPoint());
			}
		}
		log.debug("TICK - Points of Player "+lvTickWinner+" after counting: "+ getPlayerObject(lvTickWinner).getPlayerPoints());
	}
	
	public void newTick(){
		getActiveRound().addNewTick();
	}

	public boolean checkMove(ArrayList<Card> lvCards) {
		//TODO call analyzePattern or comparePattern
		//FIXME NullPointerException at server.Pattern.comparePatternTEMP(Pattern.java:95)

		for (Card c : lvCards){
			log.debug("CHECKMOVE -   ID "+ c.getCardID()+"    Suit: "+c.getCardSuit());
		}
		
		// analyzePattern if its the first move of the trick
		if (getActiveRound().getActiveTick().moveList.isEmpty()){
			log.debug("PATTERN - attempting analyzePattern");
			String lvPatternString = "";
			activePattern = new Pattern(lvCards);
			lvPatternString = activePattern.analyzePattern();
			
			if (lvPatternString.equals("nix")){
				return false;
			} else {
				log.debug("Player "+activePlayer+" plays " +lvPatternString);
				return true;
			}
			
			
		} else {  // comparePattern if its NOT the first move of the trick
			log.debug("PATTERN - attempting comparePattern");
			Pattern newPattern = new Pattern(lvCards);
			if (newPattern.comparePattern(activePattern)){
				setActivePattern(newPattern);
				log.debug("Player "+activePlayer+" plays " +getActivePattern().patternName);
				return true;
			} else {
				return false;
			}
		}
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
	public boolean checkIsPlayerFinished(PlayerToken lvToken){
		for (Player p : playerList){
			if (p.getToken().equals(lvToken)){
				if (p.getPlayerCards().size() + p.getPlayerJokers().size() == 0){
					p.setPlayerIsfinished(true);
					log.debug("PLAYER "+p.getToken()+" - is out of cards");
					return true;
				}
			}
		}
		return false;
	}
	
	public Player getPlayer( PlayerToken token){
		for (Player player: playerList){
			if( player.getToken() == token){
				return player;
			}
		}
		return null;
	}
	public Player getPlayerObject(PlayerToken lvToken){
		Player lvReturn = null;
		for (Player p : playerList){
			if (p.getToken().equals(lvToken)){
				lvReturn = p;
			}
		}
		return lvReturn;
	}
	public int getPlayerPoints(PlayerToken lvToken){
		return getPlayerObject(lvToken).getPlayerPoints();
	}

	public String getClientInfo() {
		return clientInfo;
	}

	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
	}

	public Pattern getActivePattern() {
		return activePattern;
	}

	public void setActivePattern(Pattern activePattern) {
		this.activePattern = activePattern;
	}

	public boolean isNewRound() {
		return newRound;
	}

	public void setNewRound(boolean newRound) {
		this.newRound = newRound;
	}
}