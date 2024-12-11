package nine_mens_morris_game.player_model;

public class LocationManager implements Comparable<LocationManager> {
	
	private String label;
	private GamePieces_Creation piece;
	
	/***
	 * Default location constructor
	 */
	public LocationManager()
	{
		label = "NA";
		piece = null;
	}
	
	/***
	 * Creates a location with the passed
	 * in name
	 * @param name
	 */
	public LocationManager(String name)
	{
		label = name;
		piece = null;
	}

	public LocationManager(LocationManager l) {
		this.label = l.label;
	}

	/***
	 * returns the locations label
	 * @return
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * Determines if the location contains
	 * a piece
	 * @param inPiece
	 * @return
	 */
	public boolean ContainsPiece(GamePieces_Creation inPiece)
	{
		if (this.getPiece() == inPiece)
			return true;
		else
			return false;
		
	}
	
	/***
	 * Removes the piece from the location
	 */
	public void RemovePiece()
	{
		this.setPiece(null);
	}
	
	/***
	 * Changes the label of the location
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	public boolean equal(char Label){
		if(this.label.charAt(0) == Label)
			return true;
		return false;
	}

	/***
	 * Returns the piece at the location
	 * @return
	 */
	public GamePieces_Creation getPiece() {
		return piece;
	}

	/***
	 * Changes the piece at location
	 * to the passed in piece
	 * @param piece
	 */
	public void setPiece(GamePieces_Creation piece) {
		this.piece = piece;
	}

	/***
	 * Compares to locations
	 */
	@Override
	public int compareTo(LocationManager otherLoc) {
		return this.getLabel().compareTo(otherLoc.getLabel());
	}
	
	public String toString()
	{
		int id = (this.piece == null) ? -1 : this.piece.getID();
		return this.label + "<" + id + ">";
	}
	
	

}
