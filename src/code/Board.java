package code;

//import java.awt.Color;
//import java.awt.Graphics;

public class Board {
	private Model m;
	
	public Location[] locArray;

//		public Board() {
//	for(int i =0; i<25; i++) {
//		locArray[i] = new Location[i];     	// We have to make 25 Location instances
											// and assign 2 strings and a boolean to each array
											// still trying to figure that out. -victoria


	
	//this is just creating 1 instance of location with a length of 25??
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
