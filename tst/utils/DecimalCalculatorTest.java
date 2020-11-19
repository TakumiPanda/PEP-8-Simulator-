package utils;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DecimalCalculatorTest {

	/**
	 * tests if 1 + 2 = 3 is correct in Decimal.
	 * Compares Decimal toString()
	 */ 
	@Test
	void testAdd() { 
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("3");
		Decimal actual = (Decimal) c.add("1", "2");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 3 - 2 = 1 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testSubtract() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("1");
		Decimal actual = (Decimal) c.subtract("3", "2");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 3 * 2 = 6 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testMultiply() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("6");
		Decimal actual = (Decimal) c.multiply("3", "2");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 4 / 2 = 2 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testDivide() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("2");
		Decimal actual = (Decimal) c.divide("4", "2");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 1 % 10 = 1 is correct in Decimal.
	 * Compares Decimal toString()
	 */
	@Test
	void testMod() {
		DecimalCalculator c = new DecimalCalculator();
		Decimal expected = new Decimal("1");
		Decimal actual = (Decimal) c.mod("1", "10");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	

}
