package code;

public class Location {
	
	/** codename assigned to the location instance */
	public String codename;
	/** agent assigned to the location instance */
	public String agent;
	/** boolean declaring whether or not the locations agent has been revealed yet */
	public boolean revealed;
	
	/**Instances of this class are used to represent a space on the game board
	 * 
	 * @param cd codename to be assigned to  the location instance
	 * @param a agent to be assigned to the location instance
	 */
	public Location(String cd, String a) {
		codename = cd;
		agent = a;
		revealed = false;
	}
}
