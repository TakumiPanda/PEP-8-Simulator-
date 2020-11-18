package model;

/**
 * Calculator class that'll do addition, subtraction, multiplication, 
 * division, and modulo. Takes in two objects and returns a Hexadecimal.
 * 
 * @author 
 * @version 2.3
 *
 */
public class HexCalculator implements Calculator {

	/**
	 * Add two objects and returns a Hexadecimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override 
	public Hexadecimal add(Object num1, Object num2) {
		Hexadecimal a = new Hexadecimal(num1 + "");
		Hexadecimal b = new Hexadecimal(num2 + "");
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}

	/**
	 * Subtracts the second object with the first object.
	 * Returns a Hexadecimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Hexadecimal subtract(Object num1, Object num2) {
		Hexadecimal a = new Hexadecimal(num1 + "");
		Hexadecimal b = new Hexadecimal(num2 + "");
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}

	/**
	 * Multiply the two objects together. Returns a Hexadecimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Hexadecimal multiply(Object num1, Object num2) {
		Hexadecimal a = new Hexadecimal(num1 + "");
		Hexadecimal b = new Hexadecimal(num2 + "");
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}

	/**
	 * Divide the first object with the second object.
	 * Returns a Hexadecimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	public Hexadecimal divide(Object num1, Object num2) {
		Hexadecimal a = new Hexadecimal(num1 + "");
		Hexadecimal b = new Hexadecimal(num2 + "");
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt / bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}

	/**
	 * Mods the first object with the second object.
	 * Returns a Hexadecimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Hexadecimal mod(Object num1, Object num2) {
		Hexadecimal a = new Hexadecimal(num1 + "");
		Hexadecimal b = new Hexadecimal(num2 + "");
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt % bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}
}
