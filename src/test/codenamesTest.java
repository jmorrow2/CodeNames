package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import code.Board;
import code.Model;
import code.Location;

public class codenamesTest {


	@Test
	public void createBoardTest() {
	Board b = new Board();
	assertEquals(25, b.locArray.length);
	//This is just seeing the length of one instance of 
	//location is 25?
	}
	
	@Test
	public void codenameFileTest() {
	//assertTrue();
	}
	
	@Test
	public void chooseCodenamesTest() {
		Model m = new Model();
		assertEquals(25, m.chooseCodenames());
		ArrayList<String> diffCodeNames = new ArrayList<>();
	//	HashMap<String, Integer> hash = new HashMap<>();
		for(int i=0; i<25; i++) {
			diffCodeNames.add(m.getCodenamesArray(i));
		}
		assertEquals(25, diffCodeNames.size());
	} 
	
	@Test
	public void createAgentsTest() {
		Model m = new Model();
	
		assertEquals(9, m.redArray.size());
		assertEquals(8, m.blueArray.size());
		assertEquals(7, m.bystanderArray.size());
		assertEquals(1, m.assassinArray.size());
	
	
//	HashMap<String, Integer> redHash = new HashMap<>();
	ArrayList<String> redAgents = new ArrayList<>();
	for(int i=0; i<9; i++) {
		redAgents.add(m.getRedArray(i));
	}
	assertEquals(9, redAgents.size());
	
	ArrayList<String> blueAgents = new ArrayList<>();
	for(int i=0; i<9; i++) {
		blueAgents.add(m.getRedArray(i));
	}
	assertEquals(8, blueAgents.size());
	
	ArrayList<String> bystanders = new ArrayList<>();
	for(int i=0; i<7; i++) {
		bystanders.add(m.getBystanderArray(i));
	}
	assertEquals(7, bystanders.size());
	
	ArrayList<String> assassin = new ArrayList<>();
	assassin.add(m.getAssassinArray(0));
	assertEquals(1, assassin.size());
}
	

//	@Test
//	public void turnTest() {
//		Model m = new Model();
//		assertTrue(true/false);
//	}
//	
//	@Test
//	public void locTest() {
//		Model m = new Model();
//		Location[] loc = m.locArray;
//		for(int i=0; i<25; i++) {
//			assertFalse(loc[i].codename.isEmpty());
//			assertFalse(loc[i].person.isEmpty());
//			assertFalse(loc[i].revealed);
//		}
//	}
//	
	@Test 
	public void legalTest() {
		
	}
	
}
	
