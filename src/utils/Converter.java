package utils;

/**
 * this class contains methods convert between
 * hex <---> binary
 * binary <---> decimal
 * hex ---> decimal
 */
public class Converter {

	/**
	 * binary---> hexDecimal
	 * @param binary
	 * @return hexDecimal
	 */
	public String binToHex(String binary) {
		int decimal = Integer.parseInt(binary,2);
		return Integer.toString(decimal,16).toUpperCase();
	}

	/**
	 * hexDecimal ---> decimal
	 * @param hex
	 * @return Decimal
	 */
	public int hexToDecimal(String hex) {
		return Integer.parseInt(hex, 16);
	}

	/**
	 * binary---> Decimal
	 * @param bin
	 * @return Decimal
	 */
	public int binToDecimal(String bin){
		return Integer.parseInt(bin,2);
	}

	/**
	 * hexDecimal ---> Binary
	 * @param hex
	 * @return binary
	 */
	public String hexToBinary(String hex) {
		int num = (Integer.parseInt(hex, 16));
		return Integer.toBinaryString(num);
	}

	/**
	 * decimal ---> binary
	 * @param decimal
	 * @return binary
	 */
	public String decimalToBinary(int decimal){
		return Integer.toBinaryString(decimal);
	}

}
