package client;

import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;


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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import java.awt.FlowLayout;
import java.util.Arrays;
import java.util.Vector;

import library.Player;
import library.GameState.PlayerToken;

/**
 * @author felicita.acklin
 * Klasse stellt die Zwischenresultate und Endresultate nach einer Rounde
 * und nach eine Spiel dar.
 *
 */
public class EndView extends JDialog {

								//Layer
	JPanel panelTitle;			//1
	JPanel panelContent;		//2
	JPanel panelButton;			//3
	
	JPanel panelGameResults;	//2.1
	JPanel panelImgResult; 		//2.2
	
	JPanel panelBtnContainer;	//3.1
	
	// Components of panelBtncontainer
	JButton btnExit;
	JButton btnButton;
	protected ImageIcon iconBeer;
	protected ImageIcon iconRepeat;
	
	// Components of panelTitle
	JLabel lbEndGameStatusTitle;
	
	// Components of panelImgResult
	JLabel imgResult;
	protected ImageIcon iconWinner;
	protected ImageIcon iconLoser;

	// Components of panelGameResults
	Vector<Vector> rowData;
	Vector<String> columnNames;
	JTable tblRanking;
	DefaultTableModel model; 
		
	// Controller
	EndController controller;

	public EndView(EndController controller,JFrame frame) {
		super(frame, true);							//JDialog
		this.controller = controller;
		controller.setEndView(this);
	
		
		// Define Font's
		Font title = new Font("Comic Sans MS", Font.BOLD, 24);
		Font text = new Font("Comic Sans MS", Font.PLAIN, 18);
		Font button = new Font("Comic Sans MS", Font.PLAIN, 16);
		
		// Define Image Path's
		String pathImgBeer = "/icons/beer.png";
		String pathImgWinner = "/icons/winner.png";
		String pathImgLoser = "/icons/loser.png";
		String pathImgRepeat = "/icons/repeat.png";
		
		// Define ImageIcon's
		iconWinner = new ImageIcon(EndView.class.getResource(pathImgWinner));
		iconLoser = new ImageIcon(EndView.class.getResource(pathImgLoser));
		iconBeer = new ImageIcon(EndView.class.getResource(pathImgBeer));
		iconRepeat = new ImageIcon(EndView.class.getResource(pathImgRepeat));

		// Setup Window (JDialog)
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().setBackground(Color.WHITE);
		setAlwaysOnTop (true);

		// * Title (1) *
		panelTitle = new JPanel();
		panelTitle.setOpaque(false);
		panelTitle.setBorder(new EmptyBorder(20, 0, 0, 10));
		getContentPane().add(panelTitle, BorderLayout.NORTH);
		lbEndGameStatusTitle = new JLabel();
		lbEndGameStatusTitle.setFont(title);
		panelTitle.add(lbEndGameStatusTitle);


		
		// * Content (2) *
		panelContent = new JPanel();
		panelContent.setOpaque(false);
		getContentPane().add(panelContent, BorderLayout.CENTER);
		panelContent.setLayout(new GridLayout(1, 2, 0, 0));
		panelContent.setBorder(new EmptyBorder(10, 20, 20, 20) ); 	//top, left, bottom, right
		
		
		// -- Game Result (2.1) --
		// Show the result table
		panelGameResults = new JPanel();
		panelGameResults.setOpaque(false);
		panelContent.add(panelGameResults);
		panelGameResults.setLayout(new BorderLayout(0, 0));
	
		
		// --- Game result table ---
		UIManager.put("Table.font", new FontUIResource(text));
		UIManager.put("TableHeader.font", new FontUIResource(text));
		UIManager.put(" Table.gridColor", Color.WHITE);
		UIManager.put(" TableHeader.cellBorder",Color.WHITE );
		UIManager.put("TableHeader.background", Color.BLACK);
		UIManager.put("TableHeader.foreground", Color.WHITE);
		UIManager.getDefaults().put("Table.background", Color.LIGHT_GRAY);
				
		// Create Vector for table
		rowData = new Vector<Vector>();
		columnNames = new Vector<String>();
		columnNames.add("Rank");
		columnNames.add("Name");
		columnNames.add("Points");
	
		// Create table
		model = new DefaultTableModel(rowData, columnNames);
		tblRanking = new JTable(model);
		tblRanking.setRowHeight(50);
		tblRanking.setBorder(null);
		tblRanking.setFocusable(false);
		tblRanking.setCellSelectionEnabled(false);
		tblRanking.setRowSelectionAllowed(false);

		// Scroll Pane for table
		JScrollPane scrollePane = new JScrollPane(tblRanking);
		scrollePane.setBorder(null);
		panelGameResults.add(scrollePane);

		
		// -- Result Image (2.2) --
		// Show the result image (one for loser and one for the winner)
		panelImgResult = new JPanel();
		panelImgResult.setOpaque(false);
		panelImgResult.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		imgResult = new JLabel();
		imgResult.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		// * Button Container (3) *
		// Contains dynamically button (continue or exit)
		panelButton = new JPanel();
		panelButton.setOpaque(false);
		getContentPane().add(panelButton, BorderLayout.SOUTH);
		FlowLayout fl_panelButton = (FlowLayout) panelButton.getLayout();
		fl_panelButton.setAlignment(FlowLayout.LEFT);
		panelButton.setBorder(new EmptyBorder(20, 20, 20, 10) ); 
		
		btnButton = new JButton();
		btnButton.setFont(button);
		btnButton.setBackground(Color.WHITE);
		btnButton.addActionListener(controller);
		panelButton.add(btnButton);
	}
	
	/**
	 * Set controller
	 * @param controller
	 */
	public void setController(EndController controller){
		this.controller = controller;	
	}

	/**
	 * Get the player name
	 * @param player
	 * @return
	 */
	public String getPlayerName(Player player){
		String name = "Player ";
		int playerNum = Arrays.asList(PlayerToken.values()).indexOf(player.getToken())+1;
		return name+playerNum;
	}

}
