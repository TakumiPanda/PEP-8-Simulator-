package model.instructionType;

/**
 * This class is the Character input instruction
 */

public class CharIn implements Instruction {
	/** The destination register number. */
	private String myRd;

	/** The operandSpecifier value. */
	private String myOperand;

	/** The opcode of the instruction. */
	private String myOpcode;

	public CharIn(String myOpcode, String myRd, String operandSpecifier) {
		this.myRd = myRd;
		this.myOperand = operandSpecifier;
		this.myOpcode = myOpcode;
	}

	@Override
	public String getOpcode() {
		return myOpcode;
	}
	@Override
	public String getOperand() {
		return myOperand;
	}
	@Override
	public String getRegister() {
		return myRd;
	}

}