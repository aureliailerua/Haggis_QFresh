package server;

public class MaxPlayerException extends Exception {
	
	public MaxPlayerException(){
		super("Too many players connected, cannot add more");
	}

}
