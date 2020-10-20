package model.instructionType;

/**
 * This class is the stop instruction
 */
public class Stop implements Instruction {
	/** The destination register number. */
	private String myRd;

	/** The opcode of the instruction. */
	private String myOpcode;

	public Stop(String myOpcode, String myRd) {
		this.myRd = myRd;
		this.myOpcode = myOpcode;
	}

	@Override
	public String getOpcode() {
		return myOpcode;
	}

	@Override
	public String getRegister() {
		return myRd;
	}

	@Override
	public String getOperand() {
		return null;
	}

}
