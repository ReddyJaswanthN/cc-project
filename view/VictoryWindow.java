package nine_mens_morris_game.view;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import nine_mens_morris_game.player_model.user_category.HumanPlayer;

public class VictoryWindow extends JComponent{

	/**
	 * Victory Screen for
	 * nine-mens-morris
	 */
	private static final long serialVersionUID = 4655939260856856256L;
    private JButton pAgainBut;
    private JButton quitBut;
    private MainUIWindow mw;
    
    /***
     * Default constructor for the
     * victory screen
     * 
     * Shows the winners name and the losers
     * name along with a short message
     * 
     * Has buttons to play again or quit
     * @param mw
     * @param victor
     * @param loser
     */
	public VictoryWindow(MainUIWindow mw, HumanPlayer victor, HumanPlayer loser) {
		this.mw = mw;
        JPanel winnerPanel = new JPanel();
        JLabel winnerText = new JLabel();
        JPanel loserPanel = new JPanel();
        JLabel loserText = new JLabel();
        JPanel buttonPanel = new JPanel();
        pAgainBut = new JButton();
        quitBut = new JButton();

        setLayout(new java.awt.GridLayout(3, 1));

        winnerPanel.setLayout(new java.awt.BorderLayout());

        winnerText.setFont(new java.awt.Font("Traditional Arabic", 0, 30)); // NOI18N
        winnerText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        String s = "Congratulations %s, Good Game";
        s = String.format(s, victor.getName());
        winnerText.setText(s);
        winnerPanel.add(winnerText, java.awt.BorderLayout.CENTER);

        add(winnerPanel);

        loserPanel.setLayout(new java.awt.BorderLayout());

        loserText.setFont(new java.awt.Font("Traditional Arabic", 0, 30)); // NOI18N
        loserText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        String t = "Sorry, %s.  Better luck next time";
        t = String.format(t, loser.getName());
        loserText.setText(t);
        loserPanel.add(loserText, java.awt.BorderLayout.CENTER);

        add(loserPanel);

        buttonPanel.setLayout(new java.awt.GridLayout(1, 2));

        pAgainBut.setFont(new java.awt.Font("Traditional Arabic", 0, 48)); // NOI18N
        pAgainBut.setText("Play Again?");
        buttonPanel.add(pAgainBut);

        quitBut.setFont(new java.awt.Font("Traditional Arabic", 0, 48)); // NOI18N
        quitBut.setText("Quit");
        buttonPanel.add(quitBut);

        add(buttonPanel);
        
        pAgainBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGame(evt);
            }
        });
        quitBut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                end(evt);
            }
        });
        
	}

	/**
	 * Ends the program
	 * @param evt
	 */
	private void end(ActionEvent evt) {
		this.mw.quit(evt);		
	}

	/**
	 * Starts a new game
	 * @param evt
	 */
	private void newGame(ActionEvent evt) {
		this.mw.reset(evt);
	}
}
