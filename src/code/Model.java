package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Model {
	
	public Board board;
	public int row;
	public int column;
	public int redAgents;
	public int blueAgents;
	public int bystanders;
	public int assassins;
	public String file;
	public ArrayList<String> allCodenamesArray;
	public ArrayList<String> codenamesArray;
	public ArrayList<String> agentArray;
	public ArrayList<Location> locArray;
	public ArrayList<String> illegalGuessArray;
	public boolean redTurn;
	public boolean blueTurn;
	public String clue;
	public int count;
	
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
	
	public Board createBoard(ArrayList<Location> l) {
		Board answer = new Board(l);
		return answer;
	}
	
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
		l.revealed = true; 
		for(int i=0; i<illegalGuessArray.size(); i++) {
			if(l.codename.equals(illegalGuessArray.get(i))) {
				illegalGuessArray.remove(illegalGuessArray.get(i));
			}
		}
		count--;
		if((redTurn && l.agent.equals("Red")) || (blueTurn && l.agent.equals("Blue"))){
			return true;
		}
		return false;
	}
	
	public boolean winningState() {
		int redCount = 0;
		int blueCount = 0;
		int assassinCount = 0;
			for(Location l : board.locArray) {
				if(l.revealed && l.agent.equals("Red")) {
					redCount++;
				}
				if(l.revealed && l.agent.equals("Blue")) {
					blueCount++;
				}
				if(l.revealed && l.agent.equals("Assassin")) {
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
}
