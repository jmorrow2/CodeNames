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

public class threePlayerActionListener implements ActionListener {
	/**GUI connected to this action listener*/
	private Driver d;
	
	private Model m;


	
	/**@param g - GUI
	 * */
	public threePlayerActionListener(Driver drv) {
		d = drv;
	}
	
	/**method that restarts the game
	 * calls game to restart with 3 players method on the Driver*/
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		d.restart();
		
		m = new Model(5,5,6,5,5,7,2,"src/GameWords.txt"); //(rows, columns,red,blue,green,bystanders,assassins,file_) -- 3 Players

		SwingUtilities.invokeLater(new Driver(m));


	}

		
	}

