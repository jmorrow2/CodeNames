package event_handlers;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Location;
import code.Model;
import gui.GUI;
import main.Driver;

public class SelectActionListener implements ActionListener {
	
	/**GUI connected to this action listener*/
	private GUI g;
	
	/**Driver connected to this action listener*/
	private Driver d;
	
	/**Model connected to this action listener*/
	private Model m;
	
	/**Location connected to this action listener*/
	private Location l;
	
	/**
	 * @param gui - GUI
	 * @param drv - Driver
	 * @param md - Model
	 * @param loc - Location
	 * */
	public SelectActionListener(GUI gui, Driver drv, Model md, Location loc) {
		g = gui;
		d = drv;
		m = md;
		l = loc;
	}
	
	/**method that is called each time the guesser selects a codename
	 * calls selected method for the location and sets it equal to guess boolean
	 * calls guesserTurnRefresh so the gameboard and count are updated
	 * checks loser String in model to see if the player has just revealed an assassin and lost
	 * calls winningState from the model class to determine if the game is in a winning state. 
	 * if it is the displayWinningMessage method is called with the winners name as a parameter and the game restarts
	 * checks if the guess was incorrect or the count has decreased to 0. if so it calls switchTeamGUI()
	 * if nothing occurs the players turn continues
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		boolean guess = m.selected(l);
		g.guesserTurnRefresh();
		if(m.getLoser()!=null) {
			g.displayLosingMessage(m.getLoser());
			m.setLoser(null);
		}
		if(m.winningState()) {
			g.displayWinningMessage(m.getWinner());
			d.restart();
			d.run();
			return;
		}  
		if(!(guess) || m.getCount()==0) {
			g.switchTeamGUI();
			return;
		}
	}
}