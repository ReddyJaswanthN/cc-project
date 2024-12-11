package nine_mens_morris_game.player_model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.JOptionPane;

import nine_mens_morris_game.player_model.user_category.HumanPlayer;
import nine_mens_morris_game.view.MainUIWindow;

public class BoardCreation {

	// Class variables
	private ArrayList<LocationManager> location_Manager_list;
	private ArrayList<EdgeCalc> edge_Calc_list;
	private GamePieces_Creation[][] boardArray;
	private MainUIWindow mw;
	private int current_phase;
	private boolean cheatMode;
	private boolean ignoreMessages;

	// Game phases
	public static final int GAMEOVER_PHASE = -1;
	public static final int PLACEMENT_PHASE = 0;
	public static final int MOVEMENT_PHASE = 1;
	public static final int REMOVAL_PHASE = 2;

	// Number to letter array.
	public static final char[] ALPHABET = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X'};
	public static final String[] BOARDREFERENCE = {"0,0","0,3","0,6","1,1","1,3","1,5","2,2","2,3","2,4","3,0","3,1","3,2","3,4","3,5","3,6","4,2","4,3","4,4","5,1","5,3","5,5","6,0","6,3","6,6"};

	/***
	 * This is a copy constructor
	 * which should return make
	 * a copy of the passed in BoardCreation
	 * object
	 *
	 * Needs fixed as equalizing the
	 * lists does not change the reference
	 * @param bd
	 */
	public BoardCreation(BoardCreation bd){
		this.location_Manager_list = new ArrayList<LocationManager>();
		this.edge_Calc_list = new ArrayList<EdgeCalc>();
		this.boardArray = new GamePieces_Creation[7][7];
		this.current_phase = bd.current_phase;
		this.cheatMode = bd.cheatMode;
		this.mw = bd.mw;

		copyLocList(bd);
		copyEdgList(bd);
		copyBoardAr(bd);
	}

	private void copyBoardAr(BoardCreation bd) {
		for(int i=0; i<7; i++){
			for(int j=0; j<7; j++){
				this.boardArray[i][j] = new GamePieces_Creation(bd.boardArray[i][j]);
			}
		}
	}

	private void copyEdgList(BoardCreation bd) {
		for(LocationManager l: bd.location_Manager_list){
			this.location_Manager_list.add(new LocationManager(l));
		}
	}

	private void copyLocList(BoardCreation bd) {
		for(EdgeCalc e: bd.edge_Calc_list){
			this.edge_Calc_list.add(new EdgeCalc(e));
		}
	}

	/***
	 * The normal constructor for the
	 * BoardCreation class.  The MainUIWindow param
	 * is used to send updates to the user_category
	 * from inside this class
	 * @param mw
	 */
	public BoardCreation(MainUIWindow mw){
		// Load the edge and location list from file
		location_Manager_list = new ArrayList<LocationManager>();
		edge_Calc_list = new ArrayList<EdgeCalc>();
		this.LoadBoard();

		// Sort the location list for printing the visual.
		Collections.sort(location_Manager_list);

		this.current_phase = PLACEMENT_PHASE;
		this.boardArray = newBoard();
		this.cheatMode = false;
		this.mw = mw;
	}

	/***
	 * This function will update the board
	 * with the current pieces on it
	 *
	 * As long as they are not null it will
	 * update with the current locations piece
	 */
	public void updateBoard(){
		for(int i=0; i<24; i++){
			String t[] = BOARDREFERENCE[i].split(",");
			int row = Integer.parseInt(t[0]);
			int col = Integer.parseInt(t[1]);
			String label = String.valueOf(ALPHABET[i]);
			LocationManager loc = this.GetLocationByLabel(label);
			if(loc.getPiece() != null)
				this.boardArray[row][col] = loc.getPiece();
			else
				this.boardArray[row][col] = new GamePieces_Creation(new HumanPlayer("", "red").getColor(), new HumanPlayer("", "red"), -1);
		}
	}

	/***
	 * Will return the current
	 * state of the board Array
	 * @return
	 */
	public GamePieces_Creation[][] getBoardArray() {
		return this.boardArray;
	}


	/***
	 * This function will determine the
	 * current phase of the game
	 *
	 * If it is still in placement, removal,
	 * endGame of simply currentPhase
	 *
	 * @param curplayer
	 * @return
	 */
	public int GetCurrentPhase(HumanPlayer curplayer) {
		if(!curplayer.isHuman()){
			this.ignoreMessages = true;
		}
		else
			this.ignoreMessages = false;
		if (curplayer.getPiecesPlayed() < 9)
			return PLACEMENT_PHASE;

		// See if phase has been set to REMOVAL
		else if (current_phase == REMOVAL_PHASE || current_phase == GAMEOVER_PHASE){
			return current_phase;
		}

		// Only other option is movement phase.
		else
			return MOVEMENT_PHASE;
	}


	/***
	 * This function will turn on
	 * cheat mode for the users
	 */
	public void setCheatMode(){
		this.cheatMode = true;
	}

	/***
	 * This function will set the phase
	 * of the game to the passed in phase
	 * @param current_phase
	 */
	public void SetCurrentPhase(int current_phase) {
		this.current_phase = current_phase;
	}

	/***
	 * This function will return a location
	 * object with the given label
	 * @param label
	 * @return
	 */
	public LocationManager GetLocationByLabel(String label)
	{
		for(int i = 0; i < location_Manager_list.size(); i++)
			if (location_Manager_list.get(i).getLabel().equals(label))
				return location_Manager_list.get(i);

		return null;
	}

	/***
	 * This method created an initial board for
	 * the GUI to use.  The place-able Pieces are
	 * initialized to a new piece with an ID of
	 * -1 so they are not drawn on the board.
	 *
	 * Invalid places on the board (along the lines
	 * in empty spaces) are set to null
	 * @return
	 */
	private GamePieces_Creation[][] newBoard() {


		GamePieces_Creation[][] bd = new GamePieces_Creation[7][7];
		for(int i=0; i<7; i++)
			for(int j=0; j<7; j++){
				HumanPlayer p = new HumanPlayer("", "red");
				bd[i][j] = new GamePieces_Creation(p.getColor(), p, -1);
			}
		ArrayList<ArrayList<Integer>> notPlaceableSpots = validSpots();
		int k = 0;
		for(ArrayList<Integer> i: notPlaceableSpots){
			for(Integer j: i){
				bd[k][j] = null;
			}
			k++;
		}

		return bd;
	}

	/***
	 * This is a hard coded function to set
	 * the invalid spots of the game BoardCreation.
	 *
	 * The spots correlate to lines on the board
	 * and empty spots where a place can not be placed
	 * @return
	 */
	private ArrayList<ArrayList<Integer>> validSpots() {
		ArrayList<ArrayList<Integer>> p = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> row0 = new ArrayList<Integer>(Arrays.asList(1,2,4,5));
		ArrayList<Integer> row1 = new ArrayList<Integer>(Arrays.asList(0,2,4,6));
		ArrayList<Integer> row2 = new ArrayList<Integer>(Arrays.asList(0,1,5,6));
		ArrayList<Integer> row3 = new ArrayList<Integer>(Arrays.asList(3));
		ArrayList<Integer> row4 = new ArrayList<Integer>(Arrays.asList(0,1,5,6));
		ArrayList<Integer> row5 = new ArrayList<Integer>(Arrays.asList(0,2,4,6));
		ArrayList<Integer> row6 = new ArrayList<Integer>(Arrays.asList(1,2,4,5));

		p.add(row0);
		p.add(row1);
		p.add(row2);
		p.add(row3);
		p.add(row4);
		p.add(row5);
		p.add(row6);
		return p;
	}

	/**************************************
	 * Gameplay Related Methods
	 **************************************/
	public boolean newMessageDialog(String error, int delay){
		if(this.ignoreMessages && delay != 5000)
			return false;
		this.mw.getGameBoard().setError(error, delay);
		this.mw.getGameBoard().startTimer();
		return false;
	}

	/***
	 * This function will attempt
	 * to place a piece on the board
	 *
	 * As long as the space is not occupied
	 * and the piece is valid it will return true
	 * @param humanPlayer
	 * @param pieceID
	 * @param locLabel
	 * @return
	 */
	public boolean PlacePiece(HumanPlayer humanPlayer, int pieceID, String locLabel)
	{
		GamePieces_Creation curPiece = humanPlayer.getPiece(pieceID);
		LocationManager newLoc = GetLocationByLabel(locLabel);

		if (curPiece == null || newLoc == null)
		{
			return newMessageDialog("Invalid piece id or location label", 1500);
		}

		// Make sure piece selection isn't placed already.
		if (curPiece.getStatus() != GamePieces_Creation.UNPLACED)
		{
			return newMessageDialog("Invalid Piece - It is already placed or dead", 1500);
		}


		// Make sure the location is empty
		if (!newLoc.ContainsPiece(null))
		{
			return newMessageDialog("There is a piece there already", 1500);
		}

		// We're ok to place the piece.
		newLoc.setPiece(curPiece);
		curPiece.setStatus(GamePieces_Creation.PLACED);
		return true;

	}

	/***
	 * This function will attempt to
	 * move a piece on the board
	 *
	 * As long as the space is not occupied
	 * and the piece is the current users and
	 * is valid it will return true
	 * @param humanPlayer
	 * @param pieceID
	 * @param locLabel
	 * @return
	 */
	public boolean MovePiece(HumanPlayer humanPlayer, int pieceID, String locLabel)
	{
		GamePieces_Creation curPiece = humanPlayer.getPiece(pieceID);
		LocationManager curLoc = GetPieceLocation(curPiece);
		LocationManager newLoc = GetLocationByLabel(locLabel);

		if (curPiece == null || newLoc == null)
		{
			return newMessageDialog("Invalid piece id or location label", 1500);
		}

		// Detect if we are in fly mode. If not, make sure we're adjacent.
		// Make sure the locations are neighbors.
		if (!this.cheatMode && humanPlayer.getScore() > 3 && !AreNeighbors(curLoc, newLoc))
		{
			return newMessageDialog("That spot is not adjacent", 1500);
		}

		// Make sure the location is empty
		if (!newLoc.ContainsPiece(null))
		{
			return newMessageDialog("There is a piece there already", 1500);
		}

		// We're ok to move the piece.
		curPiece.setStatus(GamePieces_Creation.MOVED);
		curPiece.setMv(new PiecesMovement(curLoc, newLoc));
		curPiece.setMoving(true);

		newLoc.setPiece(curPiece);
		curLoc.setPiece(null);

		// Check for a created mill.
		if (IsMill(newLoc))
		{
			// We will return false so current humanPlayer is not nexted.
			// Set current phase to removal phase.
			String s = "%s has created a mill!";
			s = String.format(s, humanPlayer.getName());
			this.newMessageDialog(s, 5000);
			//JOptionPane.showMessageDialog(this.mw, s);
			this.SetCurrentPhase(REMOVAL_PHASE);
			return false;
		}

		return true;

	}

	/***
	 * This function will attempt to
	 * remove a piece from the board
	 *
	 * As long as their is a piece at
	 * that location, and it is the opponents
	 * it will return true
	 * @param humanPlayer
	 * @param pieceID
	 * @return
	 */
	public boolean RemovePiece(HumanPlayer humanPlayer, int pieceID, boolean valid)
	{
		GamePieces_Creation curPiece = humanPlayer.getPiece(pieceID);
		LocationManager curLoc = GetPieceLocation(curPiece);

		if(!valid)
			return newMessageDialog("You cannot remove your own piece", 1500);


		if (curPiece == null)
		{
			return newMessageDialog("Invalid Piece ID - Piece not found", 1500);
		}

		if (!curPiece.inPlay())
		{
			return newMessageDialog("Invalid Piece - It is not placed or alive/in play", 1500);

		}

		if (humanPlayer.getScore() > 3 && IsMill(curLoc))
		{
			return newMessageDialog("You cannot remove a member of a mill", 1500);

		}


		curLoc.setPiece(null);
		curPiece.setStatus(GamePieces_Creation.DEAD);
		// Grab the humanPlayer's new calculated score.
		int score = humanPlayer.getScore();

		if (score == 3 && !this.cheatMode){
			String s = "%s has engaged fly mode with 3 pieces remaining";
			s = String.format(s, humanPlayer.getName());
			JOptionPane.showMessageDialog(this.mw, s);
		}

		// See if the humanPlayer has less than 3 pieces remaining, that leads to gameover phase.
		// if not set the phase to movement phase.
		if(score < 3)
		{
			SetCurrentPhase(GAMEOVER_PHASE);
			// We will return false so we crown the correct victor
			// We would stay on this humanPlayer otherwise, because this method was
			// passed the opposite humanPlayer of the current humanPlayer to remove his piece.
			return false;
		} else
			SetCurrentPhase(MOVEMENT_PHASE);

		return true;

	}

	/***
	 * This function will determine if a mill is
	 * created from LocationManager passed into it
	 * @param loc
	 * @return
	 */
	public boolean IsMill(LocationManager loc)
	{
		int vertCount = CountAdjacent(loc, 0);
		int horizCount = CountAdjacent(loc, 1);

		if (Math.max(vertCount, horizCount) > 2)
			return true;
		else
			return false;
	}

	/***
	 * This function will count
	 * the number of adjacent pieces
	 * to the passed in location
	 * @param loc
	 * @param dir
	 * @return
	 */
	public int CountAdjacent(LocationManager loc, int dir)
	{
		HumanPlayer owner = loc.getPiece().getOwner();
		int status1, status2;

		ArrayList<LocationManager> nghbrs = SomeNeighbors(loc, dir, owner);
		if (nghbrs.size() == 2)
		{
			// Store the neighbors status's to make sure at least one has moved (req to become a mill)
			status1 = nghbrs.get(0).getPiece().getStatus();
			status2 = nghbrs.get(1).getPiece().getStatus();

		} else if (nghbrs.size() == 1) {
			status1 = nghbrs.get(0).getPiece().getStatus();

			// See if the neighbor has another adjacent neighbor.
			nghbrs = SomeNeighbors(nghbrs.get(0), dir, owner);
			nghbrs.remove(loc);

			if(nghbrs.size() == 1)
			{
				status2 = nghbrs.get(0).getPiece().getStatus();
			} else
				return 0;
		} else
			return 0;

		// Make sure at least one piece of the mill has moved.
		if (status1 == GamePieces_Creation.MOVED || status2 == GamePieces_Creation.MOVED || loc.getPiece().getStatus() == GamePieces_Creation.MOVED)
			return 3;
		else
			return 0;

	}

	/***
	 * This function will determine
	 * if two locations are neighbors
	 * @param loc1
	 * @param loc2
	 * @return
	 */
	public boolean AreNeighbors(LocationManager loc1, LocationManager loc2)
	{
		ArrayList<LocationManager> all_neighbors = AllNeighbors(loc1);
		if (all_neighbors.contains(loc2))
			return true;
		else
			return false;
	}

	/***
	 *  Returns locations with a defined direction and a owner that
	 *  are neighbors to the supplied location.
	 */
	public ArrayList<LocationManager> SomeNeighbors(LocationManager loc, int dir, HumanPlayer owner)
	{
		ArrayList<LocationManager> NeighborList = new ArrayList<LocationManager>();
		EdgeCalc curEdgeCalc;
		LocationManager oppLoc;

		// Find all edges that hold this location
		for (int i = 0; i < edge_Calc_list.size(); i++)
		{
			curEdgeCalc = edge_Calc_list.get(i);
			oppLoc = curEdgeCalc.GetOpposite(loc);

			// If the edge has the location, add the adjacent location to the list.
			// Make sure it matches directional and player owned conditions.
			if (curEdgeCalc.HasLocation(loc) && curEdgeCalc.GetAlignment() == dir)
			{
				// If we want the owner to be null (aka no piece located there),
				// add the location if it holds no piece.
				if(owner == null && oppLoc.getPiece() == null)
					NeighborList.add(curEdgeCalc.GetOpposite(loc));
				// We want there to be a piece in the location owned by a particular owner.
				else if(oppLoc.getPiece() != null && oppLoc.getPiece().getOwner() == owner)
					NeighborList.add(curEdgeCalc.GetOpposite(loc));

			}
		}

		return NeighborList;
	}

	/***
	 * This function will return a list
	 * of all the neighbors to a given LocationManager
	 * @param loc
	 * @return
	 */
	public ArrayList<LocationManager> AllNeighbors(LocationManager loc)
	{
		ArrayList<LocationManager> NeighborList = new ArrayList<LocationManager>();

		// Find all edges that hold this location
		for (int i = 0; i < edge_Calc_list.size(); i++)
		{
			// If the edge has the location, add the adjacent location to the list.
			if (edge_Calc_list.get(i).HasLocation(loc))
				NeighborList.add(edge_Calc_list.get(i).GetOpposite(loc));
		}

		return NeighborList;
	}

	/***
	 * This function will return the location of the given piece
	 * or return null if not found
	 * @param piece
	 * @return
	 */
	public LocationManager GetPieceLocation(GamePieces_Creation piece)
	{
		for (int i = 0; i < location_Manager_list.size(); i++)
			if (location_Manager_list.get(i).ContainsPiece(piece))
				return location_Manager_list.get(i);

		return null;
	}


	/**************************************
	 * Gameboard Creation Related Methods
	 **************************************/

	/***
	 * This function will add a location
	 * given the passed in label
	 * @param label
	 * @return
	 */
	private LocationManager AddLocation(String label)
	{
		// First see if there is already a location with this label.
		LocationManager newLocationManager = GetLocationByLabel(label);

		// If null, create a new one and add it to the list.
		if (newLocationManager == null)
		{
			newLocationManager = new LocationManager(label);
			this.location_Manager_list.add(newLocationManager);
		}

		// Return either old location or the newly created one.
		return newLocationManager;
	}

	/***
	 * This function will add an
	 * edge between the two Locations,
	 * give it a label, and its alignment
	 * @param label
	 * @param loc1
	 * @param loc2
	 * @param align
	 */
	private void AddEdge(String label, LocationManager loc1, LocationManager loc2, int align)
	{
		EdgeCalc newEdgeCalc = new EdgeCalc(label, loc1, loc2, align);
		edge_Calc_list.add(newEdgeCalc);
	}

	/***
	 * This function will load the gameboard
	 * from a .txt file to create the
	 * location lists and edge lists
	 */
	private void LoadBoard()
	{
		BufferedReader br = null;
		String curLine = "";
		String tokens[], locations[];
		LocationManager newLoc1, newLoc2;

		try
		{
			br = new BufferedReader(new FileReader("resources/board.txt"));

			// Load in each line of the file, creating new edges
			// and locations as they are found.
			// Input line format: <EdgeLabel>:<LocLabel1>,<LocLabel2>:<Orientation[0=vert;1=horiz]>
			while ( (curLine = br.readLine()) != null)
			{
				// Split edge label from location labels
				tokens = curLine.split(":");

				// Split the location elements apart.
				locations = tokens[1].split(",");

				// Get the new locations connected by the edge.
				// AddLocation will reference current locs if existant already.
				newLoc1 = AddLocation(locations[0]);
				newLoc2 = AddLocation(locations[1]);

				AddEdge(tokens[0], newLoc1, newLoc2, Integer.parseInt(tokens[2]));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/***
	 * This function will count
	 * the number of moves available for
	 * a given humanPlayer
	 * @param humanPlayer
	 * @return
	 */
	public int numMovesAvailable(HumanPlayer humanPlayer)
	{
		LocationManager curloc;
		int count = 0;

		for(int i = 0; i < humanPlayer.getPieces().size(); i++)
		{
			curloc = GetPieceLocation(humanPlayer.getPiece(i));
			// Is this piece in a gameboard location?
			if (curloc != null)
			{
				// Add to count the amount of neighbors that hold no pieces (null owner)
				count += SomeNeighbors(curloc, 0, null).size();
				count += SomeNeighbors(curloc, 1, null).size();
			}
		}

		return count;
	}

	public void setPiece(int r, int c, GamePieces_Creation gp) {
		if(gp == null)
			this.boardArray[r][c] = null;
		else
			this.boardArray[r][c] = gp;
	}

	public boolean isFlyMode(HumanPlayer p) {
		int score = p.getScore();

		if (score == 3 || this.cheatMode){
			return true;
		}
		return false;
	}

}
