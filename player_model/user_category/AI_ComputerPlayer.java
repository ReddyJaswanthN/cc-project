package nine_mens_morris_game.player_model.user_category;

import java.util.Random;

import nine_mens_morris_game.Players_Creation_controller.nine_mens_morris_gameGameModel;
import nine_mens_morris_game.player_model.BoardCreation;
import nine_mens_morris_game.player_model.GamePieces_Creation;
import nine_mens_morris_game.player_model.LocationManager;

public class AI_ComputerPlayer extends HumanPlayer {
	
	public static final String[] colors = {"Black", "Red", "Blue", "Gray", "Green"};
	private nine_mens_morris_gameGameModel nine_mens_morris_game;
	public static Random R;
	/***
	 * Default constructor for an
	 * AI player
	 * @param name
	 * @param color
	 */
	public AI_ComputerPlayer(String name, String color) {
		super(name, chooseRandomColor(color));
		
	}
	
	/***
	 * Will choose a random color for the AI
	 * @param color
	 * @return
	 */
	private static String chooseRandomColor(String color) {
		R = new Random();
		int i = R.nextInt() % 5;
		while(i < 0 || i > 4)
			i = R.nextInt() % 5;
		if(AI_ComputerPlayer.colors[i] != null){
			return AI_ComputerPlayer.colors[i].toLowerCase();
		}
		return color;
	}
	
	
	public String newMove(){
		return "A";
	}
	

	/**
	 * Will be in used in PVE mode
	 * 
	 * Tells the player_model that this is a
	 * computer
	 */
	@Override
	public boolean isHuman(){
		return false;
	}

	public boolean placeMove() {
		int i = R.nextInt() % 24;
		while(i < 0 || i > 24){
			i = R.nextInt() % 24;
		}
		char move = BoardCreation.ALPHABET[i];
		while(!this.nine_mens_morris_game.newMove(String.valueOf(move))){
			i = R.nextInt() % 24;
			while(i < 0 || i > 24){
				i = R.nextInt() % 24;
			}
			move = BoardCreation.ALPHABET[i];;
		}
		return true;
	}

	public boolean moveMove() {		
		for(GamePieces_Creation p : this.getPieces()){
			LocationManager t = this.nine_mens_morris_game.getBoard().GetPieceLocation(p);

			for(char lab: BoardCreation.ALPHABET){
				LocationManager newLoc = this.nine_mens_morris_game.getBoard().GetLocationByLabel(String.valueOf(lab));
				if(t == newLoc)
					continue;
				if(!newLoc.ContainsPiece(null))
					continue;
				if(this.nine_mens_morris_game.getBoard().AreNeighbors(t, newLoc)){
					this.nine_mens_morris_game.setSelected(p);
					if(this.nine_mens_morris_game.newMove(newLoc.getLabel())){
						return true;
					}
					else{
						this.nine_mens_morris_game.clearSelected();
					}
				}
				
			}
		}
		return false;
	}

	public boolean remoMove() {
		HumanPlayer p = this.nine_mens_morris_game.getPlayer1();
		for(GamePieces_Creation gp: p.getPieces()){
			if(gp.IsAlive() && gp.getStatus() != GamePieces_Creation.UNPLACED){
				if(this.nine_mens_morris_game.newMove(this.nine_mens_morris_game.getBoard().GetPieceLocation(gp).getLabel())){
					return true;
				}
			}
		}
		return false;
	}

	public void setnine_mens_morris_game(nine_mens_morris_gameGameModel nine_mens_morris_game) {
		this.nine_mens_morris_game = nine_mens_morris_game;
	}

	public nine_mens_morris_gameGameModel getnine_mens_morris_game() {
		return nine_mens_morris_game;
	}

}
