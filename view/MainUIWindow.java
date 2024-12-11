package nine_mens_morris_game.view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.imageio.ImageIO;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nine_mens_morris_game.Players_Creation_controller.nine_mens_morris_gameGameModel;
import nine_mens_morris_game.player_model.BoardCreation;
import nine_mens_morris_game.player_model.user_category.AI_ComputerPlayer;
import nine_mens_morris_game.player_model.user_category.HumanPlayer;
import nine_mens_morris_game.view.gameBoard.GameBoardDisplayControler;
import nine_mens_morris_game.view.newGame.NewGameScreen;

public class MainUIWindow extends JFrame implements WindowListener{

	/**
	 * Main Window for
	 * nine-mens-morris
	 */
	private static final long serialVersionUID = 3638794002119631337L;
	private CardLayout cards;
	
	private GameBoardDisplayControler gb;
	private WelcomeSplashScreen ws;
	private NewGameScreen sd;
	private VictoryWindow vs;
	private nine_mens_morris_gameGameModel nine_mens_morris_game;
	
	private JPanel cardPanel;
	
	private JMenuBar jMenuBar1;
	  
	private JMenu jMenu1;
	private JMenu jMenu3;
	private JMenu jMenu2;
	private JMenuItem jMenuItem1;
	private JMenuItem jMenuItem2;
	private JMenuItem jMenuItem3;
	private JMenuItem jMenuItem5;
	private JMenuItem jMenuItem6;
	private JMenuItem jMenuItem7;
	
	/***
	 * Constructor for the mainWindow
	 * 
	 * Will load the intial state of the
	 * game which is the welcome screen
	 * 
	 */
	public MainUIWindow(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Nine Mens Morris Game BY THE BRIANIACS");
        this.setSize(600,600);

        this.createMenuBar();
        this.setLayout(new BorderLayout());
        
        this.ws = new WelcomeSplashScreen(this);
        
        cards = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cards);

		cardPanel.add(this.ws, "WelcomeSplashScreen");
        cards.show(cardPanel, "WelcomeSplashScreen");
	        
		this.add(cardPanel);
		
		BufferedImage Icon = null;
		try {
			Icon = ImageIO.read(new File("resources/taskBarIcon.png"));
		} catch (IOException e) {
			e.printStackTrace();
		};
		
		this.setIconImage(Icon);
		this.setSize(700,700);
		this.setLocation(0,0);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	/***
	 * Creates the menuBar that
	 * the mainWindow will use
	 */
	private void createMenuBar() {
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenuItem2 = new JMenuItem();
        jMenuItem3 = new JMenuItem();
        jMenu2 = new JMenu();
        jMenu3 = new JMenu();
        jMenuItem5 = new JMenuItem();
        jMenuItem6 = new JMenuItem();
        jMenuItem7 = new JMenuItem();
           
        jMenu1.setText("File");
        jMenu2.setText("Edit");
        jMenu3.setText("Help");

        jMenuItem1.setText("New Game");
        jMenuItem2.setText("Cheat Mode");
        jMenuItem3.setText("Quit");
        jMenuItem5.setText("How to Play");
        jMenuItem6.setText("About");
        jMenuItem7.setText("Color options");
       
        jMenuItem1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		reset(evt);
        	}
        });
        jMenuItem3.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		quit(evt);
        	}
        });
        jMenuItem2.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		cheatMode(evt);
        	}
        });
        jMenuItem5.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		showHowTo(evt);
        	}
        });
        jMenuItem6.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		showAbout(evt);
        	}
        });
        jMenuItem7.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt){
        		showColors(evt);
        	}
        });
        
        jMenu1.add(jMenuItem1);
        jMenu2.add(jMenuItem2);
        jMenu1.add(jMenuItem3);
        jMenu3.add(jMenuItem5);
        jMenu3.add(jMenuItem6);
        jMenu3.add(jMenuItem7);
        
        jMenuBar1.add(jMenu1);
        jMenuBar1.add(jMenu2);
        jMenuBar1.add(jMenu3);

        this.setJMenuBar(jMenuBar1);
	}
	
	private void showColors(ActionEvent evt) {
		String colors[] = AI_ComputerPlayer.colors;
		JLabel colours = new JLabel();
		String s= "<HTML> ";
		for(int i=0; i<colors.length; i++)
				s = s + "<br>" + colors[i] + "</br>";
		s = s + "</html>";
		colours.setText(s);
		JOptionPane.showMessageDialog(this, colours);
		
	}

	/***
	 * Will show information about
	 * the creation of the game
	 * @param evt
	 */
	private void showAbout(ActionEvent evt) {
		JLabel about = new JLabel();
		about.setText("<html>Created by:<br>"+"" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Bhavana Navari </br><br>"+
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Deepthi Reddy</br><br>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Jaswanth</br><br>" +
				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Chandra sekhar</br></html>");
		JOptionPane.showMessageDialog(this, about);
	}

	/***
	 * Will show an html
	 * page showing the user_category how to play
	 * 
	 * Will be updated with one of
	 * our user_category pages instead of using
	 * a random website
	 * @param evt
	 */
	private void showHowTo(ActionEvent evt) {
		JEditorPane editorPane = new JEditorPane();
		editorPane.setEditable(false);
		java.net.URL howToUrl = null;
		try {
			howToUrl = new java.net.URL("http://cse.unl.edu/~zsims/howTo.html");
		} catch (MalformedURLException e) {
		}
		try {
	        editorPane.setPage(howToUrl);
	    } catch (IOException e) {
	        System.err.println("Attempted to read a bad URL: " + howToUrl);
	    }
	    JOptionPane.showMessageDialog(this, editorPane);
    }

	/***
	 * Will activate cheat mode for the
	 * started game
	 * 
	 * If not started will alert user_category and return
	 * 
	 * If still in placement phase will alert user_category
	 * and return
	 * 
	 * Else will confirm selection and
	 * activate cheatmode
	 * @param evt
	 */
	private void cheatMode(ActionEvent evt) {
		if(this.nine_mens_morris_game == null){
			JOptionPane.showMessageDialog(this, "A game has not started yet");
			return;
		}
		if(this.nine_mens_morris_game.getBoard().GetCurrentPhase(this.nine_mens_morris_game.getCurrPlayer()) == BoardCreation.PLACEMENT_PHASE){
			JOptionPane.showMessageDialog(this, "Please wait until after placement phase\r\nto enter cheat mode");
			return;			
		}
		int confirm = JOptionPane.showConfirmDialog(this, "Do you want to enter cheat mode?");
		if(confirm == 0){
			this.nine_mens_morris_game.getBoard().setCheatMode();
		}
		else
			return;	
		
	}

	/***
	 * When the user_category selects to quit
	 * from the Menu Bar or from the 
	 * victory screen
	 * @param evt
	 */
	public void quit(ActionEvent evt) {
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?");
		if(confirm == 0){
			this.dispose();
			System.exit(NORMAL);
		}
		else
			return;	
	}

	/***
	 * Will display the card that is
	 * determined by the passed in string
	 * @param card
	 */
	public void changeCard(String card){
		this.cards.show(cardPanel, card);
	}
	
	/***
	 * Will confirm the user_category wants
	 * reset the game
	 * 
	 * if So calls the clear function
	 * Else returns to current game
	 * @param evt
	 */
	public void reset(ActionEvent evt){
		if(evt.getSource() == this.jMenuItem1){
			int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?");
			if(confirm == 0){
				this.clear();
				
			}
			else
				return;
		}
		this.clear();
	}
	
	/**
	 * Creates a new game by
	 * setting everything to null for a
	 * recreation
	 */
	private void clear() {
		this.changeCard("WelcomeSplashScreen");
		this.nine_mens_morris_game = null;
		this.sd = null;
		this.gb = null;
		this.vs = null;
	}

	/***
	 * After welcome screen
	 * shows screen to enter
	 * information for a new game
	 * @param mode
	 */
	public void newGame(Integer mode){
		this.sd = new NewGameScreen(this, mode);
		cardPanel.add(this.sd, "NewGameScreen");
		this.changeCard("NewGameScreen");
		this.setSize(700,700);
		this.setLocation(0,0);
	}
	
	/***
	 * Starts the game after getting
	 * information about the games
	 * players and the mode
	 * @param p1
	 * @param p2
	 * @param mode
	 */
	public void startGame(HumanPlayer p1, HumanPlayer p2, Integer mode){
		this.nine_mens_morris_game = new nine_mens_morris_gameGameModel(mode, p1, p2, this);
		this.nine_mens_morris_game.setPlayer1(p1);
		this.nine_mens_morris_game.setPlayer2(p2);
		this.gb = new GameBoardDisplayControler(nine_mens_morris_game, this);
		cardPanel.add(this.gb, "GameBoardDisplayControler");
		this.changeCard("GameBoardDisplayControler");
		this.setSize(700,700);
		this.setLocation(0,0);
	}
	
	/***
	 * Shows the victory screen after
	 * the game is over
	 */
	public void showEnd() {
		this.vs = new VictoryWindow(this, this.nine_mens_morris_game.getVictor(), this.nine_mens_morris_game.getLoser());
		cardPanel.add(this.vs, "EndGame");
		this.changeCard("EndGame");
		this.setLocationRelativeTo(null);
		this.setSize(700,700);
		this.setLocation(0,0);
	}

	public GameBoardDisplayControler getGameBoard() {
		return this.gb;
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		this.dispose();
		System.exit(0);
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
