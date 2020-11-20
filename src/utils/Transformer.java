package utils;
import model.Instruction;

import static java.lang.Integer.toHexString;


public class Transformer {

	/**
	 * Decode the binary string and create the appropriate Instruction object.
	 *
	 * @throws IllegalArgumentException if value for immediate is out of bound.
	 */
	public static Instruction decodeInstruction(final String binaryString) throws IllegalArgumentException {
		if (binaryString.equals("00000000")) { // Stop instruction
			return new Instruction("0000", "0000");
		}
		String operand = binaryString.substring(8,24);
		for (int i = 7; i >=0; i--) {
			String possibleOpcode = binaryString.substring(0,i);
			switch (possibleOpcode) {
				case "0000010":
				case "0000011":
				case "0000100":
				case "0000101":
				case "0000110":
				case "0000111":
				case "0001000":
				case "0001001":
				case "0001010":
				case "0001011":
					return new Instruction(possibleOpcode, "", ""+binaryString.charAt(7), operand);
				case "0001100":
				case "0001101":
				case "0001110":
				case "0001111":
				case "0010000":
				case "0010001": 
					return new Instruction(possibleOpcode, ""+binaryString.charAt(7), operand);
				case "00101":
				case "00110":
				case "00111":
				case "01000":
				case "01001":
				case "01010":
				case "01011":
				case "01100":
				case "01101":
					return new Instruction(possibleOpcode, binaryString.substring(5, 7), operand);
				case "0111":
				case "1000":
				case "1001":
				case "1010":
				case "1011":
				case "1100":
				case "1101":
				case "1110":
				case "1111":
					return new Instruction(possibleOpcode, ""+binaryString.charAt(4), binaryString.substring(5, 8), operand);
				} 
				
		}
		return new Instruction("", "");
	}

	/**
	 * binary---> hexDecimal
	 * 
	 * @param binary
	 * @return hexDecimal
	 */
	public static String binToHex(String binary) {
		StringBuilder hex = new StringBuilder();
		int count = 1;
		if (binary.length() < 1) {
			return hex.toString();
		}
		if (binary.replace(" ", "").length() % 2 != 0) {
			return "Invalid binary string.";
		}
		for (int i = 0; i < binary.replace(" ", "").length(); i += 4) {
			String binString = binary.replace(" ", "").substring(i, i + 4);
			hex.append(Integer.toString(binToDecimal(binString), 16).toUpperCase());
			if (count % 2 == 0) {
				hex.append(" ");
			}
			count++;
		}
		return hex.toString();
	}

	/**
	 * hexDecimal ---> decimal
	 * 
	 * @param hex
	 * @return Decimal
	 */
	public static int hexToDecimal(String hex) {
		String formattedString = hex.replaceAll("\\s","");
		return Integer.parseInt(formattedString, 16);
	}

	/**
	 * binary---> Decimal
	 * 
	 * @param bin
	 * @return Decimal
	 */
	public static int binToDecimal(String bin) {
		return Integer.parseInt(bin, 2);
	}

	/**
	 * hexDecimal ---> Binary
	 * 
	 * @param hex
	 * @return binary
	 */
	public static String hexToBinary(String hex) {
		StringBuilder binary = new StringBuilder();
		if (hex.length() < 1) {
			return binary.toString();
		}
		if (hex.replace(" ", "").length() % 2 != 0) {
			return "Invalid hex string.";
		}
		String[] hexCode = hex.replace(" ", "").split("");
		for (int i = 0; i < hexCode.length; i++) {
			int binVal = hexToDecimal(hexCode[i]);
			binary.append(String.format("%4s", Integer.toBinaryString(binVal)).replace(" ", "0"));
			binary.append(" ");
		}
		return binary.toString().replaceAll("\\s","");
	}

	/**
	 * decimal ---> binary
	 * 
	 * @param decimal
	 * @return binary
	 */
	public static String decimalToBinary(int decimal) {
		return Integer.toBinaryString(decimal);
	}

	/**
	 * decimal --> Hex
	 */
	public static String decimalToHex(int decimal) {
		return toHexString(decimal);
	}

	/**
	 * Formats the binary number by padding the value with 0/1 or sign extending it to the proper length
	 *
	 * @param address tring binary address.
	 * @return String binary address.
	 */
	public static String formatAddress(String address, int length, String extendCharacter) {
		int lengthExtended = length - address.length();
		for (int i = 0; i < lengthExtended; i++) {
			address = extendCharacter + address;
		}
		return address;
	}
}
