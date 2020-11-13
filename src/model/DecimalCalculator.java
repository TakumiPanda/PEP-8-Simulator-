package model;

public class DecimalCalculator implements Calculator {

	@Override
	public Object add(Object a, Object b) {
		Decimal num1 = (Decimal) a;
		Decimal num2 = (Decimal) b;
		int sum = Integer.parseInt(num1.getNumber()) + Integer.parseInt(num2.getNumber());
		return new Decimal("" + sum);
	}

	@Override
	public Object subtract(Object a, Object b) {
		Decimal num1 = (Decimal) a;
		Decimal num2 = (Decimal) b;
		int sum = Integer.parseInt(num1.getNumber()) - Integer.parseInt(num2.getNumber());
		return new Decimal("" + sum);
	}

	@Override
	public Object multiply(Object a, Object b) {
		Decimal num1 = (Decimal) a;
		Decimal num2 = (Decimal) b;
		int sum = Integer.parseInt(num1.getNumber()) * Integer.parseInt(num2.getNumber());
		return new Decimal("" + sum);
	}

	@Override
	public Object divide(Object a, Object b) {
		Decimal num1 = (Decimal) a;
		Decimal num2 = (Decimal) b;
		int sum = Integer.parseInt(num1.getNumber()) / Integer.parseInt(num2.getNumber());
		return new Decimal("" + sum);
	}
}
