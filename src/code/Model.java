package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Instances of this class are used to represent features of the game Codenames.
 * 
 * @author Victoria Dib
 * @author James Morrow
 * @author Harsh Patel
 * @author Jason Zhou
 * @author Kimberly So

*/
public class Model {
	/**
	 *  Instance variable of the Board class belonging to the Model class
	 */
	private Board board;   
	
	/**
	 *  Number of rows (for the board)
	 */
	private int row;
	
	/** Number of columns on the board) */
	private int column;
	
	/** The number of redAgents in the game*/
	private int redAgents;
	
	/** The number of blueAgents in the game */
	private int blueAgents;
	
	/** The number of bystanders  */
	private int bystanders;
	
	/**  The number of assassins	*/
	private int assassins;
	
	/** The number of greenAgents in the game */
	private int greenAgents;
	
	/**  the name of a file of words */
	private String file;
	
	/** Holds all possible codeNames from Gamewords.txt*/
	private ArrayList<String> allCodenamesArray;
	
	/** Holds a certain number of the codeNames selected depending on the size of the board*/
	private ArrayList<String> codenamesArray;
	
	/** ArrayList containing all of the possible randomly generated assignments for the agents, bystanders, and assassins*/
	private ArrayList<String> agentArray;
	
	/** ArrayList containing all the assignments for each location -not revealed, agents/bystanders/assassins, red team starts first*/
	private Location[][] locArray;
	
	/** ArrayList of all clues that are illegal to give to the guesser*/
	private ArrayList<String> illegalGuessArray;
	
	/** Determines which team's turn it is*/
	private boolean redTurn;
	
	/** Determines which team's turn it is*/
	private boolean blueTurn;
	
	/** Determines which team's turn it is*/
	private boolean greenTurn;
	
	/** Determines if it is the spymasters turn or the guessers turn*/ 
	private boolean spyTurn;
	
	/** String which returns the winning team's name*/
	private String winner;

	/** Variable named "clue" that gives a clue */
	private String clue;
	
	/** Number of locations whose codeName is related to the clue -always a whole number greater than 0 */
	private int count;
	
	/** Constructor of Model class
	 * @param r - number of rows in the board
	 * @param c - number of columns in the board
	 * @param red = number of red agents 
	 * @param blue - number of blue agents
	 * @param bys - number of bystanders
	 * @param assassin - number of assassin
	 * @param f - the name of the filename
	 *  */
	public Model(int r, int c, int red, int blue, int green,  int bys, int assassin, String f) {
		row = r;
		column = c;
		redAgents = red;
		blueAgents = blue;
		greenAgents = green;
		bystanders = bys;
		assassins = assassin;
		file = f;
		setUp();
	}
	
	/**
	 * Method that sets the board for a new game. The file is read and the names are put into the allCodenamesarraylist, codenames are chosen,
	 * a board is created, locations are chosen and it is red teams turn.
	 */
	public void setUp() {
		allCodenamesArray = readFile(file);
		codenamesArray = chooseCodenames();
		agentArray = createAgents();
		locArray = createLocationsArray();
		board = createBoard(locArray);
		redTurn = true;
		blueTurn = false;
		greenTurn = false;
		spyTurn = true;
	}
	
	/** 
	 * Method that creates a new Board
	 * @param ArrayList<Location> l holds the all the locations on the board
	 * @return A new board depending on the number of locations in the arraylist*/
	public Board createBoard(Location[][] l) {
		Board answer = new Board(l);
		return answer;
	}
	/** 
	 * Method that reads the file and adds it to an ArrayList
	 * @param s - the name of a file (/src/codeGameWords.txt)
	 * @return an ArrayList<String> containing the words from the file*/
	public ArrayList<String> readFile(String s) {
		ArrayList<String> answer = new ArrayList<String>();
		try {
			String filename = s;
	        for(String line : Files.readAllLines(Paths.get(filename))){
	        	answer.add(line);
	        	}
			} catch (IOException ex){
		        ex.printStackTrace();
		        }
		return answer;
	}
	/** 
	 * Instantiates illegalGuessArray to a new ArrayList<String>
	 * Shuffles the whole list of codenames and picks a number of codeNames depending on the number of Locations (row*column) 
	 * and adds them to the answer ArrayList and illegalGuessArray if they are not already inside the answer ArrayList to insure distinct codenames are chosen (codenamesArray)
	 * @return ArrayList<String> with a number of distinct codenames.
	 * */
	public ArrayList<String> chooseCodenames() {
		illegalGuessArray = new ArrayList<String>();
		ArrayList<String> answer = new ArrayList<String>();
		Collections.shuffle(allCodenamesArray);
			for(String s : allCodenamesArray) {
				if(!(answer.contains(s))) {
					answer.add(s);
					illegalGuessArray.add(s);
					if(answer.size() == (row * column)) {
						break;
					}
				}
			}
		return answer;
	}
	/** 
	 * Creates  members either Red agents, blue agents, bystanders or an Assassin and 
	 * shuffles them up in an arrayList so they will be randomly assigned.
	 * @return a shuffled arraylist of possible agent assignments*/
	public ArrayList<String> createAgents(){
		ArrayList<String> answer = new ArrayList<String>();
		for (int i=0; i<redAgents; i++) {
			answer.add("Red");
		}
		for (int i=0; i<blueAgents; i++) {
			answer.add("Blue");
		}
		for (int i=0; i<greenAgents; i++) {
			answer.add("Green");
		}
		for (int i=0; i<bystanders; i++) {
			answer.add("Bystander");
		}
		for (int i=0; i<assassins; i++) {
		answer.add("Assassin");
		}
		Collections.shuffle(answer);
		return answer;
	}
	/**
	 * Gives each Location a codeName and an agent on the Board
	 * @return a list of created locations each assigned with a codename and a team/agent.
	 * */
	public Location[][] createLocationsArray() {
		ArrayList<Location> temp = new ArrayList<Location>();
		for(int i=0; i<(row*column); i++) {
			Location loc = new Location(codenamesArray.get(i), agentArray.get(i));
			temp.add(loc);
		}
		Location[][] answer = new Location[row][column];
		int count = 0;
		for(int i=0; i<row; i++) {
			for(int k=0; k<column; k++) {
			answer[i][k] = temp.get(count);
			count++;
			}
		}
		return answer;
	}
	
	/**
	 * @param c checks to see if clue is in illegalGuessArray
	 *(clues cannot equal(illegal) to a codename unless that codename is in a locations that was already Revealed)
	 * @return {@code false) if it is legal because it was already revealed.  {@code true} if its an illegal move
	 */
	public boolean clueCheck(String c) {
		for(String s : illegalGuessArray) {   
			if(s.equals(c)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * Decrements the count and updates the Location's codename and whether
	 *  or not the Location held the current team's agent.
	 * 
	 * @param l Location to be selected and revealed.
	 * @return {@code true} if red turn and red agent revealed, blue turn and blue agent revealed
	 * {@code false} if  red/blue team revealed opposing team's agent
	 */
	public boolean selected(Location l) {
		l.setRevealed(true); 
		for(int i=0; i<illegalGuessArray.size(); i++) {
			if(l.getCodename().equals(illegalGuessArray.get(i))) {
				illegalGuessArray.remove(illegalGuessArray.get(i));
			}
		}
		count--;
		if((redTurn && l.getAgent().equals("Red")) || (blueTurn && l.getAgent().equals("Blue")) || greenTurn && l.getAgent().equals("Green")){
			return true;
		}
		return false;
	}
	/**
	 * Method checks whether the board is in a winning state
	 * If all red agents found returns true and sets winner string to "Red"
	 * If all blue agents found returns true and sets winner string to "Blue"
	 * If assassin revealed sets assassinRev to true and returns false
	 * @return {@code true} if all of one team's agents found or the opposing team wins
	 *  when assassin is revealed
	 *  {@code false} if board is not in a winning state
	 */
	public boolean winningState() {
		int redCount = 0;
		int blueCount = 0;
		int greenCount = 0;
		Location[][] locArray = board.getLocArray();
		for(int i=0;i<row;i++) {
			for(int k=0;k<column;k++) {
				Location l = locArray[i][k];
				if(l.getRevealed() && l.getAgent().equals("Red")) {
					redCount++;
				}
				if(l.getRevealed() && l.getAgent().equals("Blue")) {
					blueCount++;
				}
			
				if(l.getRevealed() && l.getAgent().equals("Green")) {
					greenCount++;
		
				}
				if(l.getRevealed() && l.getAgent().equals("Assassin")) {
					winner = assassinRevealed();
					return true;
				}
			}
		}
		if(redCount == redAgents)  {
			winner = "Red";
			return true;
		}
		if(blueCount == blueAgents) {
			winner = "Blue";
			return true;
		}
		if(greenCount == greenAgents && greenCount !=0 ) {
			winner = "Green";
					return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return if red agents reveal the assassins, blue agents win
	 * if blue agents reveal the assassin, the red agents win
	 * If neither teams reveal the assassin the game goes on.
	 */
	public String assassinRevealed() {
			if(redTurn) {
				return "Blue";
			}
			if(blueTurn) {
				return "Red";
			}
			if(greenTurn) {
				return "Green";
			}
			return null;
	}
	
	/**
	 * Method that changes play from the team's spymaster to it's guesser by negating spyTurn boolean
	 */
	public void switchSpyGuesserTurn() {
		if(spyTurn) {
			spyTurn = false;
		} else {
			spyTurn = true;
		}
	}
	
	/**
	 * Method that changes the turn from one team to the other by negating redTurn and blueTurn booleans
	 */
	public void changeTeam() {
		if(redTurn) {
			redTurn = false;
			blueTurn = true;
			greenTurn = false;
		}
		if(greenTurn) {

			redTurn = true;
			blueTurn = false;
			greenTurn = false;
		}
		if(blueTurn) {
			redTurn = false;
			greenTurn = true;
			blueTurn = false;
		}
	}
		

	
	/**
	 * A getter method to @return the board
	 */
	public Board getBoard() {
		return board;
	}
/**
 * 
 * @param sets the current instance of the class board
 */
	public void setBoard(Board board) {
		this.board = board;
	}
	/**
	 * 
	 * @return the number of rows in the board
	 */
	public int getRow() {
		return row;
	}
/**
 * 
 * @param row -  sets the number current of rows in the board
 */
	public void setRow(int row) {
		this.row = row;
	}
/**
 * 
 * @return the number of columns in the board
 */
	public int getColumn() {
		return column;
	}

	/**
	 * 
	 * @param column the current number  of columns in the board
	 */
	public void setColumn(int column) {
		this.column = column;
	}
	/**
	 * 
	 * @return the number of red agents
	 */
	public int getRedAgents() {
		return redAgents;
	}
/**
 * 
 * @param redAgents  the current number of red agents 
 */
	public void setRedAgents(int redAgents) {
		this.redAgents = redAgents;
	}
/**
 * 
 * @return the number of blue agents
 */
	public int getBlueAgents() {
		return blueAgents;
	}
/**
 * 
 * @param blueAgents  the current number of blue agents to be made
 */
	public void setBlueAgents(int blueAgents) {
		this.blueAgents = blueAgents;
	}
/**
 * 
 * @return the number of bystanders on the board
 */
	public int getBystanders() {
		return bystanders;
	}

	/**
	 * 
	 * @param bystanders  the current number of bystanders on the board
	 */
	public void setBystanders(int bystanders) {
		this.bystanders = bystanders;
	}
/**
 * 
 * @return the number of assassins on the board
 */
	public int getAssassins() {
		return assassins;
	}
	/**
	 * 
	 * @param assassins sets the current number of assassins
	 */
	public void setAssassins(int assassins) {
		this.assassins = assassins;
	}
	
	/**
	 * 
	 * @return the string name of the file to be read
	 */
	public String getFile() {
		return file;
	}
/**
 * 
 * @param file - the name of the file to be read, returns current file
 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * 
	 * @return returns the arraylist of all possible codenames
	 */
	public ArrayList<String> getAllCodenamesArray() {
		return allCodenamesArray;
	}
/**
 * 
 * @param allCodenamesArray current arraylist of all possible codenames
 */
	public void setAllCodenamesArray(ArrayList<String> allCodenamesArray) {
		this.allCodenamesArray = allCodenamesArray;
	}

	/**
	 * 
	 * @return an arraylist of distinct codenames to be assigned to locations
	 */
	public ArrayList<String> getCodenamesArray() {
		return codenamesArray;
	}
/**
 * 
 * @param codenamesArray current arraylist of the distinct codenames
 */
	public void setCodenamesArray(ArrayList<String> codenamesArray) {
		this.codenamesArray = codenamesArray;
	}
/**
 * 
 * @return agentaArray - the arraylist holding the possible team assigments
 */
	public ArrayList<String> getAgentArray() {
		return agentArray;
	}
/**
 * 
 * @param agentArray - the current list of possible agents
 * 
 */
	public void setAgentArray(ArrayList<String> agentArray) {
		this.agentArray = agentArray;
	}
/**
 * 
 * @return gets the list of illegal guesses
 */
	public ArrayList<String> getIllegalGuessArray() {
		return illegalGuessArray;
	}
/**
 * 
 * @param illegalGuessArray - the current list of illegal guesses
 */
	public void setIllegalGuessArray(ArrayList<String> illegalGuessArray) {
		this.illegalGuessArray = illegalGuessArray;
	}
	/**
	 * 
	 * @return gets the arraylist filled with the location instances
	 */
	public Location[][] getLocArray() {
		return locArray;
	}
 /**
  * 
  * @param locArray set equal to the current list of location instances
  */
	public void setLocArray(Location[][] locArray) {
		this.locArray = locArray;
	}
/**
 * 
 * @return gets the red team's turn. (true if it is their turn)
 */
	public boolean getRedTurn() {
		return redTurn;
	}
/**
 * 
 * @param sets the current turn equal to the reds turn.
 */
	public void setRedTurn(boolean redTurn) {
		this.redTurn = redTurn;
	}
/**
 * 
 * @return gets the blue teams turn. (true if it is their turn)
 */
	public boolean getBlueTurn() {
		return blueTurn;
	}
	

/**
 * 
 * @param blueTurn sets the current turn equal to the blue team.
 */
	public void setBlueTurn(boolean blueTurn) {
		this.blueTurn = blueTurn;
	}
	
/**@return gets the green teams turn (true if it is their turn) */
	public boolean getGreenTurn() {
		return greenTurn;
	}
	

/**
 * 
 * @param blueTurn sets the current turn equal to the blue team.
 */
	public void setGreenTurn(boolean greenTurn) {
		this.greenTurn = greenTurn;
	}
	
/**
 * 
 * @return gets the clue from the team's spymaster
 */
	public String getClue() {
		return clue;
	}
/**
 * 
 * @param clue sets the clue from the team's spymaster as the current clue 
 */
	public void setClue(String clue) {
		this.clue = clue;
	}
/**
 * 
 * @return gets the number of locations whose codename is related to the clue
 */
	public int getCount() {
		return count;
	}
/**
 * 
 * @param count sets count equal to the current number of locations whose codename
 * is related to the clue.
 */
	public void setCount(int count) {
		this.count = count;
	}
	
	/**@return boolean indicating whether is is the spymasters turn*/
	public boolean getSpyTurn() {
		return spyTurn;
	}

	/**@return method for the winner string*/
	public String getWinner() {
		return winner;
	}

	
}
