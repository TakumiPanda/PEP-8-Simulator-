package model;

public class HexCalculator implements Calculator {

	@Override
	public Hexadecimal add(Object num1, Object num2) {
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Hexadecimal(resultStr);
	}

	@Override
	public Hexadecimal subtract(Object num1, Object num2){
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Hexadecimal(resultStr);
	}

	@Override
	public Hexadecimal multiply(Object num1, Object num2) {
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Hexadecimal(resultStr);
	}

	// should i include actual decimals in divisions?
	@Override
	public Hexadecimal divide(Object num1, Object num2) {
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Hexadecimal(resultStr);
	}

}
