package event_handlers;


import java.awt.event.ActionEvent;


import java.awt.event.ActionListener;
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
	
	/**method that restarts the game
	 * calls game to restart w/ 2 players method on the Driver*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		d.restart();	
		d.run();
		

		
	}
}
