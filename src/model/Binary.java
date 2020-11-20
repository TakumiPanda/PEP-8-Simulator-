package model;

/**
 * Binary object class that extends Number.
 * 
 * @author
 * @version 2.4
 *
 */
public class Binary extends Number {

	public Binary() {
		super("000000000000000000000000");
	}

	/**
	 * Parameter constructor that creates a Binary Object.
	 * 
	 * @param num String object that'll set to the Binary object.
	 */
	public Binary(String num) {
		super(num);
	}
	
}
