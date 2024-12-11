package nine_mens_morris_game.player_model;

import java.awt.Color;

import nine_mens_morris_game.player_model.user_category.HumanPlayer;

public class GamePieces_Creation implements Comparable<GamePieces_Creation>
{
	private Color color;
	private HumanPlayer owner;
	private Integer status;
	private int id;
	private boolean selected;
	private GlowFIL gl;
	private boolean moving;
	private PiecesMovement mv;
	
	public static final int UNPLACED = 0;
	public static final int PLACED = 1;
	public static final int MOVED = 2;
	public static final int DEAD = 3;
	
	/***
	 * Creates a game piece given the color
	 * and owner
	 * @param color
	 * @param owner
	 */
	public GamePieces_Creation(Color color, HumanPlayer owner){
		this(color, owner, -1);
	}
	
	/***
	 * Creates a game piece
	 * given the parameters
	 * and sets statys ti 0
	 * @param color
	 * @param owner
	 * @param id
	 */
	public GamePieces_Creation(Color color, HumanPlayer owner, int id)
	{
		this.color = color;
		this.owner = owner;
		this.status = 0;
		this.id = id;
		this.gl = new GlowFIL();
	}
	
	public GamePieces_Creation(GamePieces_Creation gamePiecesCreation) {
		this.color = gamePiecesCreation.color;
		this.owner = gamePiecesCreation.owner;
		this.status = gamePiecesCreation.status;
		this.id = gamePiecesCreation.id;
		this.gl = gamePiecesCreation.gl;
	}

	/***
	 * returns the boolean
	 * of whether the piece is selected
	 * or not
	 * @return
	 */
	public boolean getSelected(){
		return this.selected;
	}
	
	/***
	 * Sets the piece to be selected or not
	 * @param select
	 */
	public void select(boolean select){
		this.selected = select;
		if(!select){
			this.gl = new GlowFIL();
		}
	}
	
	/**
	 * Determines if the piece is alive
	 * @return
	 */
	public boolean IsAlive()
	{
		if (this.status != DEAD)
			return true;
		else
			return false;
	}
	
	/***
	 * Returns the status of the piece
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}
	
	/***
	 * Sets the status of the piece to
	 * that of status
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	/***
	 * Returns the color
	 * @return
	 */
	public Color getColor() {
		return color;
	}
	
	/***
	 * returns the owner
	 * @return
	 */
	public HumanPlayer getOwner() {
		return owner;
	}

	/***
	 * Compares a piece to another
	 */
	@Override
	public int compareTo(GamePieces_Creation otherPiece)
	{
		return this.id - otherPiece.getID();
	}

	/***
	 * Returns the id of the piece
	 * @return
	 */
	public int getID() {
		return id;
	}
	
	public boolean inPlay()
	{
		if (status == PLACED || status == MOVED)
			return true;
		else
			return false;
	}

	public void setGl(GlowFIL gl) {
		this.gl = gl;
	}

	public GlowFIL getGl() {
		return gl;
	}

	public boolean isMoving() {
		return moving;
	}

	public void setMoving(boolean moving) {
		this.moving = moving;
	}

	public PiecesMovement getMv() {
		return mv;
	}

	public void setMv(PiecesMovement mv) {
		if(mv == null)
			this.mv = null;
		else
			this.mv = mv;
	}
}
