package nine_mens_morris_game.view.gameBoard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import nine_mens_morris_game.Players_Creation_controller.nine_mens_morris_gameGameModel;
import nine_mens_morris_game.player_model.BoardCreation;
import nine_mens_morris_game.view.MainUIWindow;

public class GameBoardDisplayControler extends JPanel implements ActionListener{

	/**
	 * GameBoardDisplayControler display
	 * for nine-mens-morris
	 */
	private static final long serialVersionUID = 2364448613335062368L;
	private nine_mens_morris_gameGameModel gameModel;
	private MainUIWindow mw;
	private PlayerPanelPiecesPainter p1;
	private PlayerPanelPiecesPainter p2;
	private GamePanel gp;
	private JLabel p1Name;
	private JLabel p2Name;
	private JLabel p1Score;
	private JLabel p2Score;
	private JLabel currentPlayer;
	private JLabel error;
	private JPanel topPanel;
	private JPanel scorePanel;
	private Timer timer;
	private BufferedImage board;
	/***
	 * The constructor for the GameBoardDisplayControler
	 * The gamemodel is used by itself and
	 * the player panels and gameBoard
	 * 
	 * The Mainwindow is passed to change
	 * to victory screen when game is over	
	 * @param nine_mens_morris_game
	 * @param mw
	 */
	public GameBoardDisplayControler(nine_mens_morris_gameGameModel nine_mens_morris_game, MainUIWindow mw){
		this.gameModel = nine_mens_morris_game;
		this.mw = mw;
		this.p1 = new PlayerPanelPiecesPainter(nine_mens_morris_game.getPlayer1());
		this.p2 = new PlayerPanelPiecesPainter(nine_mens_morris_game.getPlayer2());
		this.gameModel = nine_mens_morris_game;
		this.gp = new GamePanel(this.gameModel, this);
		
		this.setSize(950, 800);
		
		this.topPanel = new JPanel();
		this.scorePanel = new JPanel();
 		try {
			this.board = ImageIO.read(new File("resources\\boardBG.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		p1Name = new JLabel(this.gameModel.getPlayer1().getName());
		p2Name = new JLabel(this.gameModel.getPlayer2().getName());
		p1Score = new JLabel("Score: " + String.valueOf(this.gameModel.getPlayer1().getScore()));
		p2Score = new JLabel("Score: " + String.valueOf(this.gameModel.getPlayer1().getScore()));
		currentPlayer = new JLabel(this.gameModel.getCurrPlayer().getName().trim() + ", " + this.gameModel.getPhaseText().trim());
		error = new JLabel("               ");
		
		p1Name.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 24));
		p2Name.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 24));
		p1Name.setForeground(nine_mens_morris_game.getPlayer1().getColor());
		p2Name.setForeground(nine_mens_morris_game.getPlayer2().getColor());
		currentPlayer.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 30));
		error.setFont(new java.awt.Font("Times New Roman", Font.BOLD, 26));
		error.setForeground(Color.BLACK);
		p1Name.setHorizontalAlignment(JLabel.CENTER);
		currentPlayer.setHorizontalAlignment(JLabel.CENTER);
		p2Name.setHorizontalAlignment(JLabel.CENTER);
		p1Score.setHorizontalAlignment(JLabel.CENTER);
		p2Score.setHorizontalAlignment(JLabel.CENTER);
		error.setHorizontalAlignment(JLabel.CENTER);
	
		this.topPanel.setLayout(new BorderLayout());
		this.topPanel.add(this.p1Name, BorderLayout.WEST);
		this.topPanel.add(this.p2Name, BorderLayout.EAST);
		this.topPanel.add(this.currentPlayer, BorderLayout.NORTH);
		
		
		this.scorePanel.setLayout(new BorderLayout());
		this.scorePanel.add(p1Score, BorderLayout.WEST);
		this.scorePanel.add(p2Score, BorderLayout.EAST);
		this.scorePanel.add(this.error, BorderLayout.CENTER);
		this.topPanel.add(this.scorePanel, BorderLayout.SOUTH);
		
		this.topPanel.setBackground(Color.WHITE);
		this.scorePanel.setBackground(Color.WHITE);
		
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(this.p1, BorderLayout.WEST);
		this.add(this.gp, BorderLayout.CENTER);
		this.add(this.p2, BorderLayout.EAST);
		
		this.topPanel.setOpaque(false);
		this.scorePanel.setOpaque(false);
		this.gp.setOpaque(false);
		this.p1.setOpaque(false);
		this.p2.setOpaque(false);
		
		this.setVisible(true);
		this.setOpaque(false);
		int delay = 1500;
        this.timer = new Timer(delay, this);
	}
	
	@Override
	/***
	 * This function will first check if
	 * the game is over, and if so
	 * move on the the victory screen
	 * 
	 * It will then update itself, the
	 * player panels, and the gameBoard
	 * display
	 */
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if(this.gameModel.getStatus() == BoardCreation.GAMEOVER_PHASE){
			this.mw.showEnd();
		}
		
		if(this.gameModel.getCurrPlayer().isHuman())
			this.currentPlayer.setText(this.gameModel.getCurrPlayer().getName() + ", " + this.gameModel.getPhaseText());
		else
			this.currentPlayer.setText(this.gameModel.getPhaseText());

		this.p1Score.setText("Score: " + String.valueOf(this.gameModel.getPlayer1().getScore()));
		this.p2Score.setText("Score: " + String.valueOf(this.gameModel.getPlayer2().getScore()));
		
		this.p1.repaint();
		this.p2.repaint();
		this.gp.repaint();
		int steps = 0;
		while(this.gameModel.isMoving() && steps < 10){
			this.gp.repaint();
			drawBackground(g);
			steps++;
		}
		
		this.gp.repaint();
		drawBackground(g);
	}
	
	private void drawBackground(Graphics g) {
		g.drawImage(board, 0, 0, 700, 700, 0, 0, 700, 700, null);
	}
		
	
	public void setError(String s, int delay){
		this.timer.setDelay(delay);
		this.error.setText(s);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.error.setText("                 ");
		this.timer.stop();
	}
	
	public void startTimer(){
		this.timer.start();
	}
}
