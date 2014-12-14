package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JTextArea;

public class AuthorView extends JFrame {

	private JPanel contentPane;
	
	private JPanel panelTitle;
	private JPanel panelBeerIcon;
	private JPanel panelText;
	private JPanel panelCrwon;
	
	private JLabel lbAuthTitle;
	private JLabel lblBeer;
	private JLabel lblCrwon;
	
	private JTextArea authText;
	
	// Define font
	Font subtitle = new Font("Comic Sans MS", Font.BOLD, 18);
	Font text = new Font("Comic Sans MS", Font.PLAIN, 16);

	// Define image path
	String pathImgBeer = "/icons/beer.png";
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
		
		panelTitle = new JPanel();
		contentPane.add(panelTitle, BorderLayout.NORTH);
		panelTitle.setOpaque(false);

		lbAuthTitle = new JLabel();
		lbAuthTitle.setText("About us");
		lbAuthTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbAuthTitle.setFont(subtitle);
		panelTitle.add(lbAuthTitle);
		
		panelBeerIcon = new JPanel();
		panelBeerIcon.setOpaque(false);
		ImageIcon beer = new ImageIcon(AuthorView.class.getResource(pathImgBeer));
		lblBeer = new JLabel(new ImageIcon(beer.getImage().getScaledInstance(44, 44,  java.awt.Image.SCALE_SMOOTH)));
		panelBeerIcon.add(lblBeer);		
		contentPane.add(panelBeerIcon, BorderLayout.WEST);
		
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
		
		panelCrwon = new JPanel();
		panelCrwon.setOpaque(false);
		contentPane.add(panelCrwon, BorderLayout.EAST);
		ImageIcon crown = new ImageIcon(AuthorView.class.getResource(pathImgCrown));
		lblCrwon = new JLabel(new ImageIcon(crown.getImage().getScaledInstance(48, 48,  java.awt.Image.SCALE_SMOOTH)));
		panelCrwon.add(lblCrwon);
	}

}

