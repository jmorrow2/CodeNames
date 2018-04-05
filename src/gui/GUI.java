package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import com.sun.glass.events.KeyEvent;

import code.Location;
import code.Model;
import code.Observer;
import gui.event_handlers.QuitActionListener;
import gui.event_handlers.RestartActionListener;
import main.Driver;


public class GUI implements Observer {
	
	private Model _model;
	private ArrayList<Location> _location;
	private Driver _windowHolder;
	private JButton[][] locations;
	private JPanel _body; //holds codenames
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem quit,restart;
	private JLabel spymasterLabel;
	private JPanel spyMasterPanel;

	
	public GUI(Model m, JPanel mp, Driver driver) {
		_model = m;
		_windowHolder = driver;
	
		JPanel _mainPanel = mp;

		//creates new JMenu bar
		menuBar = new JMenuBar();

		//Build the first menu.
		menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription(
		        "The only menu in this program that has menu items");
		menuBar.add(menu);
		

		//your menu items
		restart = new JMenuItem("Restart Game",
		                         KeyEvent.VK_R);
		restart.addActionListener(new RestartActionListener(_model));


		quit = new JMenuItem("Quit Game", KeyEvent.VK_Q);
		quit.addActionListener(new QuitActionListener(_model));
		
		menu.add(quit);
		menu.add(restart);

		driver.get_window().setJMenuBar(menuBar);

			
		
		_mainPanel.setLayout(new BoxLayout(_mainPanel, BoxLayout.Y_AXIS));
			_body = new JPanel(); //holds all Locations to be found
			_body.setLayout(new GridLayout(_model.getRow(),_model.getColumn()));
			
			_mainPanel.add(_body);
		
		JPanel spyMaster = new JPanel(); //bullet6
		//turn begins with a message dialog w/ whose turn it is
		spyMasterPanel = new JPanel();
		spymasterLabel = new JLabel("It is red team's turn.");
		setLabelProperties(spymasterLabel);
//		spymasterLabel.setBackground(Color.BLACK);
//		spymasterLabel.setForeground(Color.RED); //font color

//	spyMaster.setBackground(Color.RED);
		spyMasterPanel.add(spymasterLabel);
		
		_mainPanel.add(spyMasterPanel);
		
		//spymaster can enter a clue & submit it to the system
		
		//revealed locations only display the blue/red agent/assassin.bystander assigned to them
		
		//the gui component used to enter a count only allows legal counts to be entered
		
		//if an illegal clue is entered, an error message is shown and the spymaster is allowed to enter another clue
		
		//if a legal clue is entered, play moves to part of the turn where the rest of the team selects locations to be revealed.
		
		
//		int n = JOptionPane.showConfirmDialog(driver.get_window(), "Quit Game", "Are you sure you want to quit?",JOptionPane.YES_NO_OPTION);
//		Object[] options = {"Yes", "Take me back!"};
//		
		
		
		
		
	
		
	 _model.createBoard(_location);
	 _model.addObserver(this);		 
		
	}
	
	@Override
	public void update() {
		ArrayList<Location> agents = _model.createLocationsArray();
		for(int i =0; i<agents.size();i++) {
			JButton b = new JButton(""+ _model.getCodenamesArray().get(i));
			//setButtonProperties(b);
			setButtonProperties(b);
			_body.add(b);
		}
		
//		ArrayList<Location> guess = _model.getLocArray();
//	for(String s : _model.getCodenamesArray()) {
//		if(_model.clueCheck(s) == false) { //
//			for(int i; i < ) {
//			_model.selected(s.equals()));
//			JButton a = new JButton("Legal move"+ _model.getCount() + "possible");
//			int x = _model.getCount();
//			}
//		}
		
//		
		if(_model.getBlueTurn()) {
			JOptionPane.showMessageDialog(_body, "It is the blue teams turn.");
			spymasterLabel.setBackground(Color.BLUE);
		}
		if(_model.getRedTurn()) {
			
			JOptionPane.showMessageDialog(spyMasterPanel, "Welcome to Codenames.\n The game begins with the Red Spymaster's turn. \n (we can remove this or make it say something cool.)");
		//	spymasterLabel.setBackground(Color.RED);
			
		}
//		
//		if(_model.clueCheck(c)
//		
		
		updateJFrameIfNotHeadless();
	}

	private void updateJFrameIfNotHeadless() {
		if(_windowHolder != null) {
			_windowHolder.updateJFrame();
		}
	}
	
	//if we have any Jbuttons, we can format them.
	private void setButtonProperties(JButton button) {
		button.setFont(new Font("ComicSans", Font.BOLD,12));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
	//if we have any JLabels, we can format them.
	private void setLabelProperties(JLabel label) {
		label.setFont(new Font("ComicSans", Font.BOLD,24));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.BLACK);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
}
