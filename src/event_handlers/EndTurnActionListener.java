package event_handlers;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import gui.GUI;

public class EndTurnActionListener implements ActionListener {
	
	/**GUI connected to this action listener*/
	private GUI g;
	
	/**@param gui - GUI
	 * */
	public EndTurnActionListener(GUI gui) {
		g = gui;
	}
	
	/**method that ends the guesser's turn voluntarily
	 * calls switchTeamGUI() method on the GUI
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		g.switchTeamGUI();
	}
}
