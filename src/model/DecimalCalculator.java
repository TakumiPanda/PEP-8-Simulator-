package model;

/**
 * Calculator class that'll do addition, subtraction, multiplication, 
 * division, and modulo. Takes in two Decimals and returns a Decimal.
 * 
 * @author 
 * @version 2.3
 *
 */  
public class DecimalCalculator extends Calculator {

	public DecimalCalculator(){
		super();
	}
	public Decimal add(Decimal decOne, Decimal decTwo) {
		return (Decimal) super.executeOperation(decOne, decTwo, 10, "Add");
	}
	public Decimal subtract(Decimal decOne, Decimal decTwo) {
		return (Decimal) super.executeOperation(decOne, decTwo, 10, "Subtract");
	}
	public Decimal multiply(Decimal decOne, Decimal decTwo) {
		return (Decimal) super.executeOperation(decOne, decTwo, 10, "Multiply");
	}
	public Decimal divide(Decimal decOne, Decimal decTwo) {
		return (Decimal) super.executeOperation(decOne, decTwo, 10, "Divide");
	}
	public Decimal mod(Decimal decOne, Decimal decTwo) {
		return (Decimal) super.executeOperation(decOne, decTwo, 10, "Mod");
	}
}
