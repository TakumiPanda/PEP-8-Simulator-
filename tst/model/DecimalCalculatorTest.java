package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DecimalCalculatorTest {

	/**
	 * tests if 1 + 2 = 3 is correct in Decimal.
	 * Compares Decimal toString()
	 */ 
	@Test
	void testAdd() { 
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("3");
		Decimal actual = c.add(new Decimal("1"), new Decimal("2"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 3 - 2 = 1 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testSubtract() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("1");
		Decimal actual = c.subtract(new Decimal("3"), new Decimal("2"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 3 * 2 = 6 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testMultiply() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("6");
		Decimal actual = c.multiply(new Decimal("3"), new Decimal("2"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 4 / 2 = 2 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testDivide() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("2");
		Decimal actual = c.divide(new Decimal("4"), new Decimal("2"));
		assertTrue(expected.equality(actual));
	}
	
	/**
	 * tests if 1 % 10 = 1 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testMod() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("1");
		Decimal actual = c.mod(new Decimal("1"), new Decimal("10"));
		assertTrue(expected.equality(actual));
	}
}
