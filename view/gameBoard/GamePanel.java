package nine_mens_morris_game.view.gameBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import nine_mens_morris_game.Players_Creation_controller.nine_mens_morris_gameGameModel;
import nine_mens_morris_game.player_model.BoardCreation;
import nine_mens_morris_game.player_model.GamePieces_Creation;
import nine_mens_morris_game.player_model.GlowFIL;
import nine_mens_morris_game.player_model.PiecesMovement;

public class GamePanel extends JPanel implements MouseListener, ActionListener{

	/**
	 * Game BoardCreation display
	 * for nine-mens-morris
	 */
	private static final long serialVersionUID = 9076559530700021419L;
	private static final int ROWS = 7;
	private static final int COLS = 7;
	public static final int CELL_SIZE = 75;
	private BufferedImage board;
	private nine_mens_morris_gameGameModel gameModel;
	private GameBoardDisplayControler gb;
	
	
	/***
	 * Constructor for gamePanel
	 * 
	 * Creates the objects that will 
	 * need to be painted of the screen
	 * to resemble the board
	 * 
	 * Uses the gameModel to determine what the
	 * board looks like
	 * @param game
	 * @param gb
	 */
	public GamePanel(nine_mens_morris_gameGameModel game, GameBoardDisplayControler gb){
		this.gb = gb;
		this.gameModel = game;
		try {
			this.board = ImageIO.read(new File("resources\\nine_mens_morris_gameBoard.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.setPreferredSize(
                new Dimension(CELL_SIZE * COLS+50, CELL_SIZE * ROWS+50));
         this.setBackground(Color.WHITE);
         this.addMouseListener(this);
         
         int delay = 500;
         new Timer(delay, this).start();
	}	
	
	/**
	 * This function will first display
	 * the background image of the board
	 * 
	 * Then it will load the state of the gameboard
	 * and print onto their the players pieces
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        GamePieces_Creation[][] gameBoard = this.gameModel.getBoard().getBoardArray();
    	drawBackground(g);
        for (int r=0; r<ROWS; r++) {
            for (int c=0; c<COLS; c++) {
            	int x = c * CELL_SIZE+25;
            	int y = r * CELL_SIZE+25;

				if(gameBoard[r][c] == null || gameBoard[r][c].getID() == -1){
					continue;
            	}
				
				else if(gameBoard[r][c].isMoving()){
					PiecesMovement mv = gameBoard[r][c].getMv();
					if(!mv.update()){
						g.setColor(gameBoard[r][c].getColor());
	            		g.fillOval((int)mv.getCurX()+10, (int)mv.getCurY()+10, CELL_SIZE-20, CELL_SIZE-20);
					}
					else{
						this.gameModel.getBoard().setPiece(r, c, gameBoard[r][c]);
						gameBoard[r][c].setMoving(false);
						this.gameModel.setMoving(false);
					}
				}
				
				else if(gameBoard[r][c].getSelected()){
            		g.setColor(gameBoard[r][c].getColor());
            		g.fillOval(x+10, y+10, CELL_SIZE-20, CELL_SIZE-20);
            		
            		GlowFIL gl = gameBoard[r][c].getGl();
            		gl.update();
            		int newx = (int) gl.getX();
            		int newy = (int) gl.getY();
            		
            		g.setColor(gl.getColor());
            		g.fillOval(x+newx, y+newy, CELL_SIZE-(newx*2), CELL_SIZE-(newy*2));
            			
				}
				
				else{
					g.setColor(gameBoard[r][c].getColor());
        			g.fillOval(x+10, y+10, CELL_SIZE-20, CELL_SIZE-20);
				}
            }
        }
	}

	/**
	 * This function draws the board image
	 * on the screen in the background
	 * @param g
	 */
	private void drawBackground(Graphics g) {
		g.drawImage(board, 25, 25, 725, 725, 0, 0, 700, 700, null);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(!this.gameModel.getCurrPlayer().isHuman() && !this.gameModel.isMoving()){
        	this.gameModel.newAIMove();
        }
		this.revalidate();
		this.gb.repaint();
	}	
	
	@Override
	/***
	 * If a mouse is clicked determine
	 * the location that was clicked
	 * 
	 * If invalid return and wait
	 * for a valid click
	 * 
	 * Else determine if valid spot
	 */
	public void mouseClicked(MouseEvent e) {
		int col = e.getX()/CELL_SIZE;
        int row = e.getY()/CELL_SIZE;
        if(this.gameModel.isMoving())
        	return;
        if(row < 0 || col < 0 || row > 6 || col > 6)
        	return;
        
        if(!this.convertToLabel(row, col) && this.gameModel.getCurrPlayer().isHuman()){
        	return;
        }
        
        this.revalidate();
        this.gb.repaint();
	}
	
	/***
	 * Given the row and col, convert
	 * them to a label that is a location
	 * 
	 * If invalid return false
	 * if not possible move return false
	 * 
	 * else return true
	 * @param row
	 * @param col
	 * @return
	 */
	private boolean convertToLabel(int row, int col) {
		char[] labels = BoardCreation.ALPHABET;
		String[] points = BoardCreation.BOARDREFERENCE;
		String label = "Z";
		
		for(int i=0; i<24; i++){
			String t[] = points[i].split(",");
			int row2 = Integer.parseInt(t[0]);
			int col2 = Integer.parseInt(t[1]);
			if(row2 == row && col2 == col)
				label = String.valueOf(labels[i]);
		}
		if(label == "Z")
			return false;
			
		if(!this.gameModel.newMove(label)){
			return false;
		}
		
		return true;
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		
	}


}
