package model;

public class Decimal extends Number{

	private String number;

	public Decimal(String num)
	{
		super(num);
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
}
