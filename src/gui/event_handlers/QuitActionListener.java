package gui.event_handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import code.Model;
import gui.GUI;

public class QuitActionListener implements ActionListener {
	private Model m;
	private GUI g;
	
	public QuitActionListener(GUI gui) {
	//	m = _model;
		g = gui;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		
//		Model m = new Model();
//		Driver d = new Driver(m);
//		get_window().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

	}

}
