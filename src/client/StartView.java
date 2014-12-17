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
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

import library.StartContainer;


/**
 * 
 * @author felicita.acklin
 * StartView is responsible for starting the game, show all logged in player and game rules
 */
public class StartView extends JFrame implements ActionListener {

	private JFrame frame;
	
	JPanel panelHeader;			//1
	JPanel panelContent;		//2
	JPanel panelButton;			//3
	
	JPanel panelTitle;			//1.1
	JPanel panelInfo;			//1.2
	
	JPanel panelStatusPlayer;	//2.1
	JPanel panelImageContainer; //2.2
	
	JPanel panelBtnStart;		//3.1
	JPanel panelBtnContainer;	//3.2
	
	JButton btnExit;
	JButton btnRules;
	JButton btnStart;
	JButton btnInfo;
	
	JLabel lblWelcomeToHaggis;
	JLabel lblWaitingForPlayer;
	JLabel lblLogginPlayer;
	JLabel imgLableCard;
	
	Vector<String> joinedPlayer;
	JTable tblLogginPlayer;
	DefaultTableModel model; 
	
	
	private static final Logger log = LogManager.getLogger( TableController.class.getName() );
		
	StartController controller;

	public StartView(StartController controller) {
		this.controller = controller;
		
		// Define Font's
		Font title = new Font("Comic Sans MS", Font.BOLD, 24);
		Font subtitle = new Font("Comic Sans MS", Font.BOLD, 18);
		Font text = new Font("Comic Sans MS", Font.PLAIN, 16);
		Font button = new Font("Comic Sans MS", Font.PLAIN, 16);
		
		// Define Image Path's
		String pathImgCard1 = "/gameContent/joker/joker13.jpg";
		String pathImgCard2 = "/gameContent/red/red9.jpg";
		String pathImgCard3 = "/gameContent/red/red10.jpg";
		String pathImgExit = "/icons/exit.png";
		String pathImgHelp = "/icons/help.png";
		String pathImgInfo = "/icons/info.png";

		frame = new JFrame("QFresh Haggis Game - Game Registration");
		frame.setBounds(50, 50, 1000, 500); // x, y, breite, höhe
		frame.setPreferredSize(new Dimension(1000, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.getContentPane().setBackground(Color.WHITE);

		/**
		 * Title (1)
		 */
		panelHeader = new JPanel();
		panelHeader.setOpaque(false);
		frame.getContentPane().add(panelHeader, BorderLayout.NORTH);
		panelHeader.setLayout(new BorderLayout(0, 0));

		panelTitle = new JPanel();
		panelTitle.setOpaque(false);
		panelTitle.setBorder(new EmptyBorder(20, 0, 0, 10));
		panelTitle.setPreferredSize(new Dimension(950, 80));
		panelHeader.add(panelTitle, BorderLayout.CENTER);
		
		lblWelcomeToHaggis = new JLabel("Welcome to QFresh Haggis Game!");
		lblWelcomeToHaggis.setFont(title);
		panelTitle.add(lblWelcomeToHaggis);
		lblWelcomeToHaggis.setHorizontalAlignment(SwingConstants.CENTER);
		
		panelInfo = new JPanel();
		panelInfo.setOpaque(false);
		panelInfo.setBorder(new EmptyBorder(20, 0, 0, 10));
		panelHeader.add(panelInfo, BorderLayout.EAST);
		
		btnInfo = new JButton();		
		ImageIcon imageIconInfo = new ImageIcon(StartView.class.getResource(pathImgInfo));
		btnInfo.setIcon(new ImageIcon(imageIconInfo.getImage().getScaledInstance(25, 25,  java.awt.Image.SCALE_SMOOTH)));
		btnInfo.setPreferredSize(new Dimension (25,25));
		btnInfo.setBackground(null);
		btnInfo.setBorder(null);
		btnInfo.addActionListener(this);
		panelInfo.add(btnInfo);
		
		
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
		UIManager.getDefaults().put("Table.background", Color.LIGHT_GRAY);
        //UIManager.put("Table.border", BorderFactory.createLineBorder(Color.BLACK));
		
		joinedPlayer = new Vector<String>();
		joinedPlayer.add("Waiting for Players...");
		model = new DefaultTableModel();
		model.addColumn(lblLogginPlayer, joinedPlayer);
		tblLogginPlayer = new JTable(model);
		tblLogginPlayer.setRowHeight(30);
		tblLogginPlayer.setBorder(null);
		tblLogginPlayer.setFocusable(false);
		tblLogginPlayer.setCellSelectionEnabled(false);
		tblLogginPlayer.setRowSelectionAllowed(false);
		panelStatusPlayer.add(tblLogginPlayer, BorderLayout.CENTER);
		
		
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
		 * Button CardContainer (3)
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
		btnStart.setEnabled(false);
		btnStart.addActionListener(controller);
		panelBtnStart.add(btnStart);
		
		// -- Button CardContainer (3.2)
		panelBtnContainer = new JPanel();
		panelBtnContainer.setOpaque(false);
		FlowLayout flBtnContainer = (FlowLayout) panelBtnContainer.getLayout();
		flBtnContainer.setAlignment(FlowLayout.RIGHT);
		panelButton.add(panelBtnContainer);
		
		btnRules = new JButton();		
		btnRules.setIcon(new ImageIcon(StartView.class.getResource(pathImgHelp)));
		btnRules.setPreferredSize(new Dimension (58,58));
		btnRules.setBackground(Color.WHITE);
		btnRules.addActionListener(this);
		panelBtnContainer.add(btnRules);
		
		btnExit = new JButton();
		//btnExit.setIcon(new ImageIcon(StartView.class.getResource(pathImgExit)));
		ImageIcon imageIconExit = new ImageIcon(StartView.class.getResource(pathImgExit));
		btnExit.setIcon(new ImageIcon(imageIconExit.getImage().getScaledInstance(35, 35,  java.awt.Image.SCALE_SMOOTH)));
		btnExit.setPreferredSize(new Dimension (58,58));
		btnExit.setBackground(Color.WHITE);
		btnExit.addActionListener(controller);
		panelBtnContainer.add(btnExit);
		
	}
	/**
	 * Method to get the frame
	 * @return
	 */
	public JFrame getJFrame(){
		return frame;
	}
		
	/**
	 * Method to display Rules with scrollbar
	 */
	public void displayRules() {
		JFrame frameRules = new JFrame ("Haggis Rules");
		frameRules.setBounds(100, 100, 800, 750); 						// x-Position, y-Position, breite und höhe des Fenster
	    frameRules.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);
	    JPanel panelRules = new JPanel();
	    panelRules.setLayout(new BoxLayout(panelRules, BoxLayout.Y_AXIS));
	    JScrollPane scrPane = new JScrollPane(panelRules); 				//Add ScrollPanel
        frameRules.add(scrPane);
	    
        JLabel[] jlRule = new JLabel[6];
		for (int i=0; i < jlRule.length; i++) {
			int j=i+1;
			ImageIcon imageIcon = new ImageIcon(StartView.class.getResource("/gameContent/rules/haggis_rules_p"+ j +".png"));
			jlRule[i] = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(537, 829,  java.awt.Image.SCALE_SMOOTH)));
			panelRules.add(jlRule[i]);
		}
	
	    frameRules.pack();
	    frameRules.setVisible(true);
	}
	
	public void displayGameInformation() {
		AuthorView frameAuthInfo = new AuthorView();
		frameAuthInfo.pack();
		frameAuthInfo.setVisible(true);
	}
	
	
	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btnRules) {
	    	displayRules();
	    }	   
		if (e.getSource() == btnInfo) {
	    	displayGameInformation();
	    }
	    
	}

}
