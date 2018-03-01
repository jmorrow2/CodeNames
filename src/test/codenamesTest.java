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
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		ArrayList<Location> l = m.createLocationsArray();
		Board b = m.createBoard(l);
		assertNotNull("The createBoard method must create a board", b);
		assertEquals("The board must have instances of Location equal to (m.row * m.column)", (m.getRow() * m.getColumn()), b.getLocArray().size());
	}

	@Test
	public void readFileTest() {
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		ArrayList<String> test = m.readFile(m.getFile());
		assertNotNull("The readFile method must create an ArrayList of the codenames", test);
	}
	
	@Test
	public void chooseCodenamesTest() {
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		ArrayList<String> test = m.chooseCodenames();
		assertEquals("The chooseCodenames method must create an ArrayList of codename strings equal to (m.row * m.column)", (m.getRow() * m.getColumn()), test.size());
		HashMap<String, Integer> hash = new HashMap<>();
		for(int i=0; i<(m.getRow() * m.getColumn()); i++) {
			hash.put(test.get(i), 0);
		}
		assertEquals("The chosen codenames must all be unique", (m.getRow() * m.getColumn()), hash.size());
	}
	
	@Test
	public void createAgentsTest() {
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
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
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		assertTrue("When the game begins the redTurn variable must be true", m.getRedTurn());
		assertFalse("When the game begins the blueTurn variable must be false", m.getBlueTurn());
		Board b = m.createBoard(m.createLocationsArray());
		for(Location l : b.getLocArray()) {
			assertFalse("Every Location instance must have a codename string", l.getCodename().isEmpty());
			assertFalse("Every Location instance must have a agent string", l.getAgent().isEmpty());
			assertFalse("Every Location instance must have a revealed boolean that is set to false", l.getRevealed());
		}
	}
	
	@Test
	public void createIlegalGuessArrayTest() {
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		ArrayList<String> test = m.createIlegalGuessArray();
		assertEquals("At the beginning of the game the illegalGuessArray must be the same as the codenamesArray", test, m.getCodenamesArray());
	}
	
	@Test 
	public void clueCheckTest() {
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
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
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		m.setCount(3);
		assertTrue(m.getRedTurn());
		assertFalse(m.getBlueTurn());
		Location l = m.getBoard().getLocArray().get(0);
		assertFalse(l.getRevealed());
		
		if(l.getAgent().equals("Red")) {
			assertTrue("If a red agent is selected during the red teams turn the selected method must return true", m.selected(l));
		}
		
		if(l.getAgent().equals("Blue") || l.getAgent().equals("Assassin") || l.getAgent().equals("Bystander")) {
			assertFalse("If an agent other than red is selected during the red teams turn the selected method must return false", m.selected(l));
		}
		
		assertTrue("The selected method must change the selected Locations revealed boolean to true", l.getRevealed());
		assertEquals("The selected method must decrement the count by one", 2, m.getCount());
		
		m.setRedTurn(false);
		m.setBlueTurn(true);
		
		assertTrue(m.getBlueTurn());
		assertFalse(m.getRedTurn());
		
		if(l.getAgent().equals("Blue")) {
			assertTrue("If a blue agent is selected during the blue teams turn the selected method must return true", m.selected(l));
		}
		
		if(l.getAgent().equals("Red") || l.getAgent().equals("Assassin") || l.getAgent().equals("Bystander")) {
			assertFalse("If an agent other than blue is selected during the blue teams turn the selected method must return false", m.selected(l));
		}
	}
	
	@Test 
	public void winningStateTest() {
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		assertFalse("The winningState method must return false when the game first begins and no agents are revealed", m.winningState());
		
		for(Location l : m.getBoard().getLocArray()) {
			if(l.getAgent().equals("Blue")) {
				l.setRevealed(true);
			}
		}
		assertTrue("The winningState method must return true when all blue agents are revealed", m.winningState());	
		
		Model m2 = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		for(Location l : m2.getBoard().getLocArray()) {
			if(l.getAgent().equals("Red")) {
				l.setRevealed(true);
			}
		}
		assertTrue("The winningState method must return true when all red agents are revealed", m2.winningState());	
		
		Model m3 = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		for(Location l : m3.getBoard().getLocArray()) {
			if(l.getAgent().equals("Assassin")) {
				l.setRevealed(true);
			}
		}
		assertTrue("The winningState method must return true when all assassins are revealed", m3.winningState());	
		
		Model m4 = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		for(Location l : m4.getBoard().getLocArray()) {
			if(l.getAgent().equals("Red")) {
				l.setRevealed(true);
				break;
			}
			for(Location k : m4.getBoard().getLocArray()) {
				if(k.getAgent().equals("Blue")) {
					k.setRevealed(true);
					break;
				}
			}
		}
		assertFalse("The winningState method must return false when some, but not all, blue and red agents are revealed", m4.winningState());	
	}
	
	@Test
	public void assassinRevealedTest() {
		Model m = new Model(5, 5, 9, 8, 7, 1, "src/GameWords.txt");
		assertTrue(m.getRedTurn());
		assertFalse(m.getBlueTurn());
		assertEquals("The assassinRevealed method must return Blue if the assassin is revealed during the Red turn", m.assassinRevealed(), "Blue");
		m.setRedTurn(false);
		m.setBlueTurn(true);
		assertTrue(m.getBlueTurn());
		assertFalse(m.getRedTurn());
		assertEquals("The assassinRevealed method must return Red if the assassin is revealed during the Blue turn", m.assassinRevealed(), "Red");
	}
}
