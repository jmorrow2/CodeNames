package code;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So
*/

public class Board {
	
	/**An arrayList where the boards location instances are stored */
	private Location[][] locArray;
	
	/** Instance of the Board class is use to represent the game board 
	 *@param ArrayList of location instances 
	 **/
	public Board(Location[][] l) {
		locArray = l;
	}
	
	/**
	Getter Method for locArray
	*/
	public Location[][] getLocArray() {
		return locArray;
	}
	
	/**
	Setter Method for locArray
	*/

	public void setLocArray(Location[][] locArray) {
		this.locArray = locArray;
	}
}