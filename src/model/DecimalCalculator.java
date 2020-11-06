package model;

public class DecimalCalculator extends Calculator {
	private int decimal1;
	private int decimal2;
	private int result;
	
	private void initializeDecimals(String a, String b) {
		decimal1 = Integer.parseInt(a);
		decimal2 = Integer.parseInt(a);
	}

	@Override
	public String add(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 + decimal2;
		return result + "";
	}

	@Override
	public String subtract(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 - decimal2;
		return result + "";
	}

	@Override
	public String multiply(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 * decimal2;
		return result + "";
	}

	@Override
	public String divide(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 / decimal2;
		return result + "";
	}
	

}
