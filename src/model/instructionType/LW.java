
package model.instructionType;


/**
 * This class is the LW instruction
 */

public class LW implements Instruction {

	/** The destination register number. */
	private String myRd;

	/** The operandSpecifier value. */
	private String operandSpecifier;

	/** The opcode of the instruction. */
	private String myOpcode;


	public LW(String myOpcode, String myRd, String operandSpecifier) {
		this.myRd = myRd;
		this.operandSpecifier = operandSpecifier;
		this.myOpcode = myOpcode;
	}

	public String getOpcode() {
		return myOpcode;
	}

//	@Override
//	public void execute() {
//		//need to be implemented
//	}

}
