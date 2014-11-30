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

import com.sun.java.swing.plaf.nimbus.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Vector;

public class EndView extends JFrame {

	private JFrame frame;
	
	JPanel panelTitle;			//1
	JPanel panelContent;		//2
	JPanel panelButton;			//3
	
	JPanel panelGameResults;	//2.1
	JPanel panelImgResult; //2.2
	
	JPanel panelBtnStart;		//3.1
	JPanel panelBtnContainer;	//3.2
	
	JButton btnExit;
	JButton btnRules;
	JButton btnStart;
	
	JLabel lblEndGameStatusTitle;
	JLabel lblWaitingForPlayer;
	JLabel lblLogginPlayer;
	JLabel imgResult;
	JLabel lblPoduium;
	
    //private DefaultListModel listPlayingPlayer;
	
	TableController controller;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EndView window = new EndView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	//public StartView(TableController controller) {
	public EndView() {
		//this.controller = controller;
		
		// Define Font's
		Font title = new Font("Comic Sans MS", Font.BOLD, 20);
		Font text = new Font("Comic Sans MS", Font.PLAIN, 14);
		Font button = new Font("Comic Sans MS", Font.PLAIN, 16);
		
		// Define Image Path's
		String pathImgExit = "/icons/exit.png";
		String pathImgWinner = "/icons/winner.png";
		String pathImgLooser = "/icons/looser.png";
		String pathImgPodium = "/icons/podium.png";
		String pathImgTrophy = "/icons/trophy.png";
		String pathImg2ndAward = "/icons/award.png";

		frame = new JFrame("QFresh Haggis Game - Game Results");
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
		
		lblPoduium = new JLabel(new ImageIcon(StartView.class.getResource(pathImgPodium)));
		lblEndGameStatusTitle = new JLabel("<Dinamic> Congratulation Player x! your ");
		lblEndGameStatusTitle.setFont(title);
		panelTitle.add(lblPoduium);
		panelTitle.add(lblEndGameStatusTitle);
		lblEndGameStatusTitle.setHorizontalAlignment(SwingConstants.CENTER);
		
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
		panelGameResults = new JPanel();
		panelGameResults.setOpaque(false);
		panelContent.add(panelGameResults);
		panelGameResults.setLayout(new BorderLayout(0, 0));
		
		// --- Check connected Player
		UIManager.put("Table.font", new FontUIResource(text));
		UIManager.put("Table.rowHeight", 30);
		UIManager.getDefaults().put("Table.background", Color.LIGHT_GRAY);
        UIManager.put("Table.alternateRowColor", Color.PINK);
        //UIManager.put("Table.border", BorderFactory.createLineBorder(Color.BLACK));
		/**
		Vector<String> data = new Vector<String>();
		Vector<String> rank = new Vector<String>();
		rank.add("1");
		rank.add("2");
		rank.add("3");
		
        Vector<String> player = new Vector<String>();
        player.add("Player1");
        player.add("Player2");
		player.add("Player3");
		
		Vector<String> point = new Vector<String>();
		point.add("255");
		point.add("150");
		point.add("50");
		
		data.addAll(rank);
		data.addAll(player);
		data.addAll(point);
		
		Vector<String> titleColumn = new Vector<String>();
		titleColumn.add("Rank");
		titleColumn.add("Player");
		titleColumn.add("Point");

		//DefaultTableModel model = new DefaultTableModel();
		//model.addColumn(lblLogginPlayer, joinedPlayer);
		JTable tblGameResults = new JTable(data, titleColumn);
		panelGameResults.add(tblGameResults, BorderLayout.CENTER);
		**/
		// -- Result Image (2.2)
		panelImgResult = new JPanel();
		panelImgResult.setOpaque(false);
		panelContent.add(panelImgResult);
		panelImgResult.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// -- Show Result Image (Winner or Loosers)
		// if (player == Winner) {
		//	else { imgResult = new JLabel(new ImageIcon(StartView.class.getResource(pathImgLooser)));}
		
		imgResult = new JLabel(new ImageIcon(StartView.class.getResource(pathImgLooser)));
		imgResult.setHorizontalAlignment(SwingConstants.CENTER);
		panelImgResult.add(imgResult);
		

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
		
		
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(StartView.class.getResource(pathImgExit)));
		btnExit.setPreferredSize(new Dimension (58,58));
		panelBtnContainer.add(btnExit);
		
	}

}
