package client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
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

public class StartView {

	private JFrame frame;

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

	/**
	 * Create the application.
	 */
	public StartView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 594, 477);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panelNorth = new JPanel();
		panelNorth.setBorder(new EmptyBorder(20, 0, 0, 10));
		frame.getContentPane().add(panelNorth, BorderLayout.NORTH);
		
		JLabel lblWelcomeToHaggis = new JLabel("Welcome to QFresh Haggis Game!");
		panelNorth.add(lblWelcomeToHaggis);
		lblWelcomeToHaggis.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panelCenter = new JPanel();
		frame.getContentPane().add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panelPlayer = new JPanel();
		panelCenter.add(panelPlayer);
		panelPlayer.setLayout(new BorderLayout(0, 0));
		
		JLabel lblLogginPlayer = new JLabel("Angemeldete Player");
		panelPlayer.add(lblLogginPlayer, BorderLayout.SOUTH);
		
	
		String[] emptyPlayerList = {" "," "," "};
		
		// should be JList<String> but windowbuilder fails with that
		JList<String> list = new JList(emptyPlayerList);		    
		DefaultListSelectionModel m = new DefaultListSelectionModel();
		list.setSelectionModel(m);
		list.setPreferredSize(new Dimension(20, 20));
		//m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//m.setLeadAnchorNotificationEnabled(false);
		
		panelPlayer.add(list, BorderLayout.CENTER);
		
		JLabel lblWaitingForPlayer = new JLabel("Waiting for Player...");
		lblWaitingForPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		panelPlayer.add(lblWaitingForPlayer, BorderLayout.NORTH);
		
		JPanel panelImageContainer = new JPanel();
		panelCenter.add(panelImageContainer);
		
		JPanel panelSouth = new JPanel();
		frame.getContentPane().add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new GridLayout(1, 2, 0, 0));
		
		JButton btnStartGame = new JButton("Start");
		panelSouth.add(btnStartGame);
		
		JPanel panelButtonContainer = new JPanel();
		panelSouth.add(panelButtonContainer);
		
		JButton btnRules = new JButton("Rules");
		btnRules.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		btnRules.setIcon(new ImageIcon(StartView.class.getResource("/icons/btnRules.png")));
		panelButtonContainer.add(btnRules);
		
		JButton btnExit = new JButton("Exit");
		panelButtonContainer.add(btnExit);
	}

}
