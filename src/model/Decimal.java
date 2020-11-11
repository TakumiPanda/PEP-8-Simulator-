package model;

import utils.Transformer;

public class Decimal extends Number {

	private String number;

	public Decimal(String num)
	{
		super(num);
	}

	@Override
	public String get(String in, int i) {
		if (i == 0)
		{
			int temp = Integer.parseInt(in);
			return Transformer.decimalToBinary(temp);
		}
		else
		{
			//placeholder for decToHex if wanted to add here
			return null;
		}
	}
}
