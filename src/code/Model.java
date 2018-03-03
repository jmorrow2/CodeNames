package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Model {
	/** Instance variable called "board" of Board class belonging to the Model class */
	private Board board;   
	/** Number of rows in the Model Constructor */
	private int row;
	/** Number of columns in the Model Constructor */
	private int column;
	/** Number of redAgents in the Model Constructor */
	private int redAgents;
	/** Number of blueAgents in the Model Constructor */
	private int blueAgents;
	/** Number of bystanders in the Model Constructor */
	private int bystanders;
	/** Number of assassins in the Model Constructor */
	private int assassins;
	/** Name of the file being read */
	private String file;
	/** ArrayList containing all possible codeNames read from the file*/
	private ArrayList<String> allCodenamesArray;
	/** ArrayList containing a certain number of the codeNames selected depending on the size of the board*/
	private ArrayList<String> codenamesArray;
	/** ArrayList containing all of the possible randomly generated assignments for the agents, bystanders, and assassins*/
	private ArrayList<String> agentArray;
	/** ArrayList containing all the assignments for each location -not revealed, agents/bystanders/assassins, red team starts first*/
	private ArrayList<Location> locArray;
	/** ArrayList */
	private ArrayList<String> illegalGuessArray;
	/** Tells you whose turn it is */
	private boolean redTurn;
	/** Tells you whose turn it is */
	private boolean blueTurn;
	/** Variable named "clue" that gives a clue */
	private String clue;
	/** Number of locations whose codeName is related to the clue -always a whole number greater than 0 */
	private int count;
	
	/** Constructor of Model class*/
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
	/** Method that creates a new Board*/
	public Board createBoard(ArrayList<Location> l) {
		Board answer = new Board(l);
		return answer;
	}
	/** Method that reads the file and adds it to an ArrayList*/
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
	/** Selects random codeNames and puts it in an ArrayList*/
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
	/** Assigns codeNames either "Red", "Blue", "Bystander", or "Assassin"*/
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
	/** Gives each codeName and agent a location on the Board*/
	public ArrayList<Location> createLocationsArray() {
		ArrayList<Location> answer = new ArrayList<Location>();
		for(int i=0; i<(row*column); i++) {
			Location loc = new Location(codenamesArray.get(i), agentArray.get(i));
			answer.add(loc);
		}
		return answer;
	}
	
	public ArrayList<String> createIlegalGuessArray(){
		ArrayList<String> answer = new ArrayList<String>();
		for(int i=0; i<(row*column); i++) {
			answer.add(codenamesArray.get(i));
		}
		return answer;
	}
	
	public boolean clueCheck(String c) {
		for(String s : illegalGuessArray) {
			if(s.equals(c)) {
				return false;
			}
		}
		return true;
	}
	
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
	
	public String assassinRevealed() {
		if(redTurn) {
			return "Blue";
		}
		if(blueTurn) {
			return "Red";
		}
		return null;
	}
	
	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}
	
	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getRedAgents() {
		return redAgents;
	}

	public void setRedAgents(int redAgents) {
		this.redAgents = redAgents;
	}

	public int getBlueAgents() {
		return blueAgents;
	}

	public void setBlueAgents(int blueAgents) {
		this.blueAgents = blueAgents;
	}

	public int getBystanders() {
		return bystanders;
	}

	public void setBystanders(int bystanders) {
		this.bystanders = bystanders;
	}

	public int getAssassins() {
		return assassins;
	}

	public void setAssassins(int assassins) {
		this.assassins = assassins;
	}
	
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
	
	public ArrayList<String> getAllCodenamesArray() {
		return allCodenamesArray;
	}

	public void setAllCodenamesArray(ArrayList<String> allCodenamesArray) {
		this.allCodenamesArray = allCodenamesArray;
	}

	public ArrayList<String> getCodenamesArray() {
		return codenamesArray;
	}

	public void setCodenamesArray(ArrayList<String> codenamesArray) {
		this.codenamesArray = codenamesArray;
	}

	public ArrayList<String> getAgentArray() {
		return agentArray;
	}

	public void setAgentArray(ArrayList<String> agentArray) {
		this.agentArray = agentArray;
	}

	public ArrayList<String> getIllegalGuessArray() {
		return illegalGuessArray;
	}

	public void setIllegalGuessArray(ArrayList<String> illegalGuessArray) {
		this.illegalGuessArray = illegalGuessArray;
	}
	
	public ArrayList<Location> getLocArray() {
		return locArray;
	}

	public void setLocArray(ArrayList<Location> locArray) {
		this.locArray = locArray;
	}

	public boolean getRedTurn() {
		return redTurn;
	}

	public void setRedTurn(boolean redTurn) {
		this.redTurn = redTurn;
	}

	public boolean getBlueTurn() {
		return blueTurn;
	}

	public void setBlueTurn(boolean blueTurn) {
		this.blueTurn = blueTurn;
	}

	public String getClue() {
		return clue;
	}

	public void setClue(String clue) {
		this.clue = clue;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
