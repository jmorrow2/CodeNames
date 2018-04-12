package main;

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

public class Driver implements Runnable {
	
	/**Model belonging to the Driver*/
	private Model _model;
	
	/**JFrame belonging to the Driver that is the main window for the game*/
	private JFrame _window;
	
	/**JPanel for the Driver that is in the window JFrame*/
	private JPanel _mainPanel;
	
	/**@param m - Model
	 * */
	public Driver(Model m) {
		_model = m;
	}
	
	/**@param
	 * main method that creates the model for the game
	 * creates the Driver for the game as well*/
	public static void main(String[] args) {
		Model m = new Model(5,5,9,8,7,1,"src/GameWords.txt");
		SwingUtilities.invokeLater(new Driver(m));
	}
	
	/**statrs the game
	 * creates the window, mainPanel, and GUI for the game
	 * sets window propererties*/
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
	
	/**method that restarts the game by disposing the GUI JFrame
	 * calls setUP in model the set up a new game
	 * calls run to start a new game
	 * */
	public void restart() {
		_window.dispose();
		_model.setUp();
		run();
	}
	
	/**updates the JFrame by packing and repainting it*/
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
