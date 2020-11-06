package utils;

import model.Instruction;

public class Transformer {

	// double check this!
	private static Hexadecimal hexa;

	/**
	 * Decode the binary string and create the appropriate Instruction object.
	 *
	 * @throws IllegalArgumentException if value for immediate is out of bound.
	 */
	public static Instruction decodeInstruction(final String hex) throws IllegalArgumentException {
		String formatedHex = String.format("%06X", Integer.parseInt(hex,16));
		Instruction instruction;
		String[] node;
		hexa = new Hexadecimal(formatedHex);
		String theString = (hexa.get(formatedHex, 1)).replace(" ", "");
		node = new String[3];
		node[0] = theString.substring(0, 5);// assign opcode to node 0
		node[1] = theString.substring(6, 8);// assign register to node 1

		// Decode and create appropriate instruction
		switch (node[0]) {
			case "01110":
			case "11000":
			case "11100":
			case "10000":
			case "01001":
			case "01010":
			case "00000":
				node[2] = theString.substring(8, 24);// assign operand specifier to node 2
				instruction = new Instruction(node[0], node[1], node[2]);
				break;
			default:
				throw new IllegalArgumentException("Instruction " + node[0] + " not supported.");
		}
		return instruction;

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
		return Integer.parseInt(hex, 16);
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
		return binary.toString();
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
}
