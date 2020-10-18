package model;

import java.util.Observable;
import java.util.Observer;

import model.instructionType.Add;
import model.instructionType.Instruction;
import model.MemoryDump;
import utils.Converter;
import utils.Decode;

public class ControlUnit implements Observer {

	private int PC = 0x000;
	private int AR = 0x000;
	private int IR = 0x000000;
	private static final int PC_COUNTER = 0x0001;
	
	private Decode decode = new Decode();
	private ArithmeticLogicUnit ALU = new ArithmeticLogicUnit();
	public MemoryDump memoryDump = new MemoryDump();
	private Instruction currentInstruction;
	
	public void startCycle() {
		this.IR = Integer.parseInt(memoryDump.fetch(this.PC),16);

		currentInstruction = decode.decodeInstruction(String.format("%06X", this.IR));
		switch (currentInstruction.getClass().getName()) {
		case ("model.instructionType.Add"):
			executeAdd();
			break;
		case ("model.instructionType.CharIn"):
			executeCharIn();
			break;
		case ("model.instructionType.CharOut"):
			executeCharOut();
			break;
		case ("model.instructionType.LW"):
			executeLW();
			break;
		case ("model.instructionType.Stop"):
			executeStop();
			break;
		case ("model.instructionType.Sub"):
			executeSub();
			break;
		case ("model.instructionType.SW"):
			executeSW();
		}

		// Get data if needed.

		// Execute the instruction.

		// PC must be updated to hold the address of the next instruction to be executed
		PC++;
		startCycle();
	}

	private void executeAdd() {
		Add addInstruction = (Add) currentInstruction;
		this.AR += Integer.parseInt(Converter.binToHex(addInstruction.getOperand()), 16);
	}

	private void executeCharIn() {

	}

	private void executeCharOut() {

	}

	private void executeLW() {

	}

	private void executeStop() {
	}

	private void executeSub() {

	}

	private void executeSW() {

	}

	@Override
	public void update(Observable o, Object arg) {
		startCycle();
	}
}
