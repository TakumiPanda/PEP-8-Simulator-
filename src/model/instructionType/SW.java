
package model.instructionType;

/**
 * This class is the SW instruction
 */

public class SW implements Instruction {

	/** The destination register number. */
	private String myRd;

	/** The operandSpecifier value. */
	private String myOperand;
	/** The opcode of the instruction. */
	private String myOpcode;

	public SW(String myOpcode, String myRd, String operandSpecifier) {
		this.myRd = myRd;
		this.myOperand = operandSpecifier;
		this.myOpcode = myOpcode;
	}

	@Override
	public String getOpcode() {
		return myOpcode;
	}

	public String getOperand() {
		return myOperand;
	}

	public String getRegister() {
		return myRd;
	}
}
