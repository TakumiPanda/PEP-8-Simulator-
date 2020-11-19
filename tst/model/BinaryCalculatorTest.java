package utils;

import model.Binary;
import model.BinaryCalculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BinaryCalculatorTest {

	/**
	 * tests if 1 + 2 = 3 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testAdd() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("11");
		Binary actual = c.add("1", "10");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 3 - 2 = 1 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testSubtract() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("01");
		Binary actual = c.subtract("11", "10");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 3 * 2 = 6 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testMultiply() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("110");
		Binary actual = c.multiply("11", "10");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 4 / 2 = 2 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testDivide() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("010");
		Binary actual = c.divide("100", "10");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	/**
	 * tests if 1 % 10 = 1 is correct in binary.
	 * Compares Binary toString()
	 */
	@Test
	void testMod() {
		BinaryCalculator c = new BinaryCalculator();
		Binary expected = new Binary("1");
		Binary actual = c.mod("1", "1010");
		assertEquals(expected.toString(), actual.toString(), "Expected: "+expected+", Actual: "+actual);
	}
	
	
	
	

}
