package code;

import java.util.ArrayList;

public class Board {
	
	public ArrayList<Location> locArray;

	public Board(ArrayList<Location> l) {
		locArray = l;
	}
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
