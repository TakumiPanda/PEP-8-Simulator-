package model;

import java.util.Objects;

public class Binary extends Numbers{

	private String in;

	public Binary(String in)
	{
		this.in = in;
	}

    /**
	 * binary---> hexDecimal
	 * @param binary
	 * @return hexDecimal
	 */
	public String binToHex(String binary) {
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
	 * binary---> Decimal
	 * @param bin
	 * @return Decimal
	 */
	public int binToDecimal(String bin){
		return Integer.parseInt(bin,2);
	}

	public String get(String in, int i) {
		if (i == 0)
		{
			return binToHex(in);
		}
		else
		{
			int temp = binToDecimal(in);
			return String.valueOf(temp);
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
		Binary bin = (Binary) o;
		return Objects.equals(in, bin.in);
	}
}
