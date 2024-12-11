package nine_mens_morris_game.view.newGame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class NewPVE extends JPanel {
	/**
	 * NewPVE screen for
	 * nine-mens-morris
	 */
	private static final long serialVersionUID = 6048218193452708272L;
	private NewGameScreen ngs;
	private JTextField p1Name;
	private JTextField p1Color;
	private JButton startBut;

	/***
	 * Default constructor
	 * for a new PVE game
	 * @param ngs
	 */
	public NewPVE(NewGameScreen ngs) {
		this.ngs = ngs;
        JPanel top = new JPanel();
        JLabel message = new JLabel();
        JPanel middle = new JPanel();
        JPanel p1BackPanel = new JPanel();
        JLabel p1NameLabel = new JLabel();
        p1Name = new JTextField();
        JLabel p1ColorLabel = new JLabel();
        p1Color = new JTextField();
        JPanel bot = new JPanel();
        startBut = new JButton();

        setLayout(new java.awt.GridLayout(3, 1, 0, 150));

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

        add(middle);

        bot.setLayout(new java.awt.BorderLayout());

        startBut.setFont(new java.awt.Font("Traditional Arabic", 0, 36)); // NOI18N
        startBut.setText("Start Game");
        bot.add(startBut, java.awt.BorderLayout.CENTER);

        add(bot);
        startBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                start(evt);
            }
        });
        this.setSize(700,700);
	}

	/**
	 * Determines if the user_category input
	 * is valid and starts a game if so
	 * @param evt
	 */
	private void start(ActionEvent evt){
		if(this.getP1Name().length() < 1){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), " Enter a valid name for HumanPlayer 1\r\n (Must be at least 1 character)");
			return;
		}
		if(this.getP1Color().length() < 1){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Enter a color for HumanPlayer 1");
			return;
		}
		int check = checkColor(this.getP1Color());
		if(check == 1)
			return;
		if(getColor(this.getP1Color()).equals(this.ngs.getComputer().getColor())){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Please choose a different color than what the computer chose");
			return;
		}
		if(this.getP1Color().equalsIgnoreCase("white")){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Please choose a different color than white");
			return;
		}
		this.ngs.startGame();
	}
	
	/**
	 * Checks if the users inputted color
	 * is a valid color
	 * @param c
	 * @return
	 */
	private int checkColor(String c) {
		Color color;
		try {
		    java.lang.reflect.Field field = Color.class.getField(c.toLowerCase());
		    color = (Color)field.get(null);
		} catch (Exception e) {
		    color = null; // Not defined
		}
		if(color == null){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), c + " is not a valid color");
			return 1;
		}
		if(color == Color.white){
			JOptionPane.showMessageDialog(this.ngs.getMainWindow(), "Please choose a different color than white");
			return 1;
		}
		return 0;
	}
	
	public Color getColor(String color){
		Color c = null;	
		try {
		    Field field = Color.class.getField(color.toLowerCase());
		    c = (Color)field.get(null);
		} catch (Exception e) {
		    c = null; // Not defined
		}
		
		return c;
	}

	/***
	 * Returns the name player 1
	 * entered
	 * @return
	 */
	public String getP1Name() {
		return this.p1Name.getText();
	}
	
	/***
	 * Returns the color player 1 chose
	 * @return
	 */
	public String getP1Color() {
		return this.p1Color.getText();
	}

}
