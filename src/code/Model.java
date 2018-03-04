package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
	 *  */
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
	
	/**  the name of a file of words */
	private String file;
	
	/** Holds all possible codeNames from Gamewords.txt*/
	private ArrayList<String> allCodenamesArray;
	
	/** Holds a certain number of the codeNames selected depending on the size of the board*/
	private ArrayList<String> codenamesArray;
	
	/** ArrayList containing all of the possible randomly generated assignments for the agents, bystanders, and assassins*/
	private ArrayList<String> agentArray;
	
	/** ArrayList containing all the assignments for each location -not revealed, agents/bystanders/assassins, red team starts first*/
	private ArrayList<Location> locArray;
	
	/** ArrayList */
	private ArrayList<String> illegalGuessArray;
	
	/** Determines which team's turn it is*/
	private boolean redTurn;
	
	/** Determines which team's turn it is*/
	private boolean blueTurn;
	
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
	 * 
	 * When game starts, the file is read and the names are put into the allCodenamesarraylist, codenames are chosen,
	 *  a board is created, locations are chosen and it is red teams turn.
	 *  */
	public Model(int r, int c, int red, int blue, int bys, int assassin, String f) {
		row = r;
		column = c;
		redAgents = red;
		blueAgents = blue;
		bystanders = bys;
		assassins = assassin;
		file = f;
		allCodenamesArray = readFile(file);
		codenamesArray = chooseCodenames();
		illegalGuessArray = createIlegalGuessArray();
		agentArray = createAgents();
		locArray = createLocationsArray();
		board = createBoard(locArray);
		redTurn = true;
		blueTurn = false;
	}
	/** Method that creates a new Board
	 * @param ArrayList<Location> l holds the all the locations on the board
	 * @return A new board depending on the number of locations in the arraylist*/
	public Board createBoard(ArrayList<Location> l) {
		Board answer = new Board(l);
		return answer;
	}
	/** Method that reads the file and adds it to an ArrayList
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
	/** Shuffles the whole list of codenames and picks a number of codeNames depending on the number of Locations (row*column) 
	 * and adds them to a hashmap to insure distinct codenames then adds the key into an arrayList. (codenamesArray)
	 * @return ArrayList<String> with a number of distinct codenames.
	 * */
	public ArrayList<String> chooseCodenames() {
		HashMap<String, Integer> hash = new HashMap<String, Integer>();
		ArrayList<String> answer = new ArrayList<String>();
		Collections.shuffle(allCodenamesArray);
			for(String s : allCodenamesArray) {
				hash.put(s, 0);
				if(hash.size() == (row*column)) {
					break;
			}
		}
		for(String k : hash.keySet()) {
			answer.add(k);
		}
		return answer;
	}
	/** Creates  members either Red agents, blue agents, bystanders or an Assassin and 
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
		for (int i=0; i<bystanders; i++) {
			answer.add("Bystander");
		}
		for (int i=0; i<assassins; i++) {
		answer.add("Assassin");
		}
		Collections.shuffle(answer);
		return answer;
	}
	/** Gives each Location a codeName and an agent on the Board
	 * @return a list of created locations each assigned with a codename and a team/agent.
	 * */
	public ArrayList<Location> createLocationsArray() {
		ArrayList<Location> answer = new ArrayList<Location>();
		for(int i=0; i<(row*column); i++) {
			Location loc = new Location(codenamesArray.get(i), agentArray.get(i));
			answer.add(loc);
		}
		return answer;
	}
	/**
	 * Creates an arraylist filled with a number of illegal guesses based on the size of the board
	 * @return arraylist filled with illegal guesses.
	 */
	public ArrayList<String> createIlegalGuessArray(){
		ArrayList<String> answer = new ArrayList<String>();
		for(int i=0; i<(row*column); i++) {
			answer.add(codenamesArray.get(i));
		}
		return answer;
	}
	/**
	 * @param c checks to see if clue is in illegalGuessArray
	 *(clues cannot equal a current codename unless that codename is in a locations that was already Revealed)
	 * @return {@code false) if code is legal because it was already revealed.  {@code true} if code is not legal
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
		if((redTurn && l.getAgent().equals("Red")) || (blueTurn && l.getAgent().equals("Blue"))){
			return true;
		}
		return false;
	}
	/**
	 * Method checks whether the board is in a winning state
	 * @return {@code true} if all of one team's agents found or the opposing team wins
	 *  when assassin is revealed
	 *  {@code false} if board is not in a winning state
	 */
	public boolean winningState() {
		int redCount = 0;
		int blueCount = 0;
		int assassinCount = 0;
			for(Location l : board.getLocArray()) {
				if(l.getRevealed() && l.getAgent().equals("Red")) {
					redCount++;
				}
				if(l.getRevealed() && l.getAgent().equals("Blue")) {
					blueCount++;
				}
				if(l.getRevealed() && l.getAgent().equals("Assassin")) {
					assassinCount++;
				}
			}
		if(redCount == redAgents || blueCount == blueAgents || assassinCount == assassins) {
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
		return null;
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
	public ArrayList<Location> getLocArray() {
		return locArray;
	}
 /**
  * 
  * @param locArray set equal to the current list of location instances
  */
	public void setLocArray(ArrayList<Location> locArray) {
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
 * @param count sets count equal to the current number of locations whos codename
 * is related to the clue.
 */
	public void setCount(int count) {
		this.count = count;
	}
}
