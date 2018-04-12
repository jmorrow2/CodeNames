package gui;

/**@author James Morrow
@author Harsh Patel
@author Victoria Dib
@author Jason Zhou
@author Kimberly So
*/

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;

import com.sun.glass.events.KeyEvent;

import code.Board;
import code.Location;
import code.Model;
import event_handlers.EndTurnActionListener;
import event_handlers.QuitActionListener;
import event_handlers.RestartActionListener;
import event_handlers.SelectActionListener;
import event_handlers.SubmitActionListener;
import main.Driver;


public class GUI {
	
	/**Model instance variable belonging to the GUI*/
	private Model model;
	
	/**Board instance variable belonging to the GUI*/
	private Board board;
	
	/**Driver instance variable belonging to the GUI*/
	private Driver windowHolder;
	
	/**JPanel instance variable which is the mainPanel of the driver's JFrame*/
	private JPanel mainPanel;
	
	/**JPanel representing the gameBoard which displays the games 5X5 grid codenames and agents*/
	private JPanel gameBoardPanel; 
	
	/**JPanel which holds the components that display whose turn it is*/
	private JPanel turnPanel;
	
	/**JLabel that displays who's turn it is*/
	private JLabel turnLabel;
	
	/**JPanel which holds components for the spymaster to enter clues and count*/
	private JPanel spyClueCountPanel;
	
	/**JTextField where the spymaster types in the clue*/
	private JTextField clueText;
	
	/**JTextField where the spymaster types in the count*/
	private JTextField countText;
	
	/**JPanel that holds the components for the guessers clue and count*/
	private JPanel guesserClueCountPanel;
	
	/**JLabel displaying the clue given by the spymaster*/
	private JLabel guessClueLabel;
	
	/**JLabel displaying the current count*/
	private JLabel guessCountLabel;
	
	/**Constructor of GUI class
	 * @param m - Model
	 * @param mp - JPanel from Driver class
	 * @param driver - Driver
	 * */
	public GUI(Model m, JPanel mp, Driver driver) {
		model = m;
		windowHolder = driver;
		mainPanel = mp;
		board = model.getBoard();

		/**creates the JMenuBar called menuBar
		 * creates and adds JMenu menu to menuBAr
		 * creates and adds JMenuItem restart to menu, with RestartActionListener
		 * creates and adds JMenuItem quit to menu, with QuitActionListener
		 * */
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_F);
		menu.getAccessibleContext().setAccessibleDescription("The only menu in this program that has menu items");
		menuBar.add(menu);
		
		JMenuItem restart = new JMenuItem("Restart Game",KeyEvent.VK_R);
//		restart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, ActionEvent.ALT_MASK));
//		restart.getAccessibleContext().setAccessibleDescription("Restart the game");
		RestartActionListener res = new RestartActionListener(windowHolder);
		restart.addActionListener(res);

		JMenuItem quit = new JMenuItem("Quit Game", KeyEvent.VK_Q);
//		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, ActionEvent.ALT_MASK));
//		quit.getAccessibleContext().setAccessibleDescription("Exit the game.");
		QuitActionListener q = new QuitActionListener(windowHolder);
		quit.addActionListener(q);
		
		menu.add(quit);
		menu.add(restart);

		windowHolder.get_window().setJMenuBar(menuBar);
		
		/**creates the spyClueCountPanel
		 * creates and adds JLabel spyClueLabel to spyClueCountPanel
		 * creates and adds JText field clueText to spyClueCountPanel
		 * creates and adds JLabel spyCountLabel to spyClueCountPanel
		 * creates and adds JText field countText to spyClueCountPanel
		 * creates and adds JButton submit to spyClueCountPanel with SubmitActionListener
		 * */
		
		spyClueCountPanel = new JPanel();
		spyClueCountPanel.setLayout(new BoxLayout(spyClueCountPanel, BoxLayout.X_AXIS));
		JLabel spyClueLabel = new JLabel("Clue:");
		setLabelProperties(spyClueLabel);
		clueText = new JTextField();
		spyClueCountPanel.add(spyClueLabel);
		spyClueCountPanel.add(clueText);
		
		JLabel spyCountLabel = new JLabel("Count:");
		setLabelProperties(spyCountLabel);
		countText = new JTextField();
		
		JButton submit = new JButton("Submit");
		SubmitActionListener sub = new SubmitActionListener(this, model);
		submit.addActionListener(sub);
		setButtonProperties(submit);
		
		spyClueCountPanel.add(spyCountLabel);
		spyClueCountPanel.add(countText);
		spyClueCountPanel.add(submit);
		
		/**creates the guesserClueCountPanel
		 * creates and adds JLabel guessClueLabel to guessClueCountPanel
		 * creates and adds JLabel guessCountLabel to guessClueCountPanel
		 * creates and adds JButton endTurn to guessClueCountPanel with EndTurnActionListener
		 * */
		guesserClueCountPanel = new JPanel();
		guesserClueCountPanel.setLayout(new BoxLayout(guesserClueCountPanel, BoxLayout.X_AXIS));
		guessClueLabel = new JLabel();
		setLabelProperties(guessClueLabel);
		
		guessCountLabel = new JLabel();
		setLabelProperties(guessCountLabel);
		
		JButton endTurn = new JButton("End Turn");
		setButtonProperties(endTurn);
		EndTurnActionListener e = new EndTurnActionListener(this);
		endTurn.addActionListener(e);
		
		guesserClueCountPanel.add(guessClueLabel);
		guesserClueCountPanel.add(guessCountLabel);
		guesserClueCountPanel.add(endTurn);
		
		/**creates the turnPanel
		 * creates and adds turnLabel to turnPanel
		 * */
		turnPanel = new JPanel();
		turnLabel = new JLabel();
		setLabelProperties(turnLabel);
		refreshTurnPanel();
		turnPanel.add(turnLabel);
		
		/**creates the _gameBoardPanel*/
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		gameBoardPanel = new JPanel(); 
		gameBoardPanel.setLayout(new GridLayout(model.getRow(),model.getColumn()));
		
		/**sets up the gameBoardPanel for the red spymasters in the beginning of the game*/
		spyGameBoardSetUp();
		
		/**adds GUI components to the _mainPanel*/
		addGameBoardPanel();
		addSpyClueCountPanel();
		addTurnPanel();
			
		/**Opening dialog box that welcomes players to the game and explains the rules*/
		displayStartGameDialogBox();
	}
	
	/**updates the gameBoardPanel for a spymasters turn 
	 * removes everything from the gameBoardPanel
	 * creates JLabels with unrevealed codenames being displayed and all agent colors displayed
	 * adds JLabels to gameBoardPanel*/
	public void spyGameBoardSetUp() {
		gameBoardPanel.removeAll();
		Location[][] agents = board.getLocArray();
		for(int i=0; i<agents.length;i++) {
			for(int k=0; k<agents[0].length;k++) {
				JLabel l = new JLabel();
				if(!(agents[i][k].getRevealed())) {
					l.setText(agents[i][k].getCodename());
				}
				setLabelProperties(l);
				if(agents[i][k].getAgent().equals("Red")) {
					l.setBackground(Color.RED);
				}
				if(agents[i][k].getAgent().equals("Blue")) {
					l.setBackground(Color.BLUE);
				}
				if(agents[i][k].getAgent().equals("Assassin")) {
					l.setBackground(Color.GRAY);
				}
				if(agents[i][k].getAgent().equals("Bystander")) {
					l.setBackground(Color.YELLOW);
				}
			gameBoardPanel.add(l);
			}
		}
	}
	
	/**updates the gameBoardPanel for a guesser's turn
	 * removes everything from the gameBoardPanel
	 * creates JButtons for unrevealed locations with codenames displayed and with SelectActionListener
	 * creates JButtons for revealed codenames with agent color displayed and no actionListener
	 * adds JButtons to the gameBoardPanel*/
	public void guesserGameBoardSetUp() {
		gameBoardPanel.removeAll();
		Location[][] agents = board.getLocArray();
		for(int i=0; i<agents.length;i++) {
			for(int k=0; k<agents[0].length;k++) {
				JButton b = new JButton();
				if(!(agents[i][k].getRevealed())) {
					b.setText(agents[i][k].getCodename());
					SelectActionListener s = new SelectActionListener(this, windowHolder, model, agents[i][k]);
					b.addActionListener(s);
					setButtonProperties(b);
				}
				if(agents[i][k].getRevealed()) {
					if(agents[i][k].getAgent().equals("Red")) {
						b.setBackground(Color.RED);
					}
					if(agents[i][k].getAgent().equals("Blue")) {
						b.setBackground(Color.BLUE);
					}
					if(agents[i][k].getAgent().equals("Assassin")) {
						b.setBackground(Color.GRAY);
					}
					if(agents[i][k].getAgent().equals("Bystander")) {
						b.setBackground(Color.YELLOW);
					}
				}
			gameBoardPanel.add(b);
			}
		}
	}
	
	/**refreshes the guesserClueCountPanel with the current clue and count displayed*/
	public void refreshGuesserClueCountLabel() {
		guessClueLabel.setText("Clue: " + model.getClue());
		guessCountLabel.setText("Count: " + model.getCount());
	}
	
	/**refreshes the turnPanel with the current players turn
	 * indicates teams color and whether it is a spymaster or guesser's turn using booleans from the model*/
	public void refreshTurnPanel() {
		if(model.getRedTurn() && model.getSpyTurn()) {
			turnLabel.setText("It is the Red SpyMaster's Turn");
		} if(model.getBlueTurn() && model.getSpyTurn()) {
			turnLabel.setText("It is the Blue Spymaster's Turn");
		}
		if(model.getRedTurn() && !(model.getSpyTurn())) {
			turnLabel.setText("It is the Red Guesser's Turn");
		} if(model.getBlueTurn() && !(model.getSpyTurn())) {
			turnLabel.setText("It is the Blue Guesser's Turn");
		}
	}
	
	/**refreshes GUI components for the guesser
	 * removes all GUI components from the mainPanel, updates them, adds them back to the mainPanel, and calls update
	 * */
	public void guesserTurnRefresh() {
		mainPanel.removeAll();
		guesserGameBoardSetUp();
		addGameBoardPanel();
		refreshGuesserClueCountLabel();
		addGuesserClueCountPanel();
		addTurnPanel();
		update();
	}
	
	/**adds gameBoardPanel to mainPanel*/
	public void addGameBoardPanel() {
		mainPanel.add(gameBoardPanel);
	}
	
	/**adds spyClueCountPanel to mainPanel*/
	public void addSpyClueCountPanel() {
		mainPanel.add(spyClueCountPanel);
	}
	
	/**adds addGuesserClueCountPanel to mainPanel*/
	public void addGuesserClueCountPanel() {
		mainPanel.add(guesserClueCountPanel);
	}
	
	/**adds turnPanel to mainPanel*/
	public void addTurnPanel() {
		mainPanel.add(turnPanel);
	}
	
	/**clears the spymasters JText fields by setting them to empty strings*/
	public void clearTextFields() {
		clueText.setText("");
		countText.setText("");
	}
	
	/**method that switches the GUI from the spymasters turn to the guesser'turn
	 * if it is currently the spymasters turn sets the GUI up for the guesser
	 * if it is currently the guessers turn sets the GUI up for the spymaster
	 * 
	 * changes the spyTurn boolean in model so the turnPanel will display the correct players turn (spymaster or guesser)
	 * removes all GUI components from the mainPanel, updates them for either the spymasters or guessers turn, adds components back to the GUI and updates
	 * 
	 * */
	public void switchSpyGuesserGUI() {
		mainPanel.removeAll();
		if(model.getSpyTurn()) {
			model.switchSpyGuesserTurn();
			guesserGameBoardSetUp();
			addGameBoardPanel();
			refreshGuesserClueCountLabel();
			addGuesserClueCountPanel();		
		} else {
			model.switchSpyGuesserTurn();
			spyGameBoardSetUp();
			addGameBoardPanel();
			addSpyClueCountPanel();	
		}
		refreshTurnPanel();
		addTurnPanel();
		update();
	}
	
	/**method that switches the GUI from one team to the other
	 * displays the switch team dialog box
	 * calls changeTeam() which changes the redTurn and blueTurn booleans in model so the turnPanel will display the correct teams turn (red or blue)
	 * calls switchSpyGuesserGUI() to switch to the next teams spymaster
	 * */
	public void switchTeamGUI() {
		displaySwitchTeamMessage();
		model.changeTeam();
		switchSpyGuesserGUI();
	}
	
	/**Opening dialog box that welcomes players to the game*/
	public void displayStartGameDialogBox() {
		 JOptionPane.showMessageDialog(null, "Welcome to Codenames.\n The game begins with the Red Spymaster's turn. \n (we can remove this or make it say something cool.)");
	}
	
	/**method that displays the illegal clue/count dialog box 
	 * */
	public void displayIllegalMessage() {
		 JOptionPane.showMessageDialog(null, "Illegal! \n Your Clue cannot be empty or a codename that is currently on the board. "
		 		+ "\n Also your count must be an integer greater than 0. "
		 		+ "\n Now be a good sport and stop cheating.");
	}
	
	/**method that displays the switch team dialog box at the end of the teams turn*/
	public void displaySwitchTeamMessage() {
		String currentTeam = "";
		String nextTeam = "";
		if(model.getRedTurn()) {
			currentTeam = "Red";
			nextTeam = "Blue";
		} else {
			currentTeam = "Blue";
			nextTeam = "Red";
		}
		JOptionPane.showMessageDialog(null, "The " + currentTeam + " Team's turn is over"
				+ "\n It is now the " + nextTeam + " Team's turn "
				+ "\n Will the " + currentTeam + " Team's guesser please step away from the computer so that you do not see the location of the agents "
				+ "\n ...that would mean you automatically lose for cheating");
	}
	
	/**method that displays the winner when all agents of a certain team have been revealed
	 * @param winner string from m.getWinner() call in SelectActionlistener 
	 * */
	public void displayWinningMessage(String winner) {
		JOptionPane.showMessageDialog(null, "The " + winner + " Team Won!");
	}
	
	/**method that calls updateJFrameIfNotHeadless method*/
	public void update() {
		updateJFrameIfNotHeadless();
	}
	
	/**method that calls updateJFrame on the windowHolder as long as it is not null*/
	private void updateJFrameIfNotHeadless() {
		if(windowHolder != null) {
			windowHolder.updateJFrame();
		}
	}
	
	/**sets aesthetic properties for a JButton
	 * @param JButton
	 * */
	private void setButtonProperties(JButton button) {
		button.setFont(new Font("ComicSans", Font.BOLD,24));
		button.setBackground(Color.WHITE);
		button.setForeground(Color.BLACK);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
	
	/**sets aesthetic properties for a JLabel
	 * @param JLabel
	 * */
	private void setLabelProperties(JLabel label) {
		label.setFont(new Font("ComicSans", Font.BOLD,24));
		label.setBackground(Color.WHITE);
		label.setForeground(Color.BLACK);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.DARK_GRAY, Color.LIGHT_GRAY));
	}
	
	/**@return method for clueText JTextField*/
	public JTextField getClueText() {
		return clueText;
	}
	
	/**@return method for countText JTextField*/
	public JTextField getCountText() {
		return countText;
	}
	
	/**Top Secret Method for the games hidden Easter Egg*/
	public void easterEgg() {
		ImageIcon ee = new ImageIcon("easterEgg.png");
		JOptionPane.showMessageDialog(null, ee, "CAPTAIN HERTZ", JOptionPane.INFORMATION_MESSAGE); 
	}
}
