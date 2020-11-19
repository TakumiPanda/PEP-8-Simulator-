package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class BinaryTest { 

	/**
	 * Tests if two Binary objects are equal
	 */
	@Test
	void testEquality() {
		Binary a = new Binary("100");
		Binary b = a;
		boolean actual = a.equality(b);
		assertTrue(actual);
	} 
	
	/**
	 * Tests if two Binary objects are not equal
	 * Second Binary object is not null
	 */
	@Test
	void testEquality2() {
		Binary a = new Binary("100");
		Binary b = new Binary("101");
		boolean actual = a.equality(b);
		assertFalse(actual);
	}
	
	/**
	 * Tests if two Binary objects are not equal
	 * Second Binary object is null
	 */
	@Test
	void testEquality3() {
		Binary a = new Binary("100");
		Binary b = null;
		boolean actual = a.equality(b);
		assertFalse(actual);
	}
	
	/**
	 * Tests if two Binary objects are not equal
	 * Both objects do not have the same class
	 */
	@Test
	void testEquality4() {
		Binary a = new Binary("100");
		String b = "101";
		boolean actual = a.equality(b);
		assertFalse(actual);
	}
	
	/**
	 * Tests if an empty Binary constructor has
	 * "000000000000000000000000" as its number.
	 */
	@Test
	void testNumber() {
		Binary a = new Binary();
		String expected = "000000000000000000000000";
		String actual = a.getNumber();
		assertEquals(expected, actual);
	}
	
	

}
