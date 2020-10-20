package model;
import model.instructionType.Instruction;
import java.util.Observable;
import java.util.Observer;
import utils.Converter;
import utils.Decode;
import view.SimulatorWindow;


public class ControlUnit implements Observer {

	private int PC = 0x000;
	private int AR = 0x000;
	private int IR = 0x000000;
	private static final int PC_COUNTER = 0x0001;
	private char charIn = '/';
	private myRunnable runnable = new myRunnable();
	private Thread waitThread = new Thread(runnable);
	SimulatorWindow window;


	private Decode decode = new Decode();
	private ArithmeticLogicUnit ALU = new ArithmeticLogicUnit();
	public MemoryDump memoryDump = new MemoryDump();
	private Instruction currentInstruction;

	public ControlUnit(SimulatorWindow window) {
		this.window = window;
	}
	public void startCycle() throws InterruptedException {
		this.IR = Integer.parseInt(memoryDump.fetch(this.PC),16);

		boolean stop = false;

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
			stop = true;
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
		if (!stop) {
			startCycle();
		}
	}

	private void executeAdd(Instruction instruction) {
		this.AR += Integer.parseInt(Converter.binToHex(instruction.getOperand()), 16);
		PC+=2;
	}

	private void executeCharIn(Instruction instruction) throws InterruptedException {
		//Wait for a character to be pressed in the terminal window
		waitThread.start();
	}

	private void executeCharOut(Instruction instruction) {
		String operand = instruction.getOperand();
		char character = (char)Converter.binToDecimal(operand);
		window.setTerminalArea(window.getTerminalArea() + "" + character);
		PC += 2;
	}

	private void executeLW(Instruction instruction) {
		int address = Converter.binToDecimal(instruction.getOperand());
		this.AR = Converter.hexToDecimal(memoryDump.getMemory(address));

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
		if (arg != null) {
			charIn = ((String) arg).charAt(0);
			waitThread.notify();
		}
	}
}


class myRunnable implements Runnable {
	@Override
	public void run() {
		synchronized(this) {
			try {
				wait();
			} catch (InterruptedException e) {
			}
		}
	}
}
