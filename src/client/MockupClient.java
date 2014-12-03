package client;

import java.awt.EventQueue;
import java.net.Socket;

public class MockupClient {
	
	public static void main(String[] args ){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ServerHandlerMock handler = new ServerHandlerMock(new Socket());
					TableController controller = new TableController(handler);
					//StartView view = new StartView(controller);
					TableView view = new TableView(controller);
					controller.setView(view);
					//controller.setStartView(view);
					view.getJFrame().pack();
					view.getJFrame().setVisible(true);
					handler.sendGameState();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});	
	}
}
