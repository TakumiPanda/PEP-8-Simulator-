package model.instructionType;

/**
 * This class is the interface of all instructions
 *
 * @author Ganghuan Huang
 * @version 24/05/2020
 */

public interface Instruction {
	/**
	 * Get the opcode.
	 *
	 * @return the opcode.
	 */
	String getOpcode();

	String getOperand();

	String getRegister();
}
