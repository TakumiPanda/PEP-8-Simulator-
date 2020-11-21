package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class HexCalculatorTest {

	/**
	 * tests if A + A = 14 is correct (10 + 10).
	 * Compares Hexadecimal toString()
	 */
	@Test
	void testAdd() {
		HexCalculator c = new HexCalculator();
		Hexadecimal expected = new Hexadecimal("14");
		Hexadecimal actual = c.add(new Hexadecimal("A"), new Hexadecimal( "A"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if B - A = 1 is correct (11 - 10 = 1).
	 * Compares Hexadecimal toString()
	 */
	@Test
	void testSubtract() {
		HexCalculator c = new HexCalculator();
		Hexadecimal expected = new Hexadecimal("1");
		Hexadecimal actual = c.subtract(new Hexadecimal("B"), new Hexadecimal( "A"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if C * D = 9C is correct (12 * 13 = 156).
	 * Compares Hexadecimal toString()
	 */
	@Test
	void testMultiply() {
		HexCalculator c = new HexCalculator();
		Hexadecimal expected = new Hexadecimal("9c");
		Hexadecimal actual = c.multiply(new Hexadecimal("C"), new Hexadecimal( "D"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 15 / 7 = 3 is correct (21 / 7 = 3)
	 * Compares Hexadecimal toString()
	 */
	@Test
	void testDivide() {
		HexCalculator c = new HexCalculator();
		Hexadecimal expected = new Hexadecimal("3");
		Hexadecimal actual = c.divide(new Hexadecimal("15"), new Hexadecimal( "7"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 2D % 5 = 0 is correct (45 % 5 = 0)
	 * Compares Hexadecimal toString()
	 */
	@Test
	void testMod() {
		HexCalculator c = new HexCalculator();
		Hexadecimal expected = new Hexadecimal("0");
		Hexadecimal actual = c.mod(new Hexadecimal("2D"), new Hexadecimal( "5"));
		assertTrue(expected.equality(actual));
	}
}















