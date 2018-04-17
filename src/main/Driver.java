package main;

import java.awt.event.ActionListener;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So
*/

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import code.Model;
import gui.GUI;
import event_handlers.threePlayerActionListener;
import event_handlers.twoPlayerActionListener;

public class Driver implements Runnable {
	
	private Model _model;
	private JFrame _window;
	private JPanel _mainPanel;

	
	public Driver(Model m) {
		_model = m;
	}
	
	public static void main(String[] args) {
	
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt"); //(rows, columns,red,blue, green,bystanders,assassins,file_) -- 2 Players
		SwingUtilities.invokeLater(new Driver(m));
	}

	@Override
	public void run() {
		_window = new JFrame("Codenames");
		_mainPanel = new JPanel();
		_window.getContentPane().add(_mainPanel);
		
		new GUI(_model, _mainPanel, this);
		
		_window.setVisible(true);
		_window.pack();
		_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public void restart() {
		_window.dispose();
		_model.setUp();
	}
	
	public void updateJFrame() {
		_window.pack();
		_window.repaint();
	}

	/**
	 * @return the _window
	 */
	public JFrame get_window() {
		return _window;
	}
}
