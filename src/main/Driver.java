package main;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.sun.glass.events.KeyEvent;

import code.Model;
import gui.GUI;

public class Driver implements Runnable {
	
	private Model _model;
	private JFrame _window;
	private JPanel _mainPanel;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem quit,restart;
	
	public Driver(Model m) {
		_model = m;
	}
	
	public static void main(String[] args) {
		Model m = new Model(5,5,9,8,7,1,"src/code/GameWords.txt");
		SwingUtilities.invokeLater(new Driver(m));
	}

	@Override
	public void run() {
		set_window(new JFrame("Codenames"));
		_mainPanel = new JPanel();
		get_window().getContentPane().add(_mainPanel);
		
		//file in header
//		menuBar = new JMenuBar();
//		menu = new JMenu("File");
//		menu.setMnemonic(KeyEvent.VK_F);
//		menuBar.add(menu);
//		_window.getContentPane().add(menuBar);
//		
//		quit = new JMenuItem("Quit Game");
//		restart = new JMenuItem("Restart game");
//		
//		menu.add(quit);
//		menu.add(restart);

		new GUI(_model, _mainPanel, this);
		
		get_window().setVisible(true);
		get_window().pack();
		get_window().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}
	
	public void updateJFrame() {
		get_window().pack();
		get_window().repaint();
	}

	/**
	 * @return the _window
	 */
	public JFrame get_window() {
		return _window;
	}

	/**
	 * @param _window the _window to set
	 */
	public void set_window(JFrame _window) {
		this._window = _window;
	}
}
