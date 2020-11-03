package model;

import java.util.Objects;

public class Decimal extends Numbers{

	private String in;

	public Decimal(String in)
	{
		this.in = in;
	}

	/**
	 * decimal ---> binary
	 * @param decimal
	 * @return binary
	 */
	public static String decimalToBinary(int decimal){
		return Integer.toBinaryString(decimal);
	}

	@Override
	public String get(String in, int i) {
		if (i == 0)
		{
			int temp = Integer.parseInt(in);
			return decimalToBinary(temp);
		}
		else
		{
			//placeholder for decToHex if wanted to add here
			return null;
		}
	}
	//Should we add decToHex?

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
		Decimal dec = (Decimal) o;
		return Objects.equals(in, dec.in);
	}
}
