package model;

import utils.Transformer;

public class Hexadecimal extends Number {

	private String number;

	public Hexadecimal(String in)
	{
		super(in);
	}

	@Override
	public String get(String in, int i) {
		if (i == 0)
		{
			return String.valueOf(Transformer.hexToDecimal(in));
		}
		else
		{
			return Transformer.hexToBinary(in);
		}
	}
}
