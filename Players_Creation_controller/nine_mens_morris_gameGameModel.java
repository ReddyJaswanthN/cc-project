package nine_mens_morris_game.Players_Creation_controller;

import nine_mens_morris_game.player_model.BoardCreation;
import nine_mens_morris_game.player_model.GamePieces_Creation;
import nine_mens_morris_game.player_model.LocationManager;
import nine_mens_morris_game.player_model.user_category.AI_ComputerPlayer;
import nine_mens_morris_game.player_model.user_category.HumanPlayer;

import nine_mens_morris_game.view.MainUIWindow;

public class nine_mens_morris_gameGameModel {
	private BoardCreation currBoardCreation;
	private HumanPlayer p1;
	private HumanPlayer p2;
	private AI_ComputerPlayer comp;
	private HumanPlayer curHumanPlayer;
	private HumanPlayer Victor;
	private HumanPlayer Loser;
	private Integer gameMode = -1;
	private GamePieces_Creation pieceSelected;
	private boolean moving;
	
	/**
	* Constructor for the GameModel
	* Takes in a mode, two players, and
	* a mainWindow
	*
	* The players are created by the gui
	* the mode will be to determine if AI
	* is present
	*
	* Mw is brought simply to pass to BoardCreation
	* @param mode
	* @param p1
	* @param p2
	* @param mw
	*/
	public nine_mens_morris_gameGameModel(Integer mode, HumanPlayer p1, HumanPlayer p2, MainUIWindow mw){
		this.gameMode = mode;
		this.currBoardCreation = new BoardCreation(mw);
		this.p1 = p1;
		if(this.gameMode == 0){
			this.comp = (AI_ComputerPlayer) p2;
			this.comp.setnine_mens_morris_game(this);
			this.p2 = p2;
		}
		else
			this.p2 = p2;
		
		double t;
		t = Math.random() * 50;
		if(t <= 25.000)
			this.curHumanPlayer = this.p1;
		else
			this.curHumanPlayer = this.p2;
		
		if(this.gameMode == 0){
			this.curHumanPlayer = this.p1;
		}
		this.Victor = p1;
		this.Loser = p2;
	}
	
	/***
	* Will return the status of the board
	*
	* Used by the gui
	* @return
	*/
	public int getStatus(){
		return this.currBoardCreation.GetCurrentPhase(this.curHumanPlayer);
	}
	
	/***
	* The main_Entry function of the GameModel
	*
	* This will check what phase the game is in
	* and determine the correct action from there on out
	*
	* Will return false if an errors occur
	* Will update the status of the board
	* @param label
	* @return
	*/
	public boolean newMove(String label) {
		int gamephase = this.currBoardCreation.GetCurrentPhase(this.curHumanPlayer);
		if(gamephase == BoardCreation.GAMEOVER_PHASE){
			return true;
		}
		if(this.currBoardCreation.numMovesAvailable(this.curHumanPlayer) <= 0 && gamephase != BoardCreation.PLACEMENT_PHASE){
			this.Victor = this.inactivePlayer();
			this.Loser = this.curHumanPlayer;
			this.currBoardCreation.SetCurrentPhase(BoardCreation.GAMEOVER_PHASE);
			this.currBoardCreation.newMessageDialog(this.Loser + " has no more moves available and losses the game", 1500);
		}
		switch(gamephase)
		{
			case BoardCreation.PLACEMENT_PHASE: //placement
				// If placement is successful, move on to next player.
				if (PlacementPhase(label))
					nextPlayer();
				else
					return false;
				break;
			
			case BoardCreation.MOVEMENT_PHASE: //movement
				// Next player on successful move.
				// Will return false if a move results in a mill, so that the player isn't skipped.
				if(this.pieceSelected == null)
					selectPiece(label);
				else if(this.pieceSelected != null){
					if(this.pieceSelected == this.currBoardCreation.GetLocationByLabel(label).getPiece()){
						this.pieceSelected.select(false);
						this.pieceSelected = null;
					}
					else if(MovementPhase(label)){
						nextPlayer();
						this.pieceSelected.select(false);
						this.pieceSelected = null;
						this.moving = true;
					}else{
						if(this.currBoardCreation.GetCurrentPhase(this.curHumanPlayer) == BoardCreation.REMOVAL_PHASE){
							this.pieceSelected.select(false);
							this.pieceSelected = null;
							this.moving = true;
						}
					}
				}
				else
				return false;
				break;
			
			case BoardCreation.REMOVAL_PHASE: //removal
				boolean passed = RemovalPhase(label);
				if(passed)
					nextPlayer();
				else
					if(this.currBoardCreation.GetCurrentPhase(this.inactivePlayer()) == BoardCreation.GAMEOVER_PHASE){
						this.Victor = this.curHumanPlayer;
						this.Loser = this.inactivePlayer();
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return true;
					}
					else
						return false;
				break;
			
			default:
				System.out.println("Invalid game phase. Exiting.");
				System.exit(1);
				break;
		}
		
		this.currBoardCreation.updateBoard();
		return true;
	}
	
	/***
	 * Used to determine what type of move the AI should take
	 * based off of the current phase of the game
	 * @return
	 */
	public boolean newAIMove() {
		if(this.curHumanPlayer.isHuman())
			return false;
		AI_ComputerPlayer p = (AI_ComputerPlayer) this.curHumanPlayer;
		
		int gamephase = this.currBoardCreation.GetCurrentPhase(this.curHumanPlayer);
		if(gamephase == BoardCreation.GAMEOVER_PHASE){
			return true;
		}
		boolean success = true;
		switch(gamephase)
		{
			case BoardCreation.PLACEMENT_PHASE:
				success = p.placeMove();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				break;
			case BoardCreation.MOVEMENT_PHASE:
				success = p.moveMove();
				break;
			case BoardCreation.REMOVAL_PHASE:
				success = p.remoMove();
				break;
			default:
				System.out.println("Invalid movement phase");
				break;
		}
		this.currBoardCreation.updateBoard();
		return success;
	}
	
	/***
	* Will select the current piece and update its
	* status
	*
	* Used for the GUI to select a piece
	* and then move that piece
	* @param label
	* @return
	*/
	private boolean selectPiece(String label) {
		LocationManager t = this.currBoardCreation.GetLocationByLabel(label);
		if(t.getPiece() == null || t.getPiece().getOwner() != this.curHumanPlayer)
			return false;
		this.pieceSelected = t.getPiece();
		this.pieceSelected.select(true);
		return true;
	}
	
	  /***
	* Will determine the location and piece to
	* remove given the label
	*
	* Will then attempt to remove that piece
	* @param label
	* @return
	*/
	private boolean RemovalPhase(String label) {
		LocationManager t = this.currBoardCreation.GetLocationByLabel(label);
		boolean v = true;
		if(t.getPiece().getOwner() != this.inactivePlayer())
			v = false;
		
		return this.currBoardCreation.RemovePiece(this.inactivePlayer(), t.getPiece().getID(), v);
	}
	
	/***
	* Will determine the location and piece to
	* place given the label
	*
	* Will then attempt to place that piece
	* @param label
	* @return
	*/
	private boolean PlacementPhase(String label) {
		LocationManager t = this.currBoardCreation.GetLocationByLabel(label);
		return this.currBoardCreation.PlacePiece(this.curHumanPlayer, 8-this.curHumanPlayer.getPiecesPlayed(), t.getLabel());
	}
	
	/***
	* Will determine the location and piece to move
	* from the given label
	*
	* Will then attempt to move that piece
	* @param label
	* @return
	*/
	private boolean MovementPhase(String label){
		if (this.currBoardCreation.numMovesAvailable(this.curHumanPlayer) == 0 && this.curHumanPlayer.getScore() > 3){
			return true;
		}
		LocationManager t = this.currBoardCreation.GetLocationByLabel(label);
		return this.currBoardCreation.MovePiece(this.curHumanPlayer, pieceSelected.getID(), t.getLabel());
	}
	
	/***
	* Will return the winner of the game
	* Used in the gui victory screen
	* @return
	*/
	public HumanPlayer getVictor() {
	    	return this.Victor;
	}
	
	/***
	* Will return the loser of the game
	* Used in the gui victory screen
	* @return
	*/
	public HumanPlayer getLoser() {
		return this.Loser;
	}
	
	/**
	* Will set the next player
	*/
	private void nextPlayer(){
		this.curHumanPlayer = inactivePlayer();
	}
	
	/**
	* Will return the inactive player
	* @return
	*/
	private HumanPlayer inactivePlayer(){
		if (this.curHumanPlayer == this.p1)
			return this.p2;
		else
			return this.p1;
	}
	
	/***
	* Will return the current HumanPlayer
	* @return
	*/
	public HumanPlayer getCurrPlayer() {
		return this.curHumanPlayer;
	}
	
	/**
	* Will set the CurrentPhase
	* to the passed in mode
	* @param mode
	*/
	public void setGamePhase(Integer mode){
		this.currBoardCreation.SetCurrentPhase(mode);
	}
	
	/**
	* Get the gameMode
	* Currently unused
	* Will be used for pve game mode
	* when it is added
	* @return
	*/
	public Integer getMode(){
		return this.gameMode;
	}	
	
	/***
	* Return player 1
	* @return
	*/
	public HumanPlayer getPlayer1() {
		return p1;
	}
	
	/***
	* Return player 2
	* @return
	*/
	public HumanPlayer getPlayer2() {
		return p2;
	}
	
	/***
	* Return the currentBoard being used
	* @return
	*/
	public BoardCreation getBoard() {
		return this.currBoardCreation;
	}
	
	/**
	* Set p1 to the passed in player
	* @param p
	*/
	public void setPlayer1(HumanPlayer p) {
		this.p1 = p;
	}
	
	/***
	* Set p2 to the passed in player
	* @param p
	*/
	public void setPlayer2(HumanPlayer p) {
		this.p2 = p;
	}
	
	/***
	* This function will return a message
	* based on the current phase of the game
	*
	* Used in the GUI for displaying what
	* to do at the top of the screen
	* @return
	*/
	public String getPhaseText() {
		if(this.gameMode == 0 && !this.curHumanPlayer.isHuman()){
			if(this.currBoardCreation.GetCurrentPhase(this.getCurrPlayer()) == BoardCreation.PLACEMENT_PHASE)
				return "The computer is placing a piece on the board";
			else if(this.currBoardCreation.GetCurrentPhase(this.getCurrPlayer()) == BoardCreation.MOVEMENT_PHASE)
				return "The computer is moving a piece on the board";
			else if(this.currBoardCreation.GetCurrentPhase(this.getCurrPlayer()) == BoardCreation.REMOVAL_PHASE)
				return "The computer is removing on of your pieces";
		}
		else{
			if(this.currBoardCreation.GetCurrentPhase(this.getCurrPlayer()) == BoardCreation.PLACEMENT_PHASE)
				return "place a piece on the board";
			else if(this.currBoardCreation.GetCurrentPhase(this.getCurrPlayer()) == BoardCreation.MOVEMENT_PHASE)
				return "move one of your pieces on the board";
			else if(this.currBoardCreation.GetCurrentPhase(this.getCurrPlayer()) == BoardCreation.REMOVAL_PHASE)
				return "remove one of your opponents pieces";
		}
		return "";
	}

	public boolean isMoving() {
		return this.moving;
	}
	
	public void setMoving(boolean mov){
		this.moving = mov;
	}

	public void setSelected(GamePieces_Creation p) {
		this.pieceSelected = p;
	}

	public void clearSelected() {
		this.pieceSelected = null;
		
	}
}