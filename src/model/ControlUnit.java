package model;

import java.util.Observable;
import java.util.Observer;

import model.instructionType.Instruction;
import model.MemoryDump;
import utils.Decode;

public class ControlUnit implements Observer {

	private int PC = 0x000;
	private int AR = 0x000;
	private int IR = 0x000000;
	private static final int PC_COUNTER = 0x0001;
	
	private Decode decode = new Decode();
	private ArithmeticLogicUnit ALU = new ArithmeticLogicUnit();
	public MemoryDump memoryDump = new MemoryDump();
	
	public void startCycle() {
		// Fetch the next instruction. This is the address of place in memory
		//int nextInstructionAddress = ALU.getPC();

		// Control Unit will go into memory and get the PC instruction
		//this.PC = memory[nextInstructionAddress];

		// Control Unit will load IR with the PC instruction
		
		this.IR = Integer.parseInt(memoryDump.fetch(this.PC),16);

//		System.out.println(memoryDump.fetch(this.PC));
//		System.out.println(this.IR);
//		System.out.println(String.format("%06X", this.IR));
		
		// Decode the instruction from IR.
		Instruction decodedInstruction = decode.decodeInstruction(String.format("%06X", this.IR));
		
		switch (decodedInstruction.getClass().getName()) {
		case ("Add"):
			executeAdd();
			break;
		case ("CharIn"):
			executeCharIn();
			break;
		case ("CharOut"):
			executeCharOut();
			break;
		case ("LW"):
			executeLW();
			break;
		case ("Stop"):
			executeStop();
			break;
		case ("Sub"):
			executeSub();
			break;
		case ("SW"):
			executeSW();
		}

		// Get data if needed.

		// Execute the instruction.

		// PC must be updated to hold the address of the next instruction to be executed
		ALU.getRegisters()[0]++;
	}

	private void executeAdd() {
		
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
