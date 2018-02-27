package code;

//import java.awt.Color;
//import java.awt.Graphics;

public class Board {
	
	public Location[] locArray;

	public Board() {
		locArray = new Location[25];
	}

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
