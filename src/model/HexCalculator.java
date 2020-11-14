package model;

public class HexCalculator extends Calculator<Hexadecimal> {
	
	@Override
	public Hexadecimal add(Hexadecimal a, Hexadecimal b) {
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		Hexadecimal resultHex = new Hexadecimal(resultStr);
		return resultHex;
	}

	@Override
	public Hexadecimal subtract(Hexadecimal a, Hexadecimal b) {
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		Hexadecimal resultHex = new Hexadecimal(resultStr);
		return resultHex;
	}

	@Override
	public Hexadecimal multiply(Hexadecimal a, Hexadecimal b) {
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		Hexadecimal resultHex = new Hexadecimal(resultStr);
		return resultHex;
	}

	@Override
	public Hexadecimal divide(Hexadecimal a, Hexadecimal b) {
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt / bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		Hexadecimal resultHex = new Hexadecimal(resultStr);
		return resultHex;
	}
	
	@Override
	public Hexadecimal mod(Hexadecimal a, Hexadecimal b) {
		String aStr = a.getNumber(); //Hex str
		String bStr = b.getNumber(); //Hex str
		int aInt = Integer.parseInt(aStr, 16); //int
		int bInt = Integer.parseInt(bStr, 16); //int 
		int result = aInt % bInt; // int
		String resultStr = Integer.toHexString(result); //Hex str
		Hexadecimal resultHex = new Hexadecimal(resultStr);
		return resultHex;
	}
}
