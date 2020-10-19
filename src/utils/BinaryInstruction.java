package utils;

import java.util.ArrayList;
import java.util.Collections;

// this class represents the BinaryInstruction Object
// takes a Hexadecimal instruction input and turns it into
// binary instruction
public class BinaryInstruction {
	// the instruction
	private String instruction;
	
	public BinaryInstruction(String hexadecimal) {
		instruction = toBinaryInstruction(hexadecimal);
	}
	
	// returns string representation of binaryInstruction
	public String toString() {
		return instruction;
	}

	// returns binary instruction given some hexadecimal instruction
	// parameter can have spaces or no spaces.
	public static String toBinaryInstruction(String hexadecimal) {
		String result = "";
		hexadecimal = hexadecimal.replaceAll("\\s","");
		char[] digits = hexadecimal.toCharArray();
		for (char digit : digits) {
			if (isLetter(digit)) {
				result = result + referToTable(digit);
			} else {
				int n = Integer.parseInt(digit + "");
				result = result + completeToFourBits(toBinary(n));
			}
		}
		return result;
	}
	
	// returns a 4 bit version of any binary that has less than 4 bits
	// ex: 11 --> 0011
	public static String completeToFourBits(String binary) {
		String result = binary;
		int len = binary.length();
		while (len < 4) {
			result = 0 + result;
			len++;
		}
		return result;
	}
	
	// returns binary string of a given decimal (0-9)
	public static String toBinary(int decimal) {
		String result = "";
		if (decimal == 0) {
			result = "0";
		}
		while (decimal > 0) {
			if (decimal % 2 == 1) {
				result = 1 + result;
				decimal = decimal - 1;
			} else {
				result = 0 + result;
			}
			decimal = decimal / 2;
		}
		return result;
	}
	
	// reference table for A,B,C,D,E,F.
	public static String referToTable(char c) {
		if (c == 'A') {
			return "1010";
		} else if (c == 'B') {
			return "1011";
		} else if (c == 'C') {
			return "1100";
		} else if (c == 'D') {
			return "1101";
		} else if (c == 'E') {
			return "1110";
		} else if (c == 'F') {
			return "1111";
		}
		return "";
	}
	
	// return true if a given char is a letter in hexadecimal
	public static boolean isLetter(char c) {
		return (c >= 65 && c <= 70);
	}
}
