package utils;

public class Transformer {
	public static int transformDecimalToBinary(int decimal) {
		return Integer.parseInt(Integer.toBinaryString(decimal));
	}

	public static int transformBinaryToDecimal(int binary) {
		String binaryStr = binary + "";
		if (binaryStr.charAt(0) == '1') {
			String invertedInt = invert(binaryStr);
			int decimalValue = Integer.parseInt(invertedInt, 2);
			decimalValue = (decimalValue + 1) * -1;
			return decimalValue;
		} else {
			return Integer.parseInt(binaryStr, 2);
		}
	}

	public static String invert(String binaryInt) {
		String result = binaryInt;
		result = result.replace("0", " "); // temp replace 0s
		result = result.replace("1", "0"); // replace 1s with 0s
		result = result.replace(" ", "1"); // put the 1s back in
		return result;
	}
}
