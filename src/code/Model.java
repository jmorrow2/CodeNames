package code;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

public class Model {
	
	public ArrayList<String> allCodenamesArray;
	public ArrayList<String> codenamesArray;
	public ArrayList<String> agentArray;
	public ArrayList<String> redArray;
	public ArrayList<String> blueArray;
	public ArrayList<String> bystanderArray;
	public ArrayList<String> assassinArray;
	private Location[] l;
	private ArrayList<String>the25names;


	
	/**
	 * allCodenamesArray holds all possible code names that are read from a GameWords.txt 
	 * the25names should hold the 25 different codenames that will be assigned to a location.
	 * the agentArray holds the 9Red agents/8Blue agents/7Bystanders/1Assassin
	 */
	
	public Model() {
		Board b = new Board();
		b.locArray = createLocationsArray();  //causes tests to fail?
		allCodenamesArray = readFile();
		the25names = chooseCodenames();
		agentArray = createAgent();
	}
	
	public void startNewGame() {
		Board b = new Board();
		//red teams move
		//each boards 25 location is assigned a code name, person and not revealed
		l = createLocationsArray();
		for(int i = 0; i<25; i++) {
		}
		
	}
	
	
	public ArrayList<String> readFile() {
		ArrayList<String> answer = new ArrayList<String>();
		try {
			String filename = "src/code/GameWords.txt";
	        for(String line : Files.readAllLines(Paths.get(filename))){
	        //	allCodenamesArray.add(line);
	        	answer.add(line);
	        	}
			} catch (IOException ex){
		        ex.printStackTrace();
		        }
		return answer;
	}
	
	public ArrayList<String> chooseCodenames() {
		the25names = new ArrayList<String>();
		Collections.shuffle(allCodenamesArray);
		for(int i=0; i<25; i++) {
			the25names.add(allCodenamesArray.get(i));
		}
		return the25names;
	}

	public ArrayList<String> createAgent(){
		ArrayList<String> answer = new ArrayList<String>();
		for (int i=0; i<25; i++) {
			if(i <10) {
				answer.add("Red");
			}	
			if(i >=10 && i <18) {
				answer.add("Blue");
		}
			if(i >=18 && i <24) {
				answer.add("Bystander");
		}
			if(i==24) {
				answer.add("Assassin");
		}
		}
		Collections.shuffle(answer);
		return answer;
	}
	
	public Location[] createLocationsArray() {
		l = new Location[25];
		for(int i=0; i<25; i++) {
			Location loc = new Location(codenamesArray.get(i), agentArray.get(i));
		//^^ causes an error and createAgentsTest & choose Codenames fail?
			l[i] = loc;
		}
		return l;
	}
	
	
	
	
	/**
	 * 
	 * @param index at which arraylist returns a string.
	 * @return will return the string at the index in the arraylist.
	 */
	public String getRedArray(int i) {
		return redArray.get(i);
	}


	/**
	 * @return chooses a blue agent from the bluearray arraylist<string> at the indicated index
	 */
	public String getBlueArray(int i) {
		return blueArray.get(i);
	}


	/**
	 * @return chooses a bystander from the bystanderarray arraylist<string> at the indicated index
	 */
	public String getBystanderArray(int i) {
		return bystanderArray.get(i);
	}

	/**
	 * @return chooses an assassin from the assassinarray arraylist<string> at the indicated index
	 */
	public String getAssassinArray(int i) {
		return assassinArray.get(i);
	}

	/**
	 * @param index
	 * @return get a code name from codenamesArray at the indicated index
	 */
	public String getCodenamesArray(int i) {
		return codenamesArray.get(i);
	}

	public String getAgentArray(int index) {
		return agentArray.get(index);
}
	}
