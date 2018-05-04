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
	
	/**model instance variable for the driver*/
	private Model _model;
	
	/**jframe instance variable for the  driver*/
	private JFrame _window;
	
	/**jpanel instance variable for the driver*/
	private JPanel _mainPanel;
	
	/**constructor  for the Driver
	 * @param Model m
	 * assigns Model m to the instance variable Model*/
	public Driver(Model m) {
		_model = m;
	}
	
	/**main method that creates the model and runs the program*/
	public static void main(String[] args) {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt"); //(rows, columns,red,blue, green,bystanders,assassins,file_) -- 2 Players
		SwingUtilities.invokeLater(new Driver(m));
	}

	/**creates the jframe, jpanel, and GUI
	 * sets properties for the jframe*/
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
	
	/**restarts the game by disposing the jframe and calling setUp on the model*/
	public void restart() {
		_window.dispose();
		_model.setUp();
	}
	
	/**updates the jframe by calling pack and repaint on the window*/  
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
