package event_handlers;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.Driver;

public class QuitActionListener implements ActionListener {
	
	/**Driver connected to this action listener*/
	private Driver d;
	
	/**@param d - Driver
	 * */
	public QuitActionListener(Driver drv) {
		d = drv;
	}

	/**method that quits the game
	 * retrieves the window JFrame from the driver and calls dispose() on it*/
	@Override
	public void actionPerformed(ActionEvent e) {
		d.get_window().dispose();	
	}
}
