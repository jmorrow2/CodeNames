package event_handlers;


import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;

import javax.swing.SwingUtilities;

import code.Model;
import main.Driver;

/**
 * @author Victoria Dib
 * @author James Morrow
 * @author Harsh Patel
 * @author Jason Zhou
 * @author Kimberly So
 */

public class twoPlayerActionListener implements ActionListener {
	/**GUI connected to this action listener*/
	private Driver d;
	

	/**@param g - GUI
	 * */
	public twoPlayerActionListener(Driver drv) {
		d = drv;
	}
	
	/**
	 * method that will restart the game with two players by calling methods made in the driver.
	 */
	
	@Override
	public void actionPerformed(ActionEvent e) {
		d.restart();	 
		
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt"); //(rows, columns,red,blue, green,bystanders,assassins,file_) -- 2 Players

		SwingUtilities.invokeLater(new Driver(m));
		
		
	}
}
