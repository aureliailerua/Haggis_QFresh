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
import javax.swing.plaf.ColorUIResource;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import com.sun.java.swing.plaf.nimbus.*;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import library.Player;
import library.GameState.PlayerToken;

public class EndView extends JDialog {

	//private JFrame frame;
	
	JPanel panelTitle;			//1
	JPanel panelContent;		//2
	JPanel panelButton;			//3
	
	JPanel panelGameResults;	//2.1
	JPanel panelImgResult; //2.2
	
	JPanel panelBtnStart;		//3.1
	JPanel panelBtnContainer;	//3.2
	
	JButton btnExit;
	JButton btnStart;
	JButton btnButton;
	
	JLabel lblEndGameStatusTitle;
	JLabel lblWaitingForPlayer;
	JLabel lblLogginPlayer;
	JLabel imgResult;
	JLabel lblPoduium;
	
	protected ImageIcon iconWinner;
	protected ImageIcon iconLoser;
	protected ImageIcon iconExit;

	

	Vector<Vector> rowData;
	Vector<String> columnNames;
	JTable tblRanking;
	DefaultTableModel model; 
	
    //private DefaultListModel listPlayingPlayer;
	
	EndController controller;

	public EndView(EndController controller,JFrame frame) {
		super(frame, "text", ModalityType.APPLICATION_MODAL);
		this.controller = controller;
		controller.setEndView(this);
	
		
		// Define Font's
		Font title = new Font("Comic Sans MS", Font.BOLD, 24);
		Font text = new Font("Comic Sans MS", Font.PLAIN, 18);
		Font button = new Font("Comic Sans MS", Font.PLAIN, 16);
		
		// Define Image Path's
		String pathImgExit = "/icons/end.png";
		String pathImgWinner = "/icons/winner.png";
		String pathImgLoser = "/icons/loser.png";
		String pathImgPodium = "/icons/podium.png";
		
		iconWinner = new ImageIcon(EndView.class.getResource(pathImgWinner));
		iconLoser = new ImageIcon(EndView.class.getResource(pathImgLoser));
		iconExit = new ImageIcon(EndView.class.getResource(pathImgExit));


		//frame = new JFrame("QFresh Haggis Game - Game Results");
		//dialog = new JDialog();
		
		//dialog = new JDialog( , "QFresh Haggis Game - Game Results");
		setBounds(0, 0, 1000, 500); // x, y, breite, höhe
		setPreferredSize(new Dimension(1000, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setBackground(Color.WHITE);
		setAlwaysOnTop (true);

		/**
		 * Title (1)
		 */
		panelTitle = new JPanel();
		panelTitle.setOpaque(false);
		panelTitle.setBorder(new EmptyBorder(20, 0, 0, 10));
		getContentPane().add(panelTitle, BorderLayout.NORTH);
		lblPoduium = new JLabel(new ImageIcon(EndView.class.getResource(pathImgPodium)));
		lblEndGameStatusTitle = new JLabel();
		panelTitle.add(lblPoduium);
		panelTitle.add(lblEndGameStatusTitle);


		/**
		 * Content (2) 
		 */
		panelContent = new JPanel();
		panelContent.setOpaque(false);
		getContentPane().add(panelContent, BorderLayout.CENTER);
		panelContent.setLayout(new GridLayout(1, 2, 0, 0));
		panelContent.setBorder(new EmptyBorder(10, 20, 20, 20) ); 	//top, left, bottom, right
		
		
		// -- Game Result (2.1)
		panelGameResults = new JPanel();
		panelGameResults.setOpaque(false);
		panelContent.add(panelGameResults);
		panelGameResults.setLayout(new BorderLayout(0, 0));
		
		// --- Game result table
		UIManager.put("Table.font", new FontUIResource(text));
		UIManager.put("TableHeader.font", new FontUIResource(text));
		UIManager.put(" Table.gridColor", Color.WHITE);
		UIManager.put(" TableHeader.cellBorder",Color.WHITE );
		UIManager.put("TableHeader.background", Color.BLACK);
		UIManager.put("TableHeader.foreground", Color.WHITE);
		UIManager.getDefaults().put("Table.background", Color.LIGHT_GRAY);
		//UIManager.getDefaults().put("Table.border", BorderFactory.createLineBorder(Color.BLACK));
				
		
		rowData = new Vector<Vector>();

		
		columnNames = new Vector<String>();
		columnNames.add("Rank");
		columnNames.add("Name");
		columnNames.add("Points");
	
		model = new DefaultTableModel(rowData, columnNames);
		tblRanking = new JTable(model);
		tblRanking.setRowHeight(50);
		tblRanking.setBorder(null);
		tblRanking.setFocusable(false);
		tblRanking.setCellSelectionEnabled(false);
		tblRanking.setRowSelectionAllowed(false);


		JScrollPane scrollePane = new JScrollPane(tblRanking);
		scrollePane.setBorder(null);
		panelGameResults.add(scrollePane);

		
		// -- Result Image (2.2)
		panelImgResult = new JPanel();
		panelImgResult.setOpaque(false);
		panelContent.add(panelImgResult);
		panelImgResult.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		/**
		 * Button CardContainer (3)
		 */
		panelButton = new JPanel();
		panelButton.setOpaque(false);
		getContentPane().add(panelButton, BorderLayout.SOUTH);
		FlowLayout fl_panelButton = (FlowLayout) panelButton.getLayout();
		fl_panelButton.setAlignment(FlowLayout.LEFT);
		//panelButton.setLayout(new GridLayout(1, 2, 0, 0));
		panelButton.setBorder(new EmptyBorder(20, 20, 20, 10) ); 
		
		btnButton = new JButton();
		btnButton.setFont(button);
		btnButton.setPreferredSize(new Dimension(130, 58));
		btnButton.setBackground(Color.WHITE);
		btnButton.addActionListener(controller);
		panelButton.add(btnButton);
		
		/*
		panelBtnStart = new JPanel();
		panelBtnStart.setOpaque(false);
		FlowLayout flBtnStart = (FlowLayout) panelBtnStart.getLayout();
		flBtnStart.setAlignment(FlowLayout.LEFT);
		panelButton.add(panelBtnStart);
		
		btnStart = new JButton("Start Game");
		btnStart.setFont(button);
		btnStart.setPreferredSize(new Dimension(130, 58));
		btnStart.setBackground(Color.WHITE);
		btnStart.addActionListener(controller);
		panelBtnStart.add(btnStart);
		
		// -- Button CardContainer (3.2)
		panelBtnContainer = new JPanel();
		panelBtnContainer.setOpaque(false);
		FlowLayout flBtnContainer = (FlowLayout) panelBtnContainer.getLayout();
		flBtnContainer.setAlignment(FlowLayout.RIGHT);
		panelButton.add(panelBtnContainer);
		
		
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(StartView.class.getResource(pathImgExit)));
		btnExit.setPreferredSize(new Dimension (58,58));
		btnExit.setBackground(Color.WHITE);
		btnExit.addActionListener(controller);
		panelBtnContainer.add(btnExit);
		*/
		// ---- Setup screen for winner or loser
		lblEndGameStatusTitle = new JLabel("");
		imgResult = new JLabel("");
		
		lblEndGameStatusTitle.setFont(title);
		lblEndGameStatusTitle.setHorizontalAlignment(SwingConstants.CENTER);
		panelTitle.add(lblEndGameStatusTitle);
		
		imgResult.setHorizontalAlignment(SwingConstants.CENTER);
		panelImgResult.add(imgResult);
		
		
	}
	
	public void setController(EndController controller){
		this.controller = controller;	
	}

	
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1; //?
		return name+playerNum;
	}


}
