package nine_mens_morris_game.view.newGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewPVP extends JPanel{
	/**
	 * NewPVP screen for
	 * nine-mens-morris
	 */
	private static final long serialVersionUID = -4800315689039990773L;
	private NewGameScreen ngs;
	private JTextField p1Name;
	private JTextField p2Name;
	private JTextField p1Color;
	private JTextField p2Color;
	private JButton startBut;
	
	/***
	 * Default constructor for
	 * a new PVP game
	 * @param ngs
	 */
	public NewPVP(NewGameScreen ngs){
        JPanel top = new JPanel();
        JLabel message = new JLabel();
        JPanel middle = new JPanel();
        JPanel p1BackPanel = new JPanel();
        JLabel p1NameLabel = new JLabel();
        p1Name = new JTextField();
        JLabel p1ColorLabel = new JLabel();
        p1Color = new JTextField();
        JPanel p2BackPanel = new JPanel();
        JLabel p2NameLabel = new JLabel();
        p2Name = new JTextField();
        JLabel p2ColorLabel = new JLabel();
        p2Color = new JTextField();
        JPanel bot = new JPanel();
        startBut = new JButton();

        setLayout(new java.awt.GridLayout(3, 1, 500, 150));

        top.setLayout(new java.awt.BorderLayout());

        message.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        message.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        message.setText("<html>Enter the information below<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;to start a new game</html>");
        top.add(message, java.awt.BorderLayout.CENTER);

        add(top);

        middle.setLayout(new java.awt.GridLayout());

        p1BackPanel.setLayout(new java.awt.GridLayout(4, 0));

        p1NameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        p1NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1NameLabel.setText("HumanPlayer 1 Name");
        p1BackPanel.add(p1NameLabel);

        p1Name.setHorizontalAlignment(JTextField.CENTER);
        p1BackPanel.add(p1Name);

        p1ColorLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        p1ColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p1ColorLabel.setText("HumanPlayer 1 Color");
        p1BackPanel.add(p1ColorLabel);

        p1Color.setHorizontalAlignment(JTextField.CENTER);
        p1BackPanel.add(p1Color);

        middle.add(p1BackPanel);

        p2BackPanel.setLayout(new java.awt.GridLayout(4, 0));

        p2NameLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        p2NameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p2NameLabel.setText("HumanPlayer 2 Name");
        p2BackPanel.add(p2NameLabel);

        p2Name.setHorizontalAlignment(JTextField.CENTER);
        p2BackPanel.add(p2Name);

        p2ColorLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        p2ColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        p2ColorLabel.setText("HumanPlayer 2 Color");
        p2BackPanel.add(p2ColorLabel);

        p2Color.setHorizontalAlignment(JTextField.CENTER);
        p2BackPanel.add(p2Color);

        middle.add(p2BackPanel);

        add(middle);

        bot.setLayout(new java.awt.BorderLayout());

        startBut.setFont(new java.awt.Font("Traditional Arabic", 0, 36)); // NOI18N
        startBut.setText("Start Game");
        bot.add(startBut, java.awt.BorderLayout.CENTER);

        add(bot);
		startBut.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		start(evt);
        	}
        });
		
		this.ngs = ngs;
		this.setSize(700,700);
	}
	
	/**
	 * Determines if user_category input is valid
	 * if so starts game
	 * @param evt
	 */
	private void start(ActionEvent evt){
		if(this.getP1Name().length() < 2){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "    Enter a valid name for HumanPlayer 1\r\n (Must be longer than 1 character)");
			return;
		}
		if(this.getP2Name().length() < 2){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "    Enter a valid name for HumanPlayer 2\r\n (Must be longer than 1 character)");
			return;
		}
		if(this.getP1Color().toLowerCase().equalsIgnoreCase(this.getP2Color())){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Please choose seperate colors for each player");
			return;
		}
		if(this.getP1Color().toLowerCase().equalsIgnoreCase("white")){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Please choose a different color than white");
			return;
		}
		if(this.getP2Color().toLowerCase().equalsIgnoreCase("white")){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Please choose a different color than white");
			return;
		}
		if(this.getP1Color().length() < 2){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Enter a color for HumanPlayer 1");
			return;
		}
		int check = checkColor(this.getP1Color());
		if(check == 1)
			return;
		
		if(this.getP2Color().length() < 2){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Enter a color for HumanPlayer 2");
			return;
		}
		check = checkColor(this.getP2Color());
		if(check == 1)
			return;
		
		this.ngs.startGame();
	}
		
	/**
	 * Determines if the players inputted
	 * color is valid
	 * @param c
	 * @return
	 */
	private int checkColor(String c) {
		Color color;
		try {
		    java.lang.reflect.Field field = Color.class.getField(c.toLowerCase());
		    color = (Color)field.get(this.ngs.getMainWindow());
		} catch (Exception e) {
		    color = null; // Not defined
		}
		if(color == null){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), c + " is not a valid color");
			return 1;
		}
		return 0;
	}
	
	/***
	 * Returns player 1's inputted name
	 * @return
	 */
	public String getP1Name() {
		return this.p1Name.getText();
	}
	
	/***
	 * Returns player 1's inputted color
	 * @return
	 */
	public String getP1Color() {
		return this.p1Color.getText();
	}
	
	/***
	 * Returns player 2's inputted name
	 * @return
	 */
	public String getP2Name() {
		return this.p2Name.getText();
	}
	
	/***
	 * Returns player 2's inputted color
	 * @return
	 */
	public String getP2Color() {
		return this.p2Color.getText();
	}
}
