package client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Dimension;  

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;

public class StartView extends JFrame implements ActionListener{

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
	JLabel imgCards;

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


	public StartView() {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500); // x, y, breite, höhe
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		/**
		 * Title (1)
		 */
		panelTitle = new JPanel();
		panelTitle.setBorder(new EmptyBorder(20, 0, 0, 10));
		frame.getContentPane().add(panelTitle, BorderLayout.NORTH);
		
		lblWelcomeToHaggis = new JLabel("Welcome to QFresh Haggis Game!");
		panelTitle.add(lblWelcomeToHaggis);
		lblWelcomeToHaggis.setHorizontalAlignment(SwingConstants.CENTER);
		
		/**
		 * Content (2) 
		 * Connected Player and card img
		 */
		panelContent = new JPanel();
		frame.getContentPane().add(panelContent, BorderLayout.CENTER);
		panelContent.setLayout(new GridLayout(1, 2, 0, 0));
		
		// -- Connected Player (2.1)
		panelStatusPlayer = new JPanel();
		panelContent.add(panelStatusPlayer);
		panelStatusPlayer.setLayout(new BorderLayout(0, 0));
		
		lblLogginPlayer = new JLabel("Angemeldete Player");
		panelStatusPlayer.add(lblLogginPlayer, BorderLayout.SOUTH);
		
	
		String[] emptyPlayerList = {" "," "," "};
		
		// should be JList<String> but windowbuilder fails with that
		JList<String> list = new JList(emptyPlayerList);		    
		DefaultListSelectionModel m = new DefaultListSelectionModel();
		list.setSelectionModel(m);
		list.setPreferredSize(new Dimension(20, 20));
		//m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//m.setLeadAnchorNotificationEnabled(false);
		
		panelStatusPlayer.add(list, BorderLayout.CENTER);
		
		lblWaitingForPlayer = new JLabel("Waiting for Player...");
		lblWaitingForPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		panelStatusPlayer.add(lblWaitingForPlayer, BorderLayout.NORTH);
		
		// -- Card Image (2.2)
		panelImageContainer = new JPanel();
		panelContent.add(panelImageContainer);
		
		imgCards = new JLabel(new ImageIcon(StartView.class.getResource("/gameContent/Dame.jpg")));
		imgCards.setPreferredSize(new Dimension(127,80));
		panelImageContainer.add(imgCards);
		
		/**
		 * Button Container (3)
		 */
		panelButton = new JPanel();
		frame.getContentPane().add(panelButton, BorderLayout.SOUTH);
		panelButton.setLayout(new GridLayout(1, 0, 0, 0));
		
		// -- Start Button (3.1)
		panelBtnStart = new JPanel();
		FlowLayout flBtnStart = (FlowLayout) panelBtnStart.getLayout();
		flBtnStart.setAlignment(FlowLayout.LEFT);
		panelButton.add(panelBtnStart);
		
		btnStart = new JButton("Start Game");
		btnStart.setPreferredSize(new Dimension(130, 48));
		btnStart.addActionListener(this);
		panelBtnStart.add(btnStart);
		
		// -- Button Container (3.2)
		panelBtnContainer = new JPanel();
		FlowLayout flBtnContainer = (FlowLayout) panelBtnContainer.getLayout();
		flBtnContainer.setAlignment(FlowLayout.RIGHT);
		panelButton.add(panelBtnContainer);
		
		btnRules = new JButton();		
		btnRules.setIcon(new ImageIcon(StartView.class.getResource("/icons/rules.png")));
		btnRules.setPreferredSize(new Dimension (64,64));
		btnRules.addActionListener(this);
		panelBtnContainer.add(btnRules);
		
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(StartView.class.getResource("/icons/exit.png")));
		btnExit.setPreferredSize(new Dimension (64,64));
		panelBtnContainer.add(btnExit);
	}

}
