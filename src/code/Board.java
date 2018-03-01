package code;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So

This class creates 25 Location Instances. 
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
	Getter Metod for Array
	*/
	public ArrayList<Location> getLocArray() {
		return locArray;
	}
	
	/**
	Setter Metod for Array
	*/

	public void setLocArray(ArrayList<Location> locArray) {
		this.locArray = locArray;
	}

	//THIS IS FOR GUI.
	/**public void ColorGrid(Graphics colorScheme) { 
	        
			for ( int i = 0;  i < 5;  i++ ) {
				for ( int I = 0;  I < 5;  I++ ) {
					if (i + I % 2 == 1) {
						colorScheme.setColor(Color.BLUE);
						}
				
				else if (i + I % 2 == 0) {
					colorScheme.setColor(Color.RED);	
				}
					}
				}
			}*/
}
