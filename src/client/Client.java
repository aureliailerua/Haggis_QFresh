package client;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import server.Server;
import library.GameState;
import library.PropertyFile;

/**
 * @author benjamin.indermuehle
 * Client CLass is responsible for starting up the Client
 */
public class Client {

	private PropertyFile prop;
	private int port;
	private InetAddress address;
	private ServerHandler handler;
	GameState.PlayerToken token;
	private static final Logger log = LogManager.getLogger( Client.class.getName() );

	public Client() throws IOException{
	
		prop = new PropertyFile();
		this.address  = InetAddress.getByName(prop.getProperty("client.address"));
		this.port = Integer.parseInt(prop.getProperty("port"));
	}
	
	/**
	 * Starts connection to client, instantiates GUI and supporting classes.
	 * @throws IOException is thrown when client cannot connect to server.
	 * 
	 */
	public void startup() throws IOException{
		Socket socket = new Socket(address,port);
		this.handler = new ServerHandler(socket);
		StartController startController = new StartController(handler,this);
		StartView startView = new StartView(startController);
		startController.setView(startView);
		startView.getJFrame().pack();
		startView.getJFrame().setVisible(true);
		handler.initialize();
		new Thread(this.handler).start();
	}
	
	public static void startGui(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client client = new Client();
					client.startup();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		Client.startGui();
	}

	public void startGame() {
		TableController tableController = new TableController(handler);
		TableView view = new TableView(tableController);
		tableController.setView(view);
		view.getJFrame().pack();
		view.getJFrame().setVisible(true);
		tableController.drawGameState();
	}
}