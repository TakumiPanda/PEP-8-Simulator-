package model;

import utils.Transformer;

/**
 * 
 * @author danieyll
 *
 */
public class Binary extends Number {

	/**
	 * Parameter constructor that creates a Binary Object.
	 * 
	 * @param num String object that'll set to the Binary object.
	 */
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
