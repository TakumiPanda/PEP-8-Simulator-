package model;

import utils.Transformer;

/**
 * Object that represents the ALU in the PEP-8 simulator. Registers[0] ->
 * Program Counter
 */
public class ArithmeticLogicUnit {

	public int performAddition(int num1, int num2) {
		int sum = Transformer.transformBinaryToDecimal(num1) + Transformer.transformBinaryToDecimal(num2);
		return Transformer.transformDecimalToBinary(sum);
	}

	public int performSubtraction(int num1, int num2) {
		int sum = Transformer.transformBinaryToDecimal(num1) - Transformer.transformBinaryToDecimal(num2);
		return Transformer.transformDecimalToBinary(sum);
	}

	public int performMultiplication(int num1, int num2) {
		int sum = Transformer.transformBinaryToDecimal(num1) * Transformer.transformBinaryToDecimal(num2);
		return Transformer.transformDecimalToBinary(sum);
	}

	public int performDivision(int num1, int num2) {
		int sum = Transformer.transformBinaryToDecimal(num1) / Transformer.transformBinaryToDecimal(num2);
		return Transformer.transformDecimalToBinary(sum);
	}

	public int performAND(int num1, int num2) {
		return num1 & num2;
	}

	public int performNOT(int num1) {
		return ~num1;
	}

	public int performOR(int num1, int num2) {
		return num1 | num2;
	}
}
