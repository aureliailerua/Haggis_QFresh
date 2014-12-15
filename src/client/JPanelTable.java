package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import library.GameState;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JPanelTable extends JPanel{

	JPanel panelPatternInfo;				//2.1
	JPanel panelTableCards;					//2.2

	ArrayList<BtnCard> btnCardTable;
	
	JLabel lblpatternInfo;
	
	GridBagConstraints cTable;	

	Color patterninfo = new Color(255,217,102);
	Font infotext = new Font("Comic Sans MS", Font.BOLD, 14);
	
	TableController controller;
	TableView view;

	private static final Logger log = LogManager.getLogger( TableView.class.getName() );
	
	JPanelTable(TableView view) {
		this.view = view;
		
		// Setup panel layout
		setLayout(new BorderLayout(0, 0));

		
		// -- Pattern Info (2.1.N)
		panelPatternInfo = new JPanel();
		panelPatternInfo = new JPanel();		
		panelPatternInfo.setOpaque(false);
		add(panelPatternInfo, BorderLayout.NORTH);
		
		lblpatternInfo = new JLabel();
		panelPatternInfo.add(lblpatternInfo);

		// -- Card Desk (2.2.C)
		panelTableCards = new JPanel();
		panelTableCards.setOpaque(false);
		add(panelTableCards, BorderLayout.CENTER);

		GridBagLayout gbl_panelTableCards = new GridBagLayout();
		cTable = new GridBagConstraints();
		panelTableCards.setLayout(gbl_panelTableCards); 
		btnCardTable = new ArrayList<BtnCard>();
	}
	
	public void updateTable(GameState gameState){
		
		panelTableCards.removeAll();
		panelTableCards.revalidate();
		
		if (gameState.roundList.size() > 0){
			int gridy = 0;
			int gridx = 0;
			for (int i = 0; i < gameState.getTopCards().size(); i++) {
				BtnCard btnCard = new BtnCard(gameState.getTopCards().get(i));

				if (i >=8) { gridy = 1; gridx= 8; }
				btnCardTable.add(btnCard);
				cTable.gridx = i - gridx;
				cTable.gridy = gridy;
				cTable.ipady = 10;
				cTable.insets = new Insets(0,0,0,0);
				panelTableCards.add(btnCard,cTable);
			}
		}
		panelTableCards.repaint();
		view.getJFrame().getContentPane().revalidate();
		view.getJFrame().getContentPane().repaint();
	}
	
	public void displayPatternInfo(String pattern) {
		if (!pattern.isEmpty()) {
			lblpatternInfo.setText(pattern);
			lblpatternInfo.setFont(infotext);
			lblpatternInfo.setForeground(Color.BLACK);
			lblpatternInfo.setOpaque(true);
			lblpatternInfo.setBackground(new Color(255,229,153));
			lblpatternInfo.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
			panelPatternInfo.add(lblpatternInfo);
		} else {
			lblpatternInfo.setText("");
			lblpatternInfo.setOpaque(false);
		}
	}
	
}
