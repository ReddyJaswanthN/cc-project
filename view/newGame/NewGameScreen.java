package nine_mens_morris_game.view.newGame;

import javax.swing.JPanel;

import nine_mens_morris_game.player_model.user_category.AI_ComputerPlayer;
import nine_mens_morris_game.player_model.user_category.HumanPlayer;
import nine_mens_morris_game.view.MainUIWindow;

public class NewGameScreen extends JPanel{

	/**
	 * New game display
	 * for nine-mens-morris
	 */
	private static final long serialVersionUID = 2841820904085389198L;
	private MainUIWindow mw;
	private NewPVP pvp;
	private NewPVE pve;
	private Integer mode;
	private AI_ComputerPlayer computer;
	
	/***
	 * Constructor for NewGameScreen
	 * 
	 * Determines if game is PVE or PVP
	 * from passed in mode
	 * 
	 * Creates AI for Game if needed
	 * @param mw
	 * @param mode
	 */
	public NewGameScreen(MainUIWindow mw, Integer mode){
		this.setSize(700,700);
		this.mw = mw;
		this.mode = mode;
		if(mode == 0){
			this.pve = new NewPVE(this);
			this.add(this.pve);
		}
		else{
			this.pvp = new NewPVP(this);
			this.add(this.pvp);
		}
		this.setVisible(true);
		this.computer = new AI_ComputerPlayer("Computer", "Black");
	}
	
	/***
	 * Returns the mainWindow object
	 * to the NewPVP and NewPVE classes
	 * 
	 * Used only to print dialog to main_Entry screen
	 * @return
	 */
	public MainUIWindow getMainWindow(){
		return this.mw;
	}
	
	/***
	 * Will start the game after
	 * receiving good input from the user_category
	 */
	public void startGame(){
		if(this.mode == 0){
			HumanPlayer p1 = new HumanPlayer(this.pve.getP1Name(), this.pve.getP1Color());
			this.mw.startGame(p1, this.computer, this.mode);
		}
		else if(this.mode == 1){
			HumanPlayer p1 = new HumanPlayer(this.pvp.getP1Name(), this.pvp.getP1Color());
			HumanPlayer p2 = new HumanPlayer(this.pvp.getP2Name(), this.pvp.getP2Color());
			this.mw.startGame(p1, p2, this.mode);
		}	
	}

	/**
	 * Will return the AI component of the game
	 * It is created earlier to avoid color clashes
	 * with the player
	 * @return
	 */
	public AI_ComputerPlayer getComputer() {
		return computer;
	}
}
