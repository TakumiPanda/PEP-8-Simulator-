package model;

/**
 * Calculator class that'll do addition, subtraction, multiplication, 
 * division, and modulo. Takes in two Hexadecimal and returns a Hexadecimal.
 * 
 * @author 
 * @version 2.3
 *
 */
public class HexCalculator extends Calculator {

    public HexCalculator(){
        super();
    }
    public Hexadecimal add(Hexadecimal hexOne, Hexadecimal hexTwo) {
        return (Hexadecimal) super.executeOperation(hexOne, hexTwo, 16, "Add");
    }

    public Hexadecimal subtract(Hexadecimal hexOne, Hexadecimal hexTwo) {
        return (Hexadecimal) super.executeOperation(hexOne, hexTwo, 16, "Subtract");
    }

    public Hexadecimal multiply(Hexadecimal hexOne, Hexadecimal hexTwo) {
        return (Hexadecimal) super.executeOperation(hexOne, hexTwo, 16, "Multiply");
    }

    public Hexadecimal divide(Hexadecimal hexOne, Hexadecimal hexTwo) {
        return (Hexadecimal) super.executeOperation(hexOne, hexTwo, 16, "Divide");
    }

    public Hexadecimal mod(Hexadecimal hexOne, Hexadecimal hexTwo) {
        return (Hexadecimal) super.executeOperation(hexOne, hexTwo, 16, "Mod");
    }
}
