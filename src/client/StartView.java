package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Dimension;  

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Vector;

public class StartView extends JFrame {

	private JFrame frame;
	
	JPanel panelTitle;			//1
	JPanel panelContent;		//2
	JPanel panelButton;			//3
	
	JPanel panelStatusPlayer;	//2.1
	JPanel panelImageContainer; //2.2
	
	JPanel panelBtnStart;		//3.1
	JPanel panelBtnContainer;	//3.2
	
	JButton btnExit;
	JButton btnRules;
	JButton btnStart;
	
	JLabel lblWelcomeToHaggis;
	JLabel lblWaitingForPlayer;
	JLabel lblLogginPlayer;
	JLabel imgLableCard;
	
    //private DefaultListModel listPlayingPlayer;
	
	TableController controller;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartView window = new StartView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	//public StartView(TableController controller) {
	public StartView() {
		//this.controller = controller;
		
		// Define Font's
		Font title = new Font("Comic Sans MS", Font.BOLD, 20);
		Font subtitle = new Font("Comic Sans MS", Font.BOLD, 14);
		Font text = new Font("Comic Sans MS", Font.PLAIN, 14);
		Font button = new Font("Comic Sans MS", Font.PLAIN, 16);
		
		// Define Image Path's
		String pathImgCard1 = "/gameContent/joker/joker13.jpg";
		String pathImgCard2 = "/gameContent/red/red9.jpg";
		String pathImgCard3 = "/gameContent/red/red10.jpg";
		String pathImgExit = "/icons/exit.png";
		String pathImgHelp = "/icons/help.png";

		frame = new JFrame("QFresh Haggis Game - Game Registration");
		frame.setBounds(100, 100, 1000, 500); // x, y, breite, höhe
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().setBackground(Color.WHITE);

		/**
		 * Title (1)
		 */
		panelTitle = new JPanel();
		panelTitle.setOpaque(false);
		panelTitle.setBorder(new EmptyBorder(20, 0, 0, 10));
		frame.getContentPane().add(panelTitle, BorderLayout.NORTH);
		
		lblWelcomeToHaggis = new JLabel("Welcome to QFresh Haggis Game!");
		lblWelcomeToHaggis.setFont(title);
		panelTitle.add(lblWelcomeToHaggis);
		lblWelcomeToHaggis.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * Content (2) 
		 * Connected Player and card img
		 */
		panelContent = new JPanel();
		panelContent.setOpaque(false);
		frame.getContentPane().add(panelContent, BorderLayout.CENTER);
		panelContent.setLayout(new GridLayout(1, 2, 0, 0));
		panelContent.setBorder(new EmptyBorder(10, 20, 20, 20) ); 	//top, left, bottom, right
		
		
		// -- Connected Player (2.1)
		panelStatusPlayer = new JPanel();
		panelStatusPlayer.setOpaque(false);
		panelContent.add(panelStatusPlayer);
		panelStatusPlayer.setLayout(new BorderLayout());
		
		lblLogginPlayer = new JLabel("Joined Player:");
		lblLogginPlayer.setFont(subtitle);
		panelStatusPlayer.add(lblLogginPlayer, BorderLayout.NORTH);
		
		// --- Check connected Player
		UIManager.put("Table.font", new FontUIResource(text));
		UIManager.put("Table.rowHeight", 30);
		UIManager.getDefaults().put("Table.background", Color.LIGHT_GRAY);
        UIManager.put("Table.alternateRowColor", Color.PINK);
        //UIManager.put("Table.border", BorderFactory.createLineBorder(Color.BLACK));
		
		Vector<String> joinedPlayer = new Vector<String>();
		joinedPlayer.add("Player1");
		joinedPlayer.add("Player2");
		joinedPlayer.add("Player3");
		joinedPlayer.add("Status:");
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn(lblLogginPlayer, joinedPlayer);
		JTable tblLogginPlayer = new JTable(model);
		panelStatusPlayer.add(tblLogginPlayer, BorderLayout.CENTER);
		
		/**Vector<String> vPlayingPlayer = new Vector<String>();
		vPlayingPlayer.add("Player1");
		vPlayingPlayer.add("Player2");
		vPlayingPlayer.add("Player3");
		vPlayingPlayer.add("Status:");

		Vector<String> vTableTitle = new Vector<String>();
		vTableTitle.add("Joined Player");
		//DefaultTableModel model = new DefaultTableModel(vPlayingPlayer, vTableTitle);
		//model.addColumn(vTableTitle, vPlayingPlayer);
		//model.addColumn("Joined Player", vPlayingPlayer);
		JTable tblLogginPlayer = new JTable(vPlayingPlayer, vTableTitle); //data, title

		//JTable tblLogginPlayer = new JTable(model); //data, title
		panelStatusPlayer.add(tblLogginPlayer, BorderLayout.CENTER);
		
		
		String[] columnName = {"Joined Player"};
		Object[] joinedPlayer = {"Player1", "Player2", "Player3"};
		
		JTable tblJoinedPlayer = new JTable(joinedPlayer, columnName);
		panelStatusPlayer.add(tblJoinedPlayer,BorderLayout.CENTER);
		**/

	
		/**String[] emptyPlayerList = {" "," "," "};
		
		//for player in player token => addElement()
		listPlayingPlayer = new DefaultListModel<String>();
		listPlayingPlayer.addElement("Player1");
		listPlayingPlayer.addElement("Player1");
		listPlayingPlayer.addElement("Player1");
		
		//JList<String> list = new JList(emptyPlayerList);		    
		// should be JList<String> but windowbuilder fails with that
		//JList<String> list = new JList(emptyPlayerList);		    
		
	    JList<String> listLogginPlayer = new JList(listPlayingPlayer);
		listLogginPlayer.setBorder(BorderFactory.createLineBorder(Color.black));
		//DefaultListSelectionModel m = new DefaultListSelectionModel();
		listLogginPlayer.setVisibleRowCount(5);
		//listLogginPlayer.setSelectionModel(m);
		listLogginPlayer.setPreferredSize(new Dimension(20, 20));
		//m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//m.setLeadAnchorNotificationEnabled(false);
		
		
		lblWaitingForPlayer = new JLabel("Status: Waiting for Player...");
		lblWaitingForPlayer.setFont(text);
		lblWaitingForPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		panelStatusPlayer.add(lblWaitingForPlayer, BorderLayout.NORTH);
		**/
		
		// -- Card Image (2.2)
		panelImageContainer = new JPanel();
		panelImageContainer.setOpaque(false);
		panelContent.add(panelImageContainer);
		
		
		GridBagLayout gblCard = new GridBagLayout();
		GridBagConstraints cCard = new GridBagConstraints();	
		panelImageContainer.setLayout(gblCard);
		
		// --CardBack
		imgLableCard = new JLabel(new ImageIcon(StartView.class.getResource(pathImgCard1)));
		imgLableCard.setPreferredSize(new Dimension(48,132));
		imgLableCard.setHorizontalAlignment(SwingConstants.LEFT);
		imgLableCard.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.BLACK));
		cCard.fill = GridBagConstraints.BOTH;
		cCard.gridwidth = 1;
		cCard.gridx = 0;
		cCard.gridy = 0;
		cCard.anchor = GridBagConstraints.LINE_END;
		panelImageContainer.add(imgLableCard, cCard);

		imgLableCard = new JLabel(new ImageIcon(StartView.class.getResource(pathImgCard2)));
		imgLableCard.setHorizontalAlignment(SwingConstants.LEFT);
		imgLableCard.setPreferredSize(new Dimension(48,132));
		imgLableCard.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 1, Color.BLACK));
		cCard = new GridBagConstraints();
		cCard.fill = GridBagConstraints.BOTH;
		cCard.gridx = 1;
		cCard.gridy = 0;
		cCard.anchor = GridBagConstraints.LINE_END;
		panelImageContainer.add(imgLableCard, cCard);
		
		imgLableCard = new JLabel(new ImageIcon(StartView.class.getResource(pathImgCard3)));
		imgLableCard.setPreferredSize(new Dimension(87,132));
		imgLableCard.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, Color.BLACK));
		cCard = new GridBagConstraints();
		cCard.fill = GridBagConstraints.BOTH;
		cCard.gridx = 2;
		cCard.gridy = 0;
		panelImageContainer.add(imgLableCard, cCard);
		

		/**
		 * Button Container (3)
		 */
		panelButton = new JPanel();
		panelButton.setOpaque(false);
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new GridLayout(1, 2, 0, 0));
		panelButton.setBorder(new EmptyBorder(20, 20, 20, 10) ); 
		
		panelBtnStart = new JPanel();
		panelBtnStart.setOpaque(false);
		FlowLayout flBtnStart = (FlowLayout) panelBtnStart.getLayout();
		flBtnStart.setAlignment(FlowLayout.LEFT);
		panelButton.add(panelBtnStart);
		
		btnStart = new JButton("Start Game");
		btnStart.setFont(button);
		btnStart.setPreferredSize(new Dimension(130, 58));
		//btnStart.addActionListener(this);
		panelBtnStart.add(btnStart);
		
		// -- Button Container (3.2)
		panelBtnContainer = new JPanel();
		panelBtnContainer.setOpaque(false);
		FlowLayout flBtnContainer = (FlowLayout) panelBtnContainer.getLayout();
		flBtnContainer.setAlignment(FlowLayout.RIGHT);
		panelButton.add(panelBtnContainer);
		
		btnRules = new JButton();		
		btnRules.setIcon(new ImageIcon(StartView.class.getResource(pathImgHelp)));
		btnRules.setPreferredSize(new Dimension (58,58));
		//btnRules.addActionListener(this);
		panelBtnContainer.add(btnRules);
		
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(StartView.class.getResource(pathImgExit)));
		btnExit.setPreferredSize(new Dimension (58,58));
		panelBtnContainer.add(btnExit);
		
	}

}
