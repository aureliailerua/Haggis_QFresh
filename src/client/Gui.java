package client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Gui {

	private JFrame frame;
	private GuiController controller;
	private JTextField txtfAddition;
	private JLabel lblPlayerID;
	private JLabel lblCurrentPlayer;
	private JLabel lblAddition;
	
	

	/**
	 * Create the application.
	 */

	public Gui(GuiController controller) {
		this.controller = controller;
		controller.setGui(this);
		
	}

	/**
	 * We need to create the instance variables of the GUI before drawing it so the GuiController can
	 * fill them with content.
	 * @wbp.parser.entryPoint
	 * 
	 */
	protected void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel plPlayerInfo = new JPanel();
		frame.getContentPane().add(plPlayerInfo, BorderLayout.NORTH);
		plPlayerInfo.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblPlayerIDLabel = new JLabel("Player Number:");
		plPlayerInfo.add(lblPlayerIDLabel);	
		
		lblPlayerID = new JLabel("");
		plPlayerInfo.add(lblPlayerID);
		
		JLabel lblCurrentPlayerLabel = new JLabel("Active :");
		plPlayerInfo.add(lblCurrentPlayerLabel);
		
		lblCurrentPlayer = new JLabel("");
		plPlayerInfo.add(lblCurrentPlayer);
		
		JPanel plPlayingField = new JPanel();
		frame.getContentPane().add(plPlayingField, BorderLayout.CENTER);
		plPlayingField.setLayout(new BorderLayout(0, 0));
		
		lblAddition = new JLabel("0");
		lblAddition.setHorizontalAlignment(SwingConstants.CENTER);
		plPlayingField.add(lblAddition);
		
		JLabel lblAdditionText = new JLabel("Result:");
		lblAdditionText.setHorizontalAlignment(SwingConstants.CENTER);
		plPlayingField.add(lblAdditionText, BorderLayout.NORTH);
		
		JPanel panel = new JPanel();
		plPlayingField.add(panel, BorderLayout.SOUTH);
		
		JLabel lblAdditionLabel = new JLabel("Addition:");
		panel.add(lblAdditionLabel);
		
		txtfAddition = new JTextField();
		panel.add(txtfAddition);
		txtfAddition.setColumns(10);
		
		JButton btnPlay = new JButton("Play");
		btnPlay.addActionListener(controller);
		panel.add(btnPlay);
	}

	
	/**
	 * the gui will be set visible at the end after everything else is done 
	 */
	public void setVisible(){
		this.frame.setVisible(true);
	}
	
	/**
	 * Getter and Setter methods for fields which will be updated or read by the controller
	 *
	 */
	
	public String getAddition() {
		return txtfAddition.getText();
	}
	public void setAddition(String string){
		txtfAddition.setText(string);
	}
	public void setLblAddition(String string){
		lblAddition.setText(string);
	}
	public void setPlayer(String string){
		lblPlayerID.setText(string);
	}
	public void setCurrentPlayer(String string){
		lblCurrentPlayer.setText(string);
	}

	/**
	 * redraw the gui
	 */
	public void redraw() {
		// TODO Auto-generated method stub
		this.frame.revalidate();
	}
}
