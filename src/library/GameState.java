package library;

import library.Move.PlayerToken;



public class GameState {

	/**
	 * 
	 */
	public enum PlayerToken{ one,two,three };
	private int numPlayers;
	
	public GameState(){
		this.numPlayers = 0;
		
	}
	public PlayerToken addPlayer(){
		switch(this.numPlayers++){
			case 0:
				return GameState.PlayerToken.one;
			case 1: 
				return GameState.PlayerToken.two;
			case 2: 
				return GameState.PlayerToken.three;
			default:
				return null;
		}
	}
	public PlayerToken makeMove(){
		return PlayerToken.one;
	}

	
}
