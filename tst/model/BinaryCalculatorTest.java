package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class BinaryCalculatorTest {

	/**
	 * tests if 1 + 2 = 3 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testAdd() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("11");
		Binary actual = c.add(new Binary("1"), new Binary("10"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 3 - 2 = 1 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testSubtract() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("01");
		Binary actual = c.subtract(new Binary("11"), new Binary("10"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 3 * 2 = 6 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testMultiply() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("110");
		Binary actual = c.multiply(new Binary("11"), new Binary("10"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 4 / 2 = 2 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testDivide() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("010");
		Binary actual = c.divide(new Binary("100"), new Binary("10"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 1 % 10 = 1 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testMod() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("1");
		Binary actual = c.mod(new Binary("1"), new Binary("10"));
		assertTrue(expected.equality(actual));
	}
}
