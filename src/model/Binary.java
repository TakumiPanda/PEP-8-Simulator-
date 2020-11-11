package model;

import utils.Transformer;

public class Binary extends Number {

	private String number;

	public Binary(String num) {
		super(num);
	}
	public String get(String in, int i) {
		if (i == 0) {
			return Transformer.binToHex(in);
		} else {
			int temp = Transformer.binToDecimal(in);
			return String.valueOf(temp);
		}
	}
}
