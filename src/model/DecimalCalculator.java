package model;

/**
 * Calculator class that'll do addition, subtraction, multiplication, 
 * division, and modulo. Takes in two objects and returns a Decimal.
 * 
 * @author 
 * @version 2.3
 *
 */
public class DecimalCalculator implements Calculator {

	/**
	 * Add two objects and returns a Decimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
    @Override
    public Object add(Object a, Object b) {
        Decimal num1 = (Decimal) a;
        Decimal num2 = (Decimal) b;
        int sum = Integer.parseInt(num1.getNumber()) + Integer.parseInt(num2.getNumber());
        return new Decimal("" + sum);
    }

	/**
	 * Subtracts the second object with the first object.
	 * Returns a Decimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
    @Override
    public Object subtract(Object a, Object b) {
        Decimal num1 = (Decimal) a;
        Decimal num2 = (Decimal) b;
        int sum = Integer.parseInt(num1.getNumber()) - Integer.parseInt(num2.getNumber());
        return new Decimal("" + sum);
    }

	/**
	 * Multiply the two objects together. Returns a Decimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
    @Override
    public Object multiply(Object a, Object b) {
        Decimal num1 = (Decimal) a;
        Decimal num2 = (Decimal) b;
        int sum = Integer.parseInt(num1.getNumber()) * Integer.parseInt(num2.getNumber());
        return new Decimal("" + sum);
    }

	/**
	 * Divide the first object with the second object.
	 * Returns a Decimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
    @Override
    public Object divide(Object a, Object b) {
        Decimal num1 = (Decimal) a;
        Decimal num2 = (Decimal) b;
        int sum = Integer.parseInt(num1.getNumber()) / Integer.parseInt(num2.getNumber());
        return new Decimal("" + sum);
    }

	/**
	 * Mods the first object with the second object.
	 * Returns a Decimal.
	 * 
	 * @param num1 first object
	 * @param num2 second object
	 */
    @Override
    public Object mod(Object a, Object b) {
        Decimal num1 = (Decimal) a;
        Decimal num2 = (Decimal) b;
        int sum = Integer.parseInt(num1.getNumber()) % Integer.parseInt(num2.getNumber());
        return new Decimal("" + sum);
    }
}
