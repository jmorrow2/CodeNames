package gui.event_handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import code.Model;

public class QuitActionListener implements ActionListener {
	private Model m;
	
	public QuitActionListener(Model _model) {
		m = _model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.exit(0);
	}

}
