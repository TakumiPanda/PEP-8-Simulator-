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
			case ("01110"):// add
				executeAdd(instruction);
				break;
			case ("01001"):// char in
				executeCharIn(instruction);
				break;
			case ("01010"):// char out
				executeCharOut(instruction);
				break;
			case ("11000"):// load
				executeLW(instruction);
				break;
			case ("00000"):// stop
				stopProgram = true;
				break;
			case ("10000"):// sub
				executeSub(instruction);
				break;
			case ("11100"):// sw
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
		this.AR += Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
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
}
