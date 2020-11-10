package model;

import utils.Transformer;
import view.SimulatorWindow;

public class ControlUnitImpl implements ControlUnit {

	private int PC = 0x000;
	private int AR = 0x000;
	private int IR = 0x000000;

	private boolean stopProgram = false;

	private ArithmeticLogicUnitImpl ALU = new ArithmeticLogicUnitImpl();
	private SimulatorWindow window;
	private MemoryDumpImpl memoryDump = new MemoryDumpImpl();

	public ControlUnitImpl(SimulatorWindow window) {
		this.window = window;
	}

	@Override
	public void executeSingleInstruction(String instr) {
		System.out.println("Executing single instruction");
		Instruction instruction = Transformer.decodeInstruction(instr);
		executeInstruction(instruction);
	}

	@Override
	public void startCycle() throws InterruptedException {
		this.IR = Integer.parseInt(memoryDump.fetch(this.PC), 16);

		Instruction currentInstruction = Transformer.decodeInstruction(memoryDump.fetch(this.PC));

		executeInstruction(currentInstruction);

		if (stopProgram == false) {
			startCycle();
		}
	}

	@Override
	public Instruction getCurrentInstructionOperand() {
		return Transformer.decodeInstruction(String.format("%06X", this.IR));
	}

	@Override
	public ArithmeticLogicUnitImpl getALU() {
		return ALU;
	}

	@Override
	public MemoryDump getMemoryDump() {
		return memoryDump;
	}

	private void executeInstruction(Instruction instruction) {
		switch (instruction.getOpcode()) {
			case ("0000"):// stop & branch
				executeStopBranch(instruction);
				break;
			case ("0001"):// shift, negate, invert, branch
				executeShiftNegateInvertBranch(instruction);
				break;
			case ("0010"):// rotate & Op trap
				executeRotateOpTrap(instruction);
				break;
			case ("0011"):// decOut
				executeDecOut(instruction);
				break;
			case ("0100"):// char in
				executeCharIn(instruction);
				break;
			case ("0101"):// char out
				executeCharOut(instruction);
				break;
			case ("0111"):// add
				executeAdd(instruction);
				break;
			case ("1000"):// sub
				executeSub(instruction);
				break;
			case ("1001"):// and
				executeAnd(instruction);
				break;
			case ("1010"):// or
				executeOr(instruction);
				break;
			case ("1011"):// compare
				executeCompare(instruction);
				break;
			case ("1100"):// load memory
				executeLW(instruction);
				break;
			case ("1110"):// sw
				executeSW(instruction);
				break;
			default:
		}
		// PC must be updated to hold the address of the next instruction to be executed
		PC++;

		// Registers must be updated in the GUI Window.
		int[] updatedRegisters = ALU.getRegisters();
		updatedRegisters[0] = PC;
		updatedRegisters[1] = IR;
		updatedRegisters[2] = AR;
		ALU.updateState(updatedRegisters);
	}

	private void executeAdd(Instruction instr) {
		if (instr.getRegister().contentEquals("000")) { // immediate
			this.AR += Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
		}else if (instr.getRegister().contentEquals("001")) { // direct
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			AR += Transformer.hexToDecimal(memoryDump.getMemory(hexVal));
		}
		PC += 2;
	}

	private void executeCharIn(Instruction instr) {
		// Wait for a character to be pressed in the terminal window
	}

	private void executeCharOut(Instruction instr) {
		String operand = instr.getOperand();
		char character = (char) Transformer.binToDecimal(operand);
		window.setTerminalArea(window.getTerminalArea() + "" + character);
		PC += 2;
	}

	private void executeLW(Instruction instr) {
		int address = Transformer.binToDecimal(instr.getOperand());
		this.AR = Transformer.hexToDecimal(memoryDump.getMemory(address));
	}

	private void executeSub(Instruction instr) {
		if (instr.getRegister().contentEquals("000")) { // immediate
			AR -= Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
		} else if (instr.getRegister().contentEquals("001")) { // direct
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			AR -= Transformer.hexToDecimal(memoryDump.getMemory(hexVal));
		}
	}

	private void executeSW(Instruction instr) {
		String hexAddress = Transformer.binToHex(instr.getOperand());
		memoryDump.setMemory(hexAddress, this.AR);
	}
	
	private void executeAnd(Instruction instr) {
		if (instr.get5thBit().contentEquals("0")){ //AC
			if (instr.getRegister().contentEquals("000")) { //immediate
			
			}else if(instr.getRegister().contentEquals("001")) { //memory

			}
		}else if(instr.get5thBit()){ //Reg
			if (instr.getRegister().contentEquals("000")) { //immediate
				
			}else if(instr.getRegister().contentEquals("001")) { //memory
				
			}
		}
	}
	
	private void executeOr(Instruction instr) {

	}
	
	private void executeCompare(Instruction instr) {
		
	}
	
	private void executeRotateOpTrap(Instruction instr) {
		if (instr.get5thBit().contentsEquals("0")) {
			if ((instr.getRegister().substring(0,1)).contentEquals("00")) { // rotate left
				String ARString = Integer.toBinaryString(AR);
				String rotatedARString = ARString.substring(1, ARString.length()) + ARString.substring(0); 
				this.AR = Transformer.binToDecimal(rotatedARString);
			}else if ((instr.getRegister().substring(0,1)).contentEquals("01")) { // rotate right
				String ARString = Integer.toBinaryString(AR);
				String rotatedARString = ARString.substring(ARString.length()-1) + ARString.substring(0, ARString.length()-1); 
				this.AR = Transformer.binToDecimal(rotatedARString);
			}else if ((instr.getRegister().substring(0)).contentEquals("1")) { // Unary no OP trap
				
			}
		}else if (instr.get5thBit().contentsEquals("1")) { //Nonunary no OP trap			
			
		}
	}
	
	private void executeDecOut(Instruction instr) {
		if (instr.getRegister().contentEquals("000")) { // immediate
			String operand = instr.getOperand();
			int dec = Transformer.binToDecimal(operand);
			window.setTerminalArea(window.getTerminalArea() + "" + dec);
		}else if (instr.getRegister().contentEquals("001")) { // memory
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			int dec = Transformer.hexToDecimal(memoryDump.getMemory(hexVal));
			window.setTerminalArea(window.getTerminalArea() + "" + dec);
		}
		PC += 2;
	}
	
	private void executeStopBranch(Instruction instr) {
		if (instr.get5thBit().contentsEquals("0")) {
			if (instr.getRegister().contentEquals("000")) { //Stop
				stopProgram = true;
				return;
			}else if ((instr.getRegister().substring(0,1)).contentEquals("10")) { //branch specified address/unconditional
				
			}else if ((instr.getRegister().substring(0,1)).contentEquals("11")) { //branch if less-than-or-equal
							
			}
		}else if (instr.get5thBit().contentEquals("1")) {
			if ((instr.getRegister().substring(0,1)).contentEquals("00")) { //branch if less than
				
			}else if ((instr.getRegister().substring(0,1)).contentEquals("01")) { //branch if equal

			}else if ((instr.getRegister().substring(0,1)).contentEquals("10")) { //branch if not equal

			}else if ((instr.getRegister().substring(0,1)).contentEquals("11")) { //branch if greater or equal
				
			}
		}
	}
	
	private void executeShiftNegateInvertBranch(Instruction instr) {
		if (instr.get5thBit().contentsEquals("0")) {
			if (instr.getRegister().contentEquals("00")) { //branch greater
				
			}else if ((instr.getRegister().substring(0,1)).contentEquals("10")) { //branch if V (overflow)
				
			}else if ((instr.getRegister().substring(0,1)).contentEquals("11")) { //branch if C (carry)
							
			}
		}else if (instr.get5thBit().contentEquals("1")) {
			if ((instr.getRegister().substring(0,1)).contentEquals("00")) { //bitwise invert
				this.AR = ~AR & 0xff;
			}else if ((instr.getRegister().substring(0,1)).contentEquals("01")) { //negate

			}else if ((instr.getRegister().substring(0,1)).contentEquals("10")) { //shift left
				this.AR = (AR << 1);
			}else if ((instr.getRegister().substring(0,1)).contentEquals("11")) { //shift right
				this.AR = (AR >> 1);
			}
		}
	}

}
