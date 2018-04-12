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

public class RestartActionListener implements ActionListener {
	
	/**GUI connected to this action listener*/
	private Driver d;
	
	/**@param g - GUI
	 * */
	public RestartActionListener(Driver drv) {
		d = drv;
	}
	
	/**method that restarts the game
	 * calls restart() method on the Driver*/
	@Override
	public void actionPerformed(ActionEvent e) {
		d.restart();
	}
}