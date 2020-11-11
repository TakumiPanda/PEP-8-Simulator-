package model;

public class BinaryCalculator extends Calculator<Binary> {

	@Override
	public Binary add(Binary a, Binary b) {
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		Binary resultBin = new Binary(resultStr);
		return resultBin;
	}


	@Override
	public Binary subtract(Binary a, Binary b) {
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		Binary resultBin = new Binary(resultStr);
		return resultBin;
	}

	@Override
	public Binary multiply(Binary a, Binary b) {
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		Binary resultBin = new Binary(resultStr);
		return resultBin;
	}

	// should i include actual decimals in divisions?
	@Override
	public Binary divide(Binary a, Binary b) {
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		Binary resultBin = new Binary(resultStr);
		return resultBin;
	}
}
