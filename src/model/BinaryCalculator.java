
public class BinaryCalculator extends Calculator {
	private int decimal1;
	private int decimal2;
	private int result;

	private void initializeDecimals(String a, String b) {
		decimal1 = Integer.parseInt(a, 2);
		decimal2 = Integer.parseInt(b, 2);
	}

	@Override
	public String add(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 + decimal2;
		return Integer.toBinaryString(result);
	}


	@Override
	public String subtract(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 - decimal2;
		return Integer.toBinaryString(result);
	}

	@Override
	public String multiply(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 * decimal2;
		return Integer.toBinaryString(result);
	}

	// should i include actual decimals in divisions?
	@Override
	public String divide(String a, String b) {
		initializeDecimals(a, b);
		result = decimal1 / decimal2;
		return Integer.toBinaryString(result);
	}
}
