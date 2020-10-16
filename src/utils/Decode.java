

package utils;

import utils.instructionType.*;

/**
 * This class reads the input binary strings and assign them to correct instruction obj

 */
public final class Decode {
	
	/**
	 * Decode the binary string and create the appropriate Instruction object.
	 * 
	 * @throws IllegalArgumentException if value for immediate is out of bound.
	 */
	public Instruction decodeInstruction(final String theString) throws IllegalArgumentException {

		Instruction instruction;
		
		// Consider all white spaces as a delimiter
		final String[] node = new String[3];
		node[0] = theString.substring(0,4);//assign opcode to node 0
		node[1] = theString.substring(5,7);// assign register to node 1
		node[2] = theString.substring(8,23);// assign operand specifier to node 2

		// Decode and create appropriate instruction
		switch(node[0]) {
			case "01110" ://instruction: add
				instruction = new Add(node[0], node[1], node[2]);
				break;

			case "00000" ://instruction: stop
				instruction = new Stop(node[0], node[1], node[2]);
				break;

			case "11000" ://instruction: load
				instruction = new LW(node[0], node[1], node[2]);
				break;

			case "11100" ://instruction: store
				instruction = new SW(node[0], node[1], node[2]);
				break;

			case "10000" ://instruction: subtract
				instruction = new Sub(node[0], node[1], node[2]);
				break;
			case "01001" ://instruction: character input
				instruction = new CharIn(node[0], node[1], node[2]);
				break;
			case "01010" ://instruction: character output
				instruction = new CharOut(node[0], node[1], node[2]);
				break;

			default :
				throw new IllegalArgumentException("Instruction " + node[0] +
												   " not supported.");
		}
		return instruction;

	}
}
