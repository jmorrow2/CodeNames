package code;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So

This class stores 25 location instances. 
*/

import java.util.ArrayList;


public class Board {
	
	/**An arrayList where the boards location instances are stored */
	private ArrayList<Location> locArray;
	
	/** Instance of the Board class is use to represent the game board 
	 *@param ArrayList of location instances 
	 **/
	public Board(ArrayList<Location> l) {
		locArray = l;
	}
	
	/**
	Getter Method for locArray ArrayList
	*/
	public ArrayList<Location> getLocArray() {
		return locArray;
	}
	
	/**
	Setter Method for locArray ArrayList
	*/

	public void setLocArray(ArrayList<Location> locArray) {
		this.locArray = locArray;
	}
}
