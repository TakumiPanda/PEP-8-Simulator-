package model;

public class DecimalCalculator extends Calculator<Decimal> {
	
	@Override
	public Decimal add(Decimal a, Decimal b) {
		String aStr = a.getNumber(); //Dec str
		String bStr = b.getNumber(); //Dec str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt + bInt; // int
		String resultStr = Integer.toString(result); //Dec str
		Decimal resultDec = new Decimal(resultStr);
		return resultDec;
	}

	@Override
	public Decimal subtract(Decimal a, Decimal b) {
		String aStr = a.getNumber(); //Dec str
		String bStr = b.getNumber(); //Dec str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt - bInt; // int
		String resultStr = Integer.toString(result); //Dec str
		Decimal resultDec = new Decimal(resultStr);
		return resultDec;
	}

	@Override
	public Decimal multiply(Decimal a, Decimal b) {
		String aStr = a.getNumber(); //Dec str
		String bStr = b.getNumber(); //Dec str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt * bInt; // int
		String resultStr = Integer.toString(result); //Dec str
		Decimal resultDec = new Decimal(resultStr);
		return resultDec;
	}

	@Override
	public Decimal divide(Decimal a, Decimal b) {
		String aStr = a.getNumber(); //Dec str
		String bStr = b.getNumber(); //Dec str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt / bInt; // int
		String resultStr = Integer.toString(result); //Dec str
		Decimal resultDec = new Decimal(resultStr);
		return resultDec;
	}
	
	@Override
	public Decimal mod(Decimal a, Decimal b) {
		String aStr = a.getNumber(); //Dec str
		String bStr = b.getNumber(); //Dec str
		int aInt = Integer.parseInt(aStr, 2); //int
		int bInt = Integer.parseInt(bStr, 2); //int 
		int result = aInt % bInt; // int
		String resultStr = Integer.toString(result); //Dec str
		Decimal resultDec = new Decimal(resultStr);
		return resultDec;
	}
}
