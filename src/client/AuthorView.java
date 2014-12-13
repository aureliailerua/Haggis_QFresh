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
	
	Font subtitle = new Font("Comic Sans MS", Font.BOLD, 18);
	Font text = new Font("Comic Sans MS", Font.PLAIN, 16);

	String pathImgBeer = "/icons/end.png";
	String pathImgGithub = "/icons/github.png";
	String pathImgCrown = "/icons/crown_64.png";
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AuthorView frame = new AuthorView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AuthorView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Haggis Game - Author Information");
		contentPane = new JPanel();
		contentPane.setPreferredSize(new Dimension(450, 200));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panelTitle = new JPanel();
		contentPane.add(panelTitle, BorderLayout.NORTH);

		JLabel lbAuthTitle = new JLabel();
		lbAuthTitle.setText("QFresh Haggis Game Authoren Info");
		lbAuthTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbAuthTitle.setFont(subtitle);
		panelTitle.add(lbAuthTitle);
		
		JPanel panelBeerIcon = new JPanel();
		//ImageIcon imageIcon = new ImageIcon(AuthorView.class.getResource("pathImgBeer"));
		//JLabel lbBeer = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH)));
		//panelBeerIcon.add(lbBeer);		
		contentPane.add(panelBeerIcon, BorderLayout.WEST);
		
		JPanel panelText = new JPanel();
		contentPane.add(panelText, BorderLayout.CENTER);
		panelText.setLayout(new BorderLayout(0, 0));
		

		
		JTextArea txtrHi = new JTextArea();
		txtrHi.setText("Hi! \n Wir sind die Gruppe QFresh. Unser Gruppenname stammt vom schweizer Beer Quell Fresch und wurde in einer kreativen Phase zu diesem JAVA Projekt bestimmt. \n Die Autoren des Haggis Game sind: \n Aurelia \n Benjamin \n Andi \n Felicita \n Wir wünschen euch viel Spass mit unserem Spiel!");
		txtrHi.setBackground(Color.BLACK);
		txtrHi.setFont(text);
		txtrHi.setForeground(Color.WHITE);
		txtrHi.setLineWrap(true);
		//txtrHi.setPreferredSize(new Dimension(400, 100));
		txtrHi.setMargin(new Insets(5,5,5,5));
		
		panelText.add(txtrHi, BorderLayout.NORTH);
		
		JPanel panelIcons = new JPanel();
		contentPane.add(panelIcons, BorderLayout.EAST);
	}

}

/**
// Setup


JPanel panelAuthTitle = new JPanel();
frameInfo.add(panelAuthTitle, BorderLayout.NORTH);


JLabel lbAuthTitle = new JLabel();
lbAuthTitle.setText("QFresh Haggis Game Authoren Info");
lbAuthTitle.setHorizontalAlignment(SwingConstants.CENTER);
lbAuthTitle.setFont(subtitle);
panelTitle.add(lbAuthTitle);

JPanel panelBeerIcon = new JPanel();
ImageIcon imageIcon = new ImageIcon(StartView.class.getResource("pathImgBeer"));
JLabel lbBeer = new JLabel(new ImageIcon(imageIcon.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH)));
panelBeerIcon.add(lbBeer);
frameInfo.add(panelBeerIcon, BorderLayout.WEST);

JPanel panelText = new JPanel();
frameInfo.add(panelText, BorderLayout.CENTER);
panelText.setLayout(new BorderLayout(0, 0));

JTextArea txtrHi = new JTextArea();
txtrHi.setText("Hi");
panelText.add(txtrHi, BorderLayout.NORTH);

JPanel panelIcons = new JPanel();
ImageIcon imageIconGit = new ImageIcon(StartView.class.getResource("pathImgGithub"));
JLabel lbGit = new JLabel(new ImageIcon(imageIconGit.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH)));
ImageIcon imageIconCrown = new ImageIcon(StartView.class.getResource("pathImgCrown"));
JLabel lbCown = new JLabel(new ImageIcon(imageIconCrown.getImage().getScaledInstance(32, 32,  java.awt.Image.SCALE_SMOOTH)));

panelIcons.add(lbCown);
panelIcons.add(lbGit);
frameInfo.add(panelIcons, BorderLayout.EAST);


/*
JPanel panelAuthInfo = new JPanel();

StyleContext context = new StyleContext();
StyledDocument document = new DefaultStyledDocument(context);
Style style = context.getStyle(StyleContext.DEFAULT_STYLE);
// All about the style configuration
StyleConstants.setAlignment(style, StyleConstants.ALIGN_LEFT);
StyleConstants.setFontSize(style, 13);
StyleConstants.setFontFamily(style, "Comic Sans MS");
StyleConstants.setBold(style, true);
StyleConstants.setSpaceAbove(style, 3);
StyleConstants.setSpaceBelow(style, 10);
StyleConstants.setRightIndent(style, 10);
StyleConstants.setLeftIndent(style, 10);
StyleConstants.setForeground(style, Color.BLACK);

//Exception for not working insert
try {
    document.insertString(document.getLength(), message, style);
  } catch (BadLocationException badLocationException) {
    System.err.println("Could not display information message!");
  }

//Generate textPane
JTextPane textPane = new JTextPane(document);
textPane.setPreferredSize(new Dimension(250, 100));
textPane.setBackground(new Color(0, 0, 0));
textPane.setEditable(false);
panelAuthInfo.add(textPane);

frameInfo.add(panelAuthInfo);


frameInfo.pack();
frameInfo.setVisible(true);
*/
