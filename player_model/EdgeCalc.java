package nine_mens_morris_game.player_model;

public class EdgeCalc implements Comparable<EdgeCalc>
{
	private String label;
	private LocationManager locationManager1;
	private LocationManager locationManager2;
	private int alignment;
	
	/***
	 * Default edge
	 */
	public EdgeCalc()
	{
		label = "NA";
		locationManager1 = null;
		locationManager2 = null;
		alignment = -1;
	}
	
	/***
	 * Constructor that creates an edge
	 * from the passed in parameters
	 * @param inLabel
	 * @param loc1
	 * @param loc2
	 * @param align
	 */
	public EdgeCalc(String inLabel, LocationManager loc1, LocationManager loc2, int align)
	{
		label = inLabel;
		locationManager1 = loc1;
		locationManager2 = loc2;
		alignment = align;
	}
	
	public EdgeCalc(EdgeCalc e) {
		// TODO Auto-generated constructor stub
	}

	/***
	 * Returns the edges label
	 * @return
	 */
	public String GetLabel()
	{
		return this.label;
	}
	
	/***
	 * Returns the Edges alignment
	 * @return
	 */
	public int GetAlignment()
	{
		return this.alignment;
	}
	
	/***
	 * Determines if the given
	 * Locations has a location
	 * @param loc
	 * @return
	 */
	public boolean HasLocation(LocationManager loc)
	{
		if (locationManager1 == loc || locationManager2 == loc)
			return true;
		else
			return false;
	}
	
	/***
	 * Returns the opposite LocationManager
	 * from the given LocationManager
	 * @param loc
	 * @return
	 */
	public LocationManager GetOpposite(LocationManager loc)
	{
		if (loc == locationManager1)
			return locationManager2;
		else if (loc == locationManager2)
			return locationManager1;
		else
			return null;
	}
	
	/***
	 * Compares the label of the edges for
	 * sorting
	 */
	@Override
	public int compareTo(EdgeCalc otherEdgeCalc) {
		return this.GetLabel().compareTo(otherEdgeCalc.GetLabel());
	}
	
	/***
	 * Returns a readable version of the edge for printing
	 */
	public String toString()
	{
		return String.format("%s[%s:(%s,%s)]", this.GetLabel(), (this.alignment == 0) ? "|" : "-", this.locationManager1.toString(), this.locationManager2.toString());
	}
}
