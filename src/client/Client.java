package client;

import java.awt.EventQueue;
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
	private ServerHandler handler;
	private GuiController guiController;
	private Gui window;
	GameState.PlayerToken token;

	public Client() throws IOException{
	
		String myLocation = Client.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		prop = new PropertyFile(myLocation);
		
		this.address  = InetAddress.getByName(prop.getProperty("server.address"));
		this.port = Integer.parseInt(prop.getProperty("port"));
		
	}
	public void startup() throws IOException{
		
		Socket socket = new Socket(address,port);
		this.handler = new ServerHandler(socket);
		this.guiController = new GuiController(handler);
		this.window = new Gui(guiController);
		guiController.initialize();
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client.startGui();

	}

}