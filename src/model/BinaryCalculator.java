package model;

import utils.Transformer;

/**
 * Calculator class that'll do addition, subtraction, multiplication, 
 * division, and modulo. Takes in two objects and returns a Binary.
 * 
 * @author 
 * @version 2.3
 * 
 */
public class BinaryCalculator implements Calculator {


	/**
	 * Add two objects and returns a Binary.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Binary add(Object num1, Object num2) {
		String aStr = num1 + ""; //bin str
		String bStr = num2 + ""; //bin str
		Binary a = new Binary(aStr);
		Binary b = new Binary(bStr);
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		resultStr = (result < 0)? Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "1"):
			Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "0");
		return new Binary(resultStr);
	}

	/**
	 * Subtracts the second object with the first object.
	 * Returns a Binary.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Binary subtract(Object num1, Object num2){
		String aStr = num1 + ""; //bin str
		String bStr = num2 + ""; //bin str
		Binary a = new Binary(aStr);
		Binary b = new Binary(bStr);
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		resultStr = (result < 0)? Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "1"):
			Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "0");
		return new Binary(resultStr);
	}

	/**
	 * Multiply the two objects together. Returns a Binary.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Binary multiply(Object num1, Object num2) {
		String aStr = num1 + ""; //bin str
		String bStr = num2 + "";; //bin str
		Binary a = new Binary(aStr);
		Binary b = new Binary(bStr);
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		resultStr = (result < 0)? Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "1"):
			Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "0");
		return new Binary(resultStr);
	}

	/**
	 * Divide the first object with the second object.
	 * Returns a Binary.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Binary divide(Object num1, Object num2) {
		String aStr = num1 + ""; //bin str
		String bStr = num2 + ""; //bin str
		Binary a = new Binary(aStr);
		Binary b = new Binary(bStr);
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt / bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		resultStr = (result < 0)? Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "1"):
			Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "0");
		return new Binary(resultStr);
	}

	/**
	 * Mods the first object with the second object.
	 * Returns a Binary.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
	@Override
	public Binary mod(Object num1, Object num2) {
		String aStr = num1 + ""; //bin str
		String bStr = num2 + ""; //bin str
		Binary a = new Binary(aStr);
		Binary b = new Binary(bStr);
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt % bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		resultStr = (result < 0)? Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "1"):
			Transformer.formatBinaryAddress(resultStr, a.getNumber().length(), "0");
		return new Binary(resultStr);
	}
}
