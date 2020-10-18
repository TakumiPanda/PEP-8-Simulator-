package utils;

/**
 * this class contains methods convert between hex <---> binary binary <--->
 * decimal hex ---> decimal
 */
public class Converter {

	/**
	 * binary---> hexDecimal
	 * 
	 * @param binary
	 * @return hexDecimal
	 */
	public String binToHex(String binary) {
<<<<<<< HEAD
		int decimal = Integer.parseInt(binary, 2);
		return Integer.toString(decimal, 16).toUpperCase();
=======
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
>>>>>>> 1053ec6aaaeea5b442744770f0f4de1eb0802625
	}

	/**
	 * hexDecimal ---> decimal
	 * 
	 * @param hex
	 * @return Decimal
	 */
	public int hexToDecimal(String hex) {
		return Integer.parseInt(hex, 16);
	}

	/**
	 * binary---> Decimal
	 * 
	 * @param bin
	 * @return Decimal
	 */
	public int binToDecimal(String bin) {
		return Integer.parseInt(bin, 2);
	}

	/**
	 * hexDecimal ---> Binary
	 * 
	 * @param hex
	 * @return binary
	 */
	public String hexToBinary(String hex) {
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
	public String decimalToBinary(int decimal) {
		return Integer.toBinaryString(decimal);
	}

}
