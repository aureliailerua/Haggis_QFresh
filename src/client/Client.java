package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import library.GameState;
import library.PropertyFile;

public class Client {

	private PropertyFile prop;
	private int port;
	private InetAddress address;
	private GameState gameState;
	GameState.PlayerToken token;

	public Client() throws IOException{
	
		String myLocation = Client.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		prop = new PropertyFile(myLocation);
		
		address  = InetAddress.getByName(prop.getProperty("server.address"));
		port = Integer.parseInt(prop.getProperty("port"));
		Socket socket = new Socket(address,port);
		ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
		System.out.println("client socket established");
		try {
			this.token = (GameState.PlayerToken) in.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(token);
    	System.out.println("here");
	}
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Client client = new Client();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
