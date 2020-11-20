package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DecimalTest {

	/**
	 * Tests if two Decimal objects are equal
	 */
	@Test
	void testEquality() {
		Decimal a = new Decimal("1");
		Decimal b = a;
		boolean actual = a.equality(b);
		assertTrue(actual);
	} 
	
	/**
	 * Tests if two Decimal objects are not equal
	 * Second Decimal object is not null
	 */
	@Test
	void testEquality2() {
		Decimal a = new Decimal("1");
		Decimal b = new Decimal("2");
		boolean actual = a.equality(b);
		boolean expected = false;
		assertEquals(expected, actual);
	}
	
	/**
	 * Tests if two Decimal objects are not equal
	 * Second Decimal object is null
	 */
	@Test
	void testEquality3() {
		Decimal a = new Decimal("1");
		Decimal b = null;
		boolean actual = a.equality(b);
		assertFalse(actual);
	}
	
	/**
	 * Tests if two Decimal objects are not equal
	 * Both objects do not have the same class
	 */
	@Test
	void testEquality5() {
		Decimal a = new Decimal("1");
		String b = "2";
		boolean actual = a.equality(b);
		assertFalse(actual);
	}
	 
	

}
