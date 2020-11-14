package model;

public class BinaryCalculator implements Calculator {

	@Override
	public Binary add(Object num1, Object num2) {
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Binary(resultStr);
	}

	@Override
	public Binary subtract(Object num1, Object num2){
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Binary(resultStr);
	}

	@Override
	public Binary multiply(Object num1, Object num2) {
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Binary(resultStr);
	}

	@Override
	public Binary divide(Object num1, Object num2) {
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt / bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		Binary resultBin = new Binary(resultStr);
		return resultBin;
	}
	
	@Override
	public Binary mod(Object num1, Object num2) {
		Binary a = (Binary) num1;
		Binary b = (Binary) num2;
		String aStr = a.getNumber(); //bin str
		String bStr = b.getNumber(); //bin str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt % bInt; // int
		String resultStr = Integer.toBinaryString(result); //bin str
		return new Binary(resultStr);
	}
}
