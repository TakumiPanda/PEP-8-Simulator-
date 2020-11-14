package model;

public class HexCalculator implements Calculator {
	@Override
	public Hexadecimal add(Object num1, Object num2) {
		Hexadecimal a = (Hexadecimal) num1;
		Hexadecimal b = (Hexadecimal) num2;
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}

	@Override
	public Hexadecimal subtract(Object num1, Object num2) {
		Hexadecimal a = (Hexadecimal) num1;
		Hexadecimal b = (Hexadecimal) num2;
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}

	@Override
	public Hexadecimal multiply(Object num1, Object num2) {
		Hexadecimal a = (Hexadecimal) num1;
		Hexadecimal b = (Hexadecimal) num2;
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}

	@Override
	public Hexadecimal divide(Object num1, Object num2) {
		Hexadecimal a = (Hexadecimal) num1;
		Hexadecimal b = (Hexadecimal) num2;
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt / bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}
	@Override
	public Hexadecimal mod(Object num1, Object num2) {
		Hexadecimal a = (Hexadecimal) num1;
		Hexadecimal b = (Hexadecimal) num2;
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt % bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		return new Hexadecimal(resultStr);
	}
}
