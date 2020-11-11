package model;

/**
 * This class is the interface of all instructions
 */

public class Instruction {
	/**
	 * The destination register number.
	 */
	private String myRegisterSpecifier;

	/** The operandSpecifier value. */
	private String myOperand;

	private String myAddressingMode;

	/**
	 * The opcode of the instruction.
	 */
	private String myOpcode;

	public Instruction(){}

	public Instruction(String opCode, String operand) {
		this.myOpcode = opCode;
		this.myOperand = operand;
	}

	public Instruction(String opCode, String registerSpec, String operand) {
		this.myOpcode = opCode;
		this.myRegisterSpecifier = registerSpec;
		this.myOperand = operand;
	}

	public Instruction(String opCode, String registerSpec, String addressingMode, String operand) {
		this.myOpcode = opCode;
		this.myRegisterSpecifier = registerSpec;
		this.myOperand = operand;
		this.myAddressingMode = addressingMode;
	}

	@Override
	public String toString() {
		return myOpcode + myOperand;
	}

	public String getOpcode() {
		return (myOpcode == null)? "": myOpcode;
	}
	public String getOperand() {
		return (myOperand == null)? "": myOperand;
	}
	public String getRegisterSpecifier() {
		return (myRegisterSpecifier == null)? "": myRegisterSpecifier;
	}	

	public String get5thBit() {
		return null;
	}
}
