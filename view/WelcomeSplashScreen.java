package nine_mens_morris_game.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class WelcomeSplashScreen extends JComponent{
	/**
	 * Welcome screen for
	 * nine-mens-morris
	 */
	private static final long serialVersionUID = 5219597153026471980L;
	private MainUIWindow mw;
	private JLabel welcome;
	private JButton pve;
	private JButton pvp;
	private JPanel top;
	private JPanel bot;
	
	/***
	 * Constructor for the welcome
	 * screen
	 * 
	 * Lets the player decide what type of game to play
	 * @param mw
	 */
	public WelcomeSplashScreen(MainUIWindow mw){
		this.mw = mw;

        welcome = new JLabel();
        pve = new JButton();
        pvp = new JButton();

        welcome.setFont(new Font("Traditional Arabic", 1, 25));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setText("Nine Men's Morris Game");



        top = new JPanel();
        welcome = new JLabel();
        bot = new JPanel();
        pve = new JButton();
        pvp = new JButton();

        setLayout(new GridLayout(2, 2));

        top.setLayout(new BorderLayout());

        welcome.setFont(new Font("Tahoma", 0, 25)); // NOI18N
        welcome.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        welcome.setText("Nine Men's Morris Game");
        top.add(welcome, BorderLayout.CENTER);

        add(top);

        bot.setLayout(new GridLayout());

        pve.setFont(new Font("Traditional Arabic", 0, 18)); // NOI18N
        pve.setText("HumanPlayer VS Computer");
        bot.add(pve);

        pvp.setFont(new Font("Traditional Arabic", 0, 18)); // NOI18N
        pvp.setText("HumanPlayer VS HumanPlayer");

        pve.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		changeWindow(evt);
        	}
        });
		pvp.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		changeWindow(evt);
        	}
        });
		
        bot.add(pvp);
        //this.pve.setEnabled(false);

        add(bot);
	}
	
	/**
	 * Will start a new game depending
	 * on the button that was clicked
	 * @param evt
	 */
	private void changeWindow(ActionEvent evt) {
		if((JButton)evt.getSource() == this.pve)
			this.mw.newGame(0);
		else if((JButton)evt.getSource() == this.pvp)
			this.mw.newGame(1);
	}
}
