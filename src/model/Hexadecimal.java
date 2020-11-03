package model;

import java.util.Objects;

public class Hexadecimal extends Numbers{

	private String in;

	public Hexadecimal(String in)
	{
		this.in = in;
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

	@Override
	public int compare(String in, String compare) {
		int compare1 = Integer.parseInt(in);
		int compare2 = Integer.parseInt(compare);
		if (compare1 == compare2)
		{
			return 0;
		}
		else if (compare1 < compare2)
		{
			return -1;
		}
		else
		{
			return 1;
		}
	}

	@Override
	public boolean equality(Object o) {
		if (this == o)
		{
			return true;
		}
		if (o == null)
		{
			return false;
		}
		if (getClass() != o.getClass())
		{
			return false;
		}
		Hexadecimal hex = (Hexadecimal) o;
		return Objects.equals(in, hex.in);
	}

}
