package model;

import java.util.Observable;
import java.util.Observer;

import model.instructionType.*;
import utils.Converter;
import utils.Decode;

public class ControlUnit implements Observer {

	private int PC = 0x000;
	private int AR = 0x000;
	private int IR = 0x000000;
	
	private Decode decode = new Decode();
	private ArithmeticLogicUnit ALU = new ArithmeticLogicUnit();
	public MemoryDump memoryDump = new MemoryDump();
	private Instruction currentInstruction;
	
	public void startCycle() {
		this.IR = Integer.parseInt(memoryDump.fetch(this.PC),16);

		currentInstruction = decode.decodeInstruction(String.format("%06X", this.IR));

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
			executeStop(currentInstruction);
			break;

		case ("10000")://sub
			executeSub(currentInstruction);
			break;

		case ("11100")://sw
			executeSW(currentInstruction);
		}

		// Get data if needed.

		// Execute the instruction.

		// PC must be updated to hold the address of the next instruction to be executed
		PC++;
		startCycle();
	}

	private void executeAdd(Instruction instruction) {
		this.AR += Integer.parseInt(Converter.binToHex(instruction.getOperand()), 16);
	}

	private void executeCharIn(Instruction instruction) {

	}

	private void executeCharOut(Instruction instruction) {

	}

	private void executeLW(Instruction instruction) {
		int address = Converter.binToDecimal(instruction.getOperand());
		this.AR = Converter.hexToDecimal(memoryDump.getMemory(address));

	}

	private void executeStop(Instruction instruction) {
	}

	private void executeSub(Instruction instruction) {
		if (instruction.getRegister().contentEquals("000")){ //immediate
			AR -= Integer.parseInt(Converter.binToHex(instruction.getOperand()),16);
		} else if (instruction.getRegister().contentEquals("001")) { //direct
			int hexVal = Integer.parseInt(Converter.binToHex(instruction.getOperand()),16);
			AR -= Converter.hexToDecimal(memoryDump.getMemory(hexVal));
		}
	}

	private void executeSW(Instruction instruction) {
		String hexAddress = Converter.binToHex(instruction.getOperand());
		memoryDump.setMemory(hexAddress,this.AR);
	}

	@Override
	public void update(Observable o, Object arg) {
		startCycle();
	}
}
