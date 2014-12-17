package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JLabel;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;

/**
 * Klasse stellt das "about us" dar.
 * @author felicita.acklin
 *
 */
public class AuthorView extends JFrame {

	private JPanel contentPane;
									//Layer
	private JPanel panelTitle;		//1
	private JPanel panelBeerIcon;	//2
	private JPanel panelText;		//3
	private JPanel panelCrwon;		//4
	
	private JLabel lbAuthTitle;
	private JLabel lblBeer;
	private JLabel lblCrwon;
	
	private JTextArea authText;
	
	// Define font
	Font subtitle = new Font("Comic Sans MS", Font.BOLD, 18);
	Font text = new Font("Comic Sans MS", Font.PLAIN, 16);

	// Define image path
	String pathImgBeer = "/icons/beer_big.png";
	String pathImgCrown = "/icons/crown.png";

	public AuthorView() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 550);
		setTitle("Haggis Game - About us");
		
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(600, 550));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// * Titel(1) *
		panelTitle = new JPanel();
		contentPane.add(panelTitle, BorderLayout.NORTH);
		panelTitle.setOpaque(false);

		lbAuthTitle = new JLabel();
		lbAuthTitle.setText("About us");
		lbAuthTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbAuthTitle.setFont(subtitle);
		panelTitle.add(lbAuthTitle);
		
		// * BeerIcon (2) *
		panelBeerIcon = new JPanel();
		panelBeerIcon.setOpaque(false);
		ImageIcon beer = new ImageIcon(AuthorView.class.getResource(pathImgBeer));
		lblBeer = new JLabel(new ImageIcon(beer.getImage().getScaledInstance(44, 44,  java.awt.Image.SCALE_SMOOTH)));
		panelBeerIcon.add(lblBeer);		
		contentPane.add(panelBeerIcon, BorderLayout.WEST);
		
		// * About us Text (3) *
		panelText = new JPanel();
		panelText.setOpaque(false);
		contentPane.add(panelText, BorderLayout.CENTER);
		panelText.setLayout(new BorderLayout(0, 0));
		
		authText = new JTextArea();
		authText.setText(
				"Hi\nWir sind die Gruppe QFresh und haben im Rahmen des FHNW Informatikprojekt " +
				"entwickelte unsere Gruppe das Kartenspiel Haggis in einer Java Version.\n" +
				"Das Spiel wurde von Sean Ross und dem Verlag Indie Boards & Cards kreiert.\n" +
				"Wir haben das Spiel, das nichts mit dem schottischen Fleischgericht zu tun hat, " +
				"möglichst regelgetreu umgesetzt.\nAn der Entwicklung sind folgende Teammitglieder " + 
				"beteiligt gewesen:\n" +
				"                       Aurelia Erhardt\n" +
				"                       Andreas Denger\n" +
				"                       Benjamin Indermühle\n" +
				"                       Felicita Acklin\n\n" +
				"Wir wünschen viel Spass mit unserer Haggis Version!\n\n" +
				"Eures QFresh Team\n\n"+
				"(c) QFresh 2014");
		authText.setBackground(Color.WHITE);
		authText.setFont(text);
		authText.setForeground(Color.BLACK);
		authText.setLineWrap(true);
		authText.setWrapStyleWord(true);
		authText.setEditable(false);
		authText.setMargin(new Insets(5,5,5,5));
		panelText.add(authText, BorderLayout.NORTH);
		
		// * Crwon Icon (4) *
		panelCrwon = new JPanel();
		panelCrwon.setOpaque(false);
		contentPane.add(panelCrwon, BorderLayout.EAST);
		ImageIcon crown = new ImageIcon(AuthorView.class.getResource(pathImgCrown));
		lblCrwon = new JLabel(new ImageIcon(crown.getImage().getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH)));
		panelCrwon.add(lblCrwon);
	}

}

