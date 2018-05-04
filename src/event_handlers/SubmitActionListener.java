package event_handlers;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Model;
import gui.GUI;

public class SubmitActionListener implements ActionListener {
	
	/**GUI connected to this action listener*/
	private GUI g;
	
	/**Model connected to this action listener*/
	private Model m;
	
	/**@param gui - GUI
	 * @param md - Model
	 * */
	public SubmitActionListener(GUI gui, Model md) {
		g = gui;
		m = md;
	}
	
	/**method that handles when the spymaster submits and clue and count
	 * retrieves clue from the GUI's JTextField and sets it equal to clue string 
	 * retrieves count from the GUI's JTextField and sets it equal to count string 
	 * determined if clue equals ("Easter Egg") if so then the easter egg method in GUI is triggered and clear text fields. end method
	 * checks if clue or count are empty. if so displays illegal message and clear text fields. end method
	 * turns the clue uppercase and calls clueCheck in model to be checked, if legal sets the model's clue string to the clue. if illegal displays illegal message and clear text fields. end method
	 * makes sure count is a positive int by trying to parse count text to an int and if it is positive set it to the models count variable. if not able displays illegal message and clear text fields. end method
	 * emptys textfields by callin GUI method clearTextFields()
//	 * switches from the spymaster turn to the guesser turn by calling GUI method switchSpyGuesserGUI()
	 * */
	@Override
	public void actionPerformed(ActionEvent e) {
		String clue = g.getClueText().getText();
		String count = g.getCountText().getText();
		if(clue.equals("EASTER EGG")){
			g.easterEgg();
			g.clearTextFields();
			return;
		}
		if(clue.equals("I WIN!!!")) {
			g.displayWinningMessage(m.getCurrentTeam());
			g.getWindowHolder().restart();
			g.getWindowHolder().run();
		return;
		}
		if(clue.equals("") || count.equals("")) {
			g.displayIllegalMessage();
			g.clearTextFields();
			return;
		}
		if(m.clueCheck(clue.toUpperCase())) {
			m.setClue(clue);
		}else {
			g.displayIllegalMessage();
			g.clearTextFields();
			return;
		}
		
		try {
			int countInt = Integer.parseInt(count);
			if(countInt>0) {
				m.setCount(countInt);
			} else {
				g.displayIllegalMessage();
				g.clearTextFields();
				return;
			}
		} catch(Exception ex){
			g.displayIllegalMessage();
			g.clearTextFields();
			return;
		}
		
		g.clearTextFields();
		g.switchSpyGuesserGUI();
	}
}