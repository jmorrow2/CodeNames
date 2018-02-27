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
	
	public Model() {
		Board b = new Board();
		b.locArray = createLocationsArray();
		allCodenamesArray = readFile();
		codenamesArray = chooseCodenames();
		agentArray = createAgent();
	}
	
	public ArrayList<String> readFile() {
		ArrayList<String> answer = new ArrayList<String>();
		try {
			String filename = "src/code/GamesWords.txt";
	        for(String line : Files.readAllLines(Paths.get(filename))){
	        	answer.add(line);
	        	}
			} catch (IOException ex){
		        ex.printStackTrace();
		        }
		return answer;
	}
	
	public ArrayList<String> chooseCodenames() {
		ArrayList<String> answer = new ArrayList<String>();
		Collections.shuffle(allCodenamesArray);
		for(int i=0; i<25; i++) {
			answer.add(allCodenamesArray.get(i));
		}
		return answer;
	}

	public ArrayList<String> createAgent(){
		ArrayList<String> answer = new ArrayList<String>();
		for (int i=0; i<9; i++) {
			answer.add("Red");
		}
		for (int i=0; i<8; i++) {
			answer.add("Blue");
		}
		for (int i=0; i<7; i++) {
			answer.add("Bystander");
		}
		answer.add("Assassin");
		Collections.shuffle(answer);
		return answer;
	}
	
	public Location[] createLocationsArray() {
		Location[] l = new Location[25];
		for(int i=0; i<25; i++) {
			Location loc = new Location(codenamesArray.get(i), agentArray.get(i));
			l[i] = loc;
		}
		return l;
	}
}
