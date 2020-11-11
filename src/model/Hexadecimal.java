package model;

public class Hexadecimal extends Number{

	private String number;

	public Hexadecimal(String in)
	{
		super(in);
	}

	/**
	 * hexDecimal ---> decimal
	 * @param hex
	 * @return Decimal
	 */
	public static int hexToDecimal(String hex) {
		return Integer.parseInt(hex, 16);
	}

	/**
	 * hexDecimal ---> Binary
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

	@Override
	public String get(String in, int i) {
		if (i == 0)
		{
			return String.valueOf(hexToDecimal(in));
		}
		else
		{
			return hexToBinary(in);
		}
	}
}
