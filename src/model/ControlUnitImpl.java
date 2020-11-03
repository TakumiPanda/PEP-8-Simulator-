package model;

import utils.Transformer;
import view.SimulatorWindow;


public class ControlUnitImpl implements ControlUnit {

	private int PC;
	private int AR;
	private int IR;

	private ArithmeticLogicUnitImpl ALU = new ArithmeticLogicUnitImpl();
	private SimulatorWindow window;
	private MemoryDumpImpl memoryDump = new MemoryDumpImpl();
	private Instruction currentInstruction;

	public ControlUnitImpl(SimulatorWindow window) {
		PC = ALU.getPC();
		AR = ALU.getAR();
		IR = ALU.getIR();
		this.window = window;
	}
	
	@Override
	public void startCycle() throws InterruptedException {
		this.IR = Integer.parseInt(memoryDump.fetch(this.PC),16);

		boolean stop = false;

		currentInstruction = Transformer.decodeInstruction(String.format("%06X", this.IR));

		switch (currentInstruction.getOpcode()) {

		case ("01110")://add
			executeAdd(currentInstruction);
			break;

		case ("01001")://char in
			executeCharIn(currentInstruction);
			break;

		case ("01010")://char out
			executeCharOut(currentInstruction);
			break;

		case ("11000")://load
			executeLW(currentInstruction);
			break;

		case ("00000")://stop
			stop = true;
			break;

		case ("10000")://sub
			executeSub(currentInstruction);
			break;

		case ("11100")://sw
			executeSW(currentInstruction);
			break;
		default:	
		}

		// PC must be updated to hold the address of the next instruction to be executed
		PC++;

		//Registers must be updated in the GUI Window.
		int[] updatedRegisters = ALU.getRegisters();
		updatedRegisters[0] = PC;
		updatedRegisters[1] = IR;
		updatedRegisters[2] = AR;
		ALU.updateState(updatedRegisters);

		if (!stop) {
			startCycle();
		}
	}

	public ArithmeticLogicUnitImpl getALU(){
		return ALU;
	}

	public MemoryDump getMemoryDump() {
		return memoryDump;
	}

	private void executeAdd(Instruction instruction) {
		this.AR += Integer.parseInt(Transformer.binToHex(instruction.getOperand()), 16);
		PC+=2;
	}

	private void executeCharIn(Instruction instruction) throws InterruptedException {
		//Wait for a character to be pressed in the terminal window
	}

	private void executeCharOut(Instruction instruction) {
		String operand = instruction.getOperand();
		char character = (char)Transformer.binToDecimal(operand);
		window.setTerminalArea(window.getTerminalArea() + "" + character);
		PC += 2;
	}

	private void executeLW(Instruction instruction) {
		int address = Transformer.binToDecimal(instruction.getOperand());
		this.AR = Transformer.hexToDecimal(memoryDump.getMemory(address));
	}

	private void executeSub(Instruction instruction) {
		if (instruction.getRegister().contentEquals("000")){ //immediate
			AR -= Integer.parseInt(Transformer.binToHex(instruction.getOperand()),16);
		} else if (instruction.getRegister().contentEquals("001")) { //direct
			int hexVal = Integer.parseInt(Transformer.binToHex(instruction.getOperand()),16);
			AR -= Transformer.hexToDecimal(memoryDump.getMemory(hexVal));
		}
	}

	private void executeSW(Instruction instruction) {
		String hexAddress = Transformer.binToHex(instruction.getOperand());
		memoryDump.setMemory(hexAddress,this.AR);
	}
}
