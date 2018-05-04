package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import code.Board;
import code.Location;
import code.Model;

public class codenamesTest {

	@Test
	public void createBoardTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		Location[][] l = m.createLocationsArray();
		Board b = m.createBoard(l);
		assertNotNull("The createBoard method must create a board", b);
		assertEquals("The board must have instances of Location equal to (m.row * m.column)", (m.getRow() * m.getColumn()), (l.length * l[0].length));
	}

	@Test
	public void readFileTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		ArrayList<String> test = m.readFile(m.getFile());
		assertNotNull("The readFile method must create an ArrayList of the codenames", test);
	}
	
	@Test
	public void chooseCodenamesTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		ArrayList<String> test = m.chooseCodenames();
		assertEquals("The chooseCodenames method must create an ArrayList of codename strings equal to (m.row * m.column)", (m.getRow() * m.getColumn()), test.size());
		HashMap<String, Integer> hash = new HashMap<>();
		for(int i=0; i<(m.getRow() * m.getColumn()); i++) {
			hash.put(test.get(i), 0);
		}
		assertEquals("The chosen codenames must all be unique", (m.getRow() * m.getColumn()), hash.size());
		ArrayList<String> illegal = m.getIllegalGuessArray();
		assertEquals("At the beginning of the game the illegalGuessArray must be the same as the codenamesArray", illegal, test);
	}
	
	@Test
	public void createAgentsTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		ArrayList<String> test = m.createAgents();
		assertEquals("The createAgents method must create an ArrayList of agent strings equal to (m.getRow() * m.getColumn())", (m.getRow() * m.getColumn()), (test.size()));
		
		int redCount = 0;
		int blueCount = 0;
		int bystanderCount = 0;
		int assassinCount = 0;
		
		for(int i=0; i<(m.getRow() * m.getColumn()); i++) {
			if (test.get(i).equals("Red")){
				redCount++;
			}
			if (test.get(i).equals("Blue")){
				blueCount++;
			}
			if (test.get(i).equals("Bystander")){
				bystanderCount++;
			}
			if (test.get(i).equals("Assassin")){
				assassinCount++;
			}
		}
		
		assertEquals("The agentArray must contain red agents equal to the redAgents variable", m.getRedAgents(), redCount);
		assertEquals("The agentArray must contain blue agents equal to the blueAgents variable", m.getBlueAgents(), blueCount);
		assertEquals("The agentArray must contain bystanders agents equal to the bystanders variable", m.getBystanders(), bystanderCount);
		assertEquals("The agentArray must contain assassins agents equal to the assassins variable", m.getAssassins(), assassinCount);
		
		ArrayList<String> test2 = m.createAgents();
		assertNotEquals(test, test2);
	}
	
	@Test
	public void createLocationsArrayTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		Board b = m.createBoard(m.createLocationsArray());
		assertEquals(m.getCurrentTeam(), "Red");
		assertNotEquals(m.getCurrentTeam(), "Blue");
		assertNotEquals(m.getCurrentTeam(), "Green");
		Location[][] l = b.getLocArray();
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) {
			assertFalse("Every Location instance must have a codename string", l[i][k].getCodename().isEmpty());
			assertFalse("Every Location instance must have a agent string", l[i][k].getAgent().isEmpty());
			assertFalse("Every Location instance must have a revealed boolean that is set to false", l[i][k].getRevealed());
			}
		}
	}
	
	@Test 
	public void clueCheckTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		String test = m.getIllegalGuessArray().get(0);
		assertFalse("The clueCheck method return false for a clue that is contained in the illegalGuessArray", m.clueCheck(test));
		
		String a = "--------";
			for(String s : m.getIllegalGuessArray()) {
				assertNotEquals(s, a);
			}
		assertTrue("The clueCheck method must return true for a clue that is not contained in the illegalGuessArray", m.clueCheck(a));
	}	
	
	@Test
	public void selectedTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		m.setCount(3);
		assertEquals(m.getCurrentTeam(), "Red");
		assertNotEquals(m.getCurrentTeam(), "Blue");
		assertNotEquals(m.getCurrentTeam(), "Green");
		Location[][] locArray = m.getBoard().getLocArray();
		Location l = locArray[0][0];
		assertFalse(l.getRevealed());
		
		if(l.getAgent().equals("Red")) {
			assertTrue("If a red agent is selected during the red teams turn the selected method must return true", m.selected(l));
		}
		
		if(l.getAgent().equals("Blue") || l.getAgent().equals("Assassin") || l.getAgent().equals("Bystander")) {
			assertFalse("If an agent other than red is selected during the red teams turn the selected method must return false", m.selected(l));
		}
		
		assertTrue("The selected method must change the selected Locations revealed boolean to true", l.getRevealed());
		assertEquals("The selected method must decrement the count by one", 2, m.getCount());
		
		m.setCurrentTeam("Blue");
		
		assertEquals(m.getCurrentTeam(), "Blue");
		assertNotEquals(m.getCurrentTeam(), "Red");
		assertNotEquals(m.getCurrentTeam(), "Green");
		
		if(l.getAgent().equals("Blue")) {
			assertTrue("If a blue agent is selected during the blue teams turn the selected method must return true", m.selected(l));
		}
		
		if(l.getAgent().equals("Red") || l.getAgent().equals("Assassin") || l.getAgent().equals("Bystander")) {
			assertFalse("If an agent other than blue is selected during the blue teams turn the selected method must return false", m.selected(l));
		}
	}
	
	@Test 
	public void winningStateTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		assertFalse("The winningState method must return false when the game first begins and no agents are revealed", m.winningState());
		Location[][] locArray = m.getBoard().getLocArray();
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) { 
				if(locArray[i][k].getAgent().equals("Blue")) {
					locArray[i][k].setRevealed(true);
				}
			}
		}
		
		assertTrue("The winningState method must return true when all blue agents are revealed", m.winningState());	
		
		Model m2 = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		Location[][] locArray2 = m2.getBoard().getLocArray();
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) { 
				if(locArray2[i][k].getAgent().equals("Red")) {
					locArray2[i][k].setRevealed(true);
				}
			}
		}
		assertTrue("The winningState method must return true when all red agents are revealed", m2.winningState());	
		
		Model m3 = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		Location[][] locArray3 = m3.getBoard().getLocArray();
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) { 
				if(locArray3[i][k].getAgent().equals("Assassin")) {
					locArray3[i][k].setRevealed(true);
				}
			}
		}
		assertTrue("The winningState method must return true when all assassins are revealed", m3.winningState());	
		
		Model m4 = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		Location[][] locArray4 = m4.getBoard().getLocArray();
		
		outerloop:
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) {
				if(locArray4[i][k].getAgent().equals("Red")) {
					locArray4[i][k].setRevealed(true);
					break outerloop;
				}
			}
		}
		
		outerloop:
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) {
				if(locArray4[i][k].getAgent().equals("Blue")) {
					locArray4[i][k].setRevealed(true);
					break outerloop;
				}
			}
		}
		assertFalse("The winningState method must return false when some, but not all, blue and red agents are revealed", m4.winningState());	
	}
	
	@Test
	public void assassinRevealedTest() {
		Model m = new Model(5,5,9,8,0,7,1,"src/GameWords.txt");
		m.getLoserArray().add("Red");
		assertEquals("The assassinRevealed method must return Blue if the Red team reveals the assassin", m.assassinRevealed(), "Blue");
		m.getLoserArray().remove("Red");
		m.getLoserArray().add("Blue");
		assertEquals("The assassinRevealed method must return Red if the Blue team reveals the assassin", m.assassinRevealed(), "Red");
	}
	
	
	@Test
	public void createLocationsArray3PlayerGameTest() {
		Model m = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		Board b = m.createBoard(m.createLocationsArray());
		Location[][] l = b.getLocArray();
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) {
			assertFalse("Every Location instance must have a codename string", l[i][k].getCodename().isEmpty());
			assertFalse("Every Location instance must have a agent string", l[i][k].getAgent().isEmpty());
			assertFalse("Every Location instance must have a revealed boolean that is set to false", l[i][k].getRevealed());
			}
		}
		
		ArrayList<String> test = m.getAgentArray();
		int redCount = 0;
		int blueCount = 0;
		int bystanderCount = 0;
		int assassinCount = 0;
		
		for(int i=0; i<(m.getRow() * m.getColumn()); i++) {
			if (test.get(i).equals("Red")){
				redCount++;
			}
			if (test.get(i).equals("Blue")){
				blueCount++;
			}
			if (test.get(i).equals("Bystander")){
				bystanderCount++;
			}
			if (test.get(i).equals("Assassin")){
				assassinCount++;
			}
		}
		
		assertEquals("The agentArray must contain red agents equal to the redAgents variable", m.getRedAgents(), redCount);
		assertEquals("The agentArray must contain blue agents equal to the blueAgents variable", m.getBlueAgents(), blueCount);
		assertEquals("The agentArray must contain bystanders agents equal to the bystanders variable", m.getBystanders(), bystanderCount);
		assertEquals("The agentArray must contain assassins agents equal to the assassins variable", m.getAssassins(), assassinCount);
		
		ArrayList<String> test2 = m.createAgents();
		assertNotEquals(test, test2);
	}
	
	@Test 
	public void winningState3PlayerTest() {
		Model m = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		assertFalse("The winningState method must return false when the game first begins and no agents are revealed", m.winningState());
		Location[][] locArray = m.getBoard().getLocArray();
		for(int i=0;i<m.getRow();i++) {
			for(int k=0;k<m.getColumn();k++) { 
				if(locArray[i][k].getAgent().equals("Blue")) {
					locArray[i][k].setRevealed(true);
				}
			}
		}
		
		assertTrue("The winningState method must return true when all blue agents are revealed", m.winningState());	
		
		Model m2 = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		Location[][] locArray2 = m2.getBoard().getLocArray();
		for(int i=0;i<m2.getRow();i++) {
			for(int k=0;k<m2.getColumn();k++) { 
				if(locArray2[i][k].getAgent().equals("Red")) {
					locArray2[i][k].setRevealed(true);
				}
			}
		}
		assertTrue("The winningState method must return true when all red agents are revealed", m2.winningState());	
		
		Model m3 = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		Location[][] locArray3 = m3.getBoard().getLocArray();
		for(int i=0;i<m3.getRow();i++) {
			for(int k=0;k<m3.getColumn();k++) { 
				if(locArray3[i][k].getAgent().equals("Green")) {
					locArray3[i][k].setRevealed(true);
				}
			}
		}
		assertTrue("The winningState method must return true when all green agents are revealed", m3.winningState());
		
		Model m4 = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		Location[][] locArray4 = m4.getBoard().getLocArray();
		for(int i=0;i<m4.getRow();i++) {
			for(int k=0;k<m4.getColumn();k++) { 
				if(locArray4[i][k].getAgent().equals("Assassin")) {
					locArray4[i][k].setRevealed(true);
				}
			}
		}
		assertTrue("The winningState method must return true when all assassins are revealed", m4.winningState());	
		
		Model m5 = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		Location[][] locArray5 = m5.getBoard().getLocArray();
		
		outerloop:
		for(int i=0;i<m5.getRow();i++) {
			for(int k=0;k<m5.getColumn();k++) {
				if(locArray5[i][k].getAgent().equals("Red")) {
					locArray5[i][k].setRevealed(true);
					break outerloop;
				}
			}
		}
		
		outerloop:
		for(int i=0;i<m5.getRow();i++) {
			for(int k=0;k<m5.getColumn();k++) {
				if(locArray5[i][k].getAgent().equals("Blue")) {
					locArray5[i][k].setRevealed(true);
					break outerloop;
				}
			}
		}
		
		outerloop:
		for(int i=0;i<m5.getRow();i++) {
			for(int k=0;k<m5.getColumn();k++) {
				if(locArray5[i][k].getAgent().equals("Green")) {
					locArray5[i][k].setRevealed(true);
					break outerloop;
				}
			}
		}
		
		outerloop:
		for(int i=0;i<m5.getRow();i++) {
			for(int k=0;k<m5.getColumn();k++) {
				if(locArray5[i][k].getAgent().equals("Assassin")) {
					locArray5[i][k].setRevealed(true);
					break outerloop;
				}
				
			}
		}
		
		assertFalse("The winningState method must return false when some but not all blue, red, and green agents and assasins are revealed", m5.winningState());
		
		Model m6 = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		m6.getLoserArray().add("Red");
		Location[][] locArray6 = m6.getBoard().getLocArray();
		for(int i=0;i<m6.getRow();i++) {
			for(int k=0;k<m6.getColumn();k++) { 
				if(locArray6[i][k].getAgent().equals("Red")) {
					locArray6[i][k].setRevealed(true);
				}
			}
		}
		
		assertFalse("If a team has lost before all of their agents were revealed they cannot win the game", m6.winningState());
	}
	
	@Test
	public void assassinsRevealed3PlayerTest() {
		Model m = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		assertNull(m.assassinRevealed());
		ArrayList<String> testLoserArray = m.getLoserArray();
		testLoserArray.add("Blue");
		assertNull(m.assassinRevealed());
		testLoserArray.add("Red");
		assertEquals("When Blue and Red both reveal assassins Green must win", m.assassinRevealed(), "Green");
		testLoserArray.remove("Blue");
		testLoserArray.add("Green");
		assertEquals("When Red and Green both reveal assassins Blue must win", m.assassinRevealed(), "Blue");
	}
	
	@Test
	public void changeCurrentTeam3PlayerTest() {
		Model m = new Model(5,5,6,5,5,7,2, "src/GameWords.txt");
		assertEquals("The game must start with the Red Teams turn", m.getCurrentTeam(), "Red");
		m.changeCurrentTeam();
		assertEquals("The Red Teams turn must change to the Blue Team", m.getCurrentTeam(), "Blue");
		m.changeCurrentTeam();
		assertEquals("The Blue Teams turn must change to the Green Team", m.getCurrentTeam(), "Green");
		m.getLoserArray().add("Red");
		m.changeCurrentTeam();
		assertEquals("If the Red Team Loses changeCurrentTeam must switch from Green to Blue", m.getCurrentTeam(), "Blue");
	}
}