package model;

/**
 * Calculator class that'll do addition, subtraction, multiplication, 
 * division, and modulo. Takes in two Binary and returns a Binary.
 * 
 * @author 
 * @version 2.3
 * 
 */
public class BinaryCalculator extends Calculator {

    public BinaryCalculator() {
        super();
    }

    public Binary add(Binary binOne, Binary binTwo) {
        return (Binary) super.executeOperation(binOne, binTwo, 2, "Add");
    }

    public Binary subtract(Binary binOne, Binary binTwo) {
        return (Binary) super.executeOperation(binOne, binTwo, 2, "Subtract");
    }

    public Binary multiply(Binary binOne, Binary binTwo) {
        return (Binary) super.executeOperation(binOne, binTwo, 2, "Multiply");
    }

    public Binary divide(Binary binOne, Binary binTwo) {
        return (Binary) super.executeOperation(binOne, binTwo, 2, "Divide");
    }

    public Binary mod(Binary binOne, Binary binTwo) {
        return (Binary) super.executeOperation(binOne, binTwo, 2, "Mod");
    }
}
