package model.instructionType;

/**
 * This class is the add instruction
 */

public class Add implements Instruction {
	/** The destination register number. */
	private String myRd;

	/** The operandSpecifier value. */
	private String operand;

	/** The opcode of the instruction. */
	private String myOpcode;

	public Add(String myOpcode, String myRd, String operandSpecifier) {
		this.myRd = myRd;
		this.operand = operandSpecifier;
		this.myOpcode = myOpcode;
	}

//	public execute()

	@Override
	public String getOpcode() {
		return myOpcode;
	}

	public String getOprand() {
		return myOpcode;
	}

	public String getRegister() {
		return myRd;
	}

}
