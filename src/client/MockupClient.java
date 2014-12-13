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
					//EndController controller = new EndController(handler);
					//EndView view = new EndView(controller);
					TableView view = new TableView(controller);
					controller.setView(view);
					//controller.setEndView(view);
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
