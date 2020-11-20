package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class HexadecimalTest {

	/**
	 * Tests if two Hexadecimal objects are equal
	 */
	@Test
	void testEquality() {
		Hexadecimal a = new Hexadecimal("1");
		Hexadecimal b = a;
		boolean actual = a.equality(b);
		assertTrue(actual);
	}
	
	/**
	 * Tests if two Hexadecimal objects are not equal
	 * Second Hexadecimal object is null
	 */
	@Test
	void testEquality2() {
		Hexadecimal a = new Hexadecimal("1");
		Hexadecimal b = null;
		boolean actual = a.equality(b);
		assertFalse(actual);
	}
	
	/**
	 * Tests if two Decimal objects are not equal
	 * Both objects do not have the same class
	 */
	@Test
	void testEquality3() {
		Hexadecimal a = new Hexadecimal("1");
		String b = "2";
		boolean actual = a.equality(b);
		assertFalse(actual);
	}
	
	/**
	 * Tests if two Decimal objects are not equal
	 * Second Hexadecimal object is not null
	 */
	@Test
	void testEquality4() {
		Hexadecimal a = new Hexadecimal("1");
		Hexadecimal b = new Hexadecimal("2");
		boolean actual = a.equality(b);
		assertFalse(actual);
	}

}
