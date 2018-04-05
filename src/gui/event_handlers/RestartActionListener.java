package gui.event_handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Board;
import code.Model;
	
	/*
	 * @author Victoria Dib
	 * @author
	 * @author
	 * @author
	 * 
	 * 
	 * This class implements function to the restart button within the game.
	 */
        
public class RestartActionListener implements ActionListener {
	private Model m;
	
	public RestartActionListener(Model _model) {
		m = _model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		m.restart();
	}
}
