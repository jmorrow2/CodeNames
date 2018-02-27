package test;

import java.util.HashMap;

import javax.xml.stream.Location;

import org.junit.Test;

public class codenamesTest {

	@Test
	public void createBoardTest() {
	Board b = new Board();
	assertEquals(25, b.locArray.length);
	}
	
	@Test
	public void codenameFileTest() {
	assertTrue();
	}
	
	@Test
	public void chooseCodenamesTest() {
		Model m = new Model();
		assertEquals(25, m.codenamesArray.length);
		HashMap<String, Integer> hash = new HashMap<>();
		for(int i=0; i<25; i++) {
			hash.add(codenamesArray[i], i++);
		}
		assertEquals(25, hash.size());
	} 
	
	@Test
	public void createAgentsTest() {
		Model m = new Model();
		assertEquals(9, m.redArray.length);
		assertEquals(8, m.blueArray.length);
		assertEquals(7, m.bystanderArray.length);
	assertEquals(1, m.assassinArray.length);
	
	HashMap<String, Integer> redHash = new HashMap<>();
	for(int i=0; i<9; i++) {
		hash.add(redArray[i], i++);
	}
	assertEquals(9, redHash.size());
	
	HashMap<String, Integer> blueHash = new HashMap<>();
	for(int i=0; i<9; i++) {
		hash.add(blueArray[i], i++);
	}
	assertEquals(8, blueHash.size());
	
	HashMap<String, Integer> bystanderHash = new HashMap<>();
	for(int i=0; i<9; i++) {
		hash.add(bystanderArray[i], i++);
	}
	assertEquals(9, bystanderHash.size());
}

	@Test
	public void turnTest() {
		Model m = new Model();
		assertTrue(m.red);
	}
	
	@Test
	public void locTest() {
		Model m = new Model();
		Location[] l = m.locArray;
		for(int i=0; i<25; i++) {
			assertFalse(l[i].codename.isEmpty());
			assertFalse(l[i].person.isEmpty());
			assertFalse(l[i].revealed);
		}
	}
	
	@Test 
	public void legalTest() {
		
	}
	
}
	
