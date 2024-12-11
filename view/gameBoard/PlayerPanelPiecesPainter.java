package nine_mens_morris_game.view.gameBoard;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

import nine_mens_morris_game.player_model.user_category.HumanPlayer;

public class PlayerPanelPiecesPainter extends JPanel{

	/**
	 * HumanPlayer Panel of pieces
	 * for nine-mens-morris
	 */
	private static final long serialVersionUID = -3043526960369929645L;
	private static final int COLS = 1;
	private static final int CELL_SIZE = 59;
	private HumanPlayer humanPlayer;

	
	/***
	 * Constructor for humanPlayer Panel
	 * @param p1
	 */
	public PlayerPanelPiecesPainter(HumanPlayer p1) {
		this.humanPlayer = p1;

		this.setPreferredSize(
                new Dimension(CELL_SIZE * COLS, 525));
         this.setBackground(Color.WHITE);
	}

	/**
	 * This function will paint
	 * the pieces of the humanPlayer
	 * that have yet to be humanPlayer
	 * on the side of the gameBoard
	 * 
	 * Will eventually show empty
	 * 
	 * Should show opponents pieces gathered
	 * Later work
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Integer pieces = 9 - this.humanPlayer.getPiecesPlayed();
        for (int r=0; r<pieces; r++) {
            for (int c=0; c<COLS; c++) {
                int x = c * CELL_SIZE;
                int y = r * CELL_SIZE + 30;
//            	g.setColor(Color.WHITE);
//            	g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
            	g.setColor(this.humanPlayer.getColor());
            	g.fillOval(x+10, y+10, CELL_SIZE-20, CELL_SIZE-20);
            }
        }
	}

}
