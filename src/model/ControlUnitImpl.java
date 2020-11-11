package model;

import java.util.Map;
import static java.util.Map.entry;
import utils.Transformer;
import view.SimulatorWindow;

public class ControlUnitImpl implements ControlUnit {

	private Binary PC = new Binary("0000000000000000");
	private Binary AR = new Binary("0000000000000000");
	private Binary IR = new Binary("0000000000000000");

	private boolean stopProgram = false;

	/** N bit is set if result of operation in negative. */
	private Binary N = new Binary("" + 0);
	/** C bit is set if operation produced a carry (borrow on subtraction. */
	private Binary C = new Binary("" + 0);
	/** V bit is set if operation produced an overflow. */
	private Binary V = new Binary("" + 0);
	/** Z bit is set if result of operation is zero (All bits = 0 */
	private Binary Z = new Binary("" + 0);

	private BinaryCalculator binCalculator = new BinaryCalculator();
	private ArithmeticLogicUnitImpl ALU = new ArithmeticLogicUnitImpl();
	private SimulatorWindow window;
	private MemoryDumpImpl memoryDump = new MemoryDumpImpl();

	public ControlUnitImpl(SimulatorWindow window) {
		this.window = window;
	}

	@Override
	public void executeSingleInstruction(String instr) {
		Instruction instruction = Transformer.decodeInstruction(instr);
		executeInstruction(instruction);
	}

	@Override
	public void startCycle() throws InterruptedException {
		String pcStr = memoryDump.fetch(Integer.parseInt(this.PC.getNumber()));
		this.IR.setNumber(pcStr);
		
		Instruction currentInstruction = Transformer.decodeInstruction(pcStr);

		executeInstruction(currentInstruction);

		if (stopProgram == false) {
			startCycle();
		}
	}

	@Override
	public String getCurrentInstruction() {
		return this.IR.getNumber();	
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
		this.PC.setNumber(binCalculator.add(this.PC.getNumber(), "1"));

		// Registers must be updated in the GUI Window.
		Binary[] updatedRegisters = ALU.getRegisters();
		updatedRegisters[0] = this.PC; 
		updatedRegisters[1] = this.IR; 
		updatedRegisters[2] = this.AR; 
		ALU.updateState(updatedRegisters);
	}

	private void executeAdd(Instruction instr) {
		setFlagsForArithmetic(new Binary("" + this.AR), new Binary(instr.getOperand()), "Addition");
		if (instr.getRegisterSpecifier().contentEquals("000")) { // immediate
			Binary operandValue = new Binary(instr.getOperand());
			this.AR = binCalculator.add(operandValue,AR);
		} else if (instr.getRegisterSpecifier().contentEquals("001")) { // direct
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
			this.AR = binCalculator.add(memVal, AR);
		}
		incrementPC();
	}

	private void executeCharIn(Instruction instr) {
		// Wait for a character to be pressed in the terminal window
	}

	private void executeCharOut(Instruction instr) {
		String operand = instr.getOperand();
		char character = (char) Transformer.binToDecimal(operand);
		window.setTerminalArea(window.getTerminalArea() + "" + character);
		incrementPC();
	}

	private void executeLW(Instruction instr) {
		int address = Transformer.binToDecimal(instr.getOperand());
		Binary memBin = new Binary(Integer.toBinaryString(
				Transformer.hexToDecimal(memoryDump.getMemory(address))));
		this.AR = memBin;
	}

	private void executeSub(Instruction instr) {

		setFlagsForArithmetic(new Binary("" + this.AR), new Binary(instr.getOperand()), "Subtraction");
		
		if (instr.getRegisterSpecifier().contentEquals("000")) { // immediate
			Binary operandVal = new Binary (instr.getOperand());
			this.AR = binCalculator.subtract(AR, operandVal);
		} else if (instr.getRegisterSpecifier().contentEquals("001")) { // direct
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
			this.AR = binCalculator.subtract(AR, memVal);
		}
	}

	private void executeSW(Instruction instr) {
		String hexAddress = Transformer.binToHex(instr.getOperand());
		memoryDump.setMemory(hexAddress, Integer.parseInt(this.AR.getNumber(),2)); //double check if radix is 2 or 16
	}

	private void executeAnd(Instruction instr) {
		if (instr.get5thBit().contentEquals("0")){ //AC
			if (instr.getRegisterSpecifier().contentEquals("000")) { //AR & immediate
				int valueInt = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
				int ARint = Integer.parseInt(AR.getNumber(),2);
				int andInt = (ARint & valueInt);
				String andStr = Integer.toBinaryString(andInt);
				Binary andBin = new Binary(andStr);
				this.AR = andBin;
			}else if(instr.getRegisterSpecifier().contentEquals("001")) { //AR & memory
				int address = Transformer.binToDecimal(instr.getOperand());
				int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
				this.AR = (AR & value);
			}
		}
//		currently implementing later: Reason (Don't know index register and how it works)
//		}else if(instr.get5thBit().contentEquals("1")){ //Reg
//			if (instr.getRegisterSpecifier().contentEquals("000")) { //Reg & immediate
//				
//			}else if(instr.getRegisterSpecifier().contentEquals("001")) { //Reg & memory
//				
//			}
//		}
	}

	private void executeOr(Instruction instr) {
		if (instr.get5thBit().contentEquals("0")){ //AC
		if (instr.getRegisterSpecifier().contentEquals("000")) { //AR | immediate
			int value = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
			this.AR = (AR | value);
		}else if(instr.getRegisterSpecifier().contentEquals("001")) { //AR | memory
			int address = Transformer.binToDecimal(instr.getOperand());
			int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
			this.AR = (AR | value);
		}
	}else if(instr.get5thBit().contentEquals("1")){ //Reg
		if (instr.getRegisterSpecifier().contentEquals("000")) { //Reg | immediate
			
		}else if(instr.getRegisterSpecifier().contentEquals("001")) { //Reg | memory
			
		}
	}
	if (instr.get5thBit().contentEquals("0")) { // AC
		if (instr.getRegisterSpecifier().contentEquals("000")) { // immediate

		} else if (instr.getRegisterSpecifier().contentEquals("001")) { // memory

		}
	} else if (instr.get5thBit().equals("0")) { // Reg
		if (instr.getRegisterSpecifier().contentEquals("000")) { // immediate

		} else if (instr.getRegisterSpecifier().contentEquals("001")) { // memory

		}
	}
	}

	private void executeCompare(Instruction instr) {
		Decimal dec;
		if (instr.get5thBit().contentEquals("0")){ //AC
			if (instr.getRegisterSpecifier().contentEquals("000")) { //AR Compare immediate
				int value = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
				this.AR = dec.compare(Integer.toString(value),Integer.toString(AR));
			}else if(instr.getRegisterSpecifier().contentEquals("001")) { //AR Compare memory
				int address = Transformer.binToDecimal(instr.getOperand());
				int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
				this.AR = dec.compare(Integer.toString(value), Integer.toString(AR));
			}
		}else if(instr.get5thBit().contentEquals("1")){ //Reg
			if (instr.getRegisterSpecifier().contentEquals("000")) { //Reg Compare immediate
				
			}else if(instr.getRegisterSpecifier().contentEquals("001")) { //Reg Compare memory
				
			}
		}
	}

	private void executeRotateOpTrap(Instruction instr) {
		if (instr.get5thBit().equals("0")) {
			if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("00")) { // rotate left
				String ARString = Integer.toBinaryString(AR);
				String rotatedARString = ARString.substring(1, ARString.length()) + ARString.substring(0);
				this.AR = Transformer.binToDecimal(rotatedARString);
			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("01")) { // rotate right
				String ARString = Integer.toBinaryString(AR);
				String rotatedARString = ARString.substring(ARString.length() - 1)
						+ ARString.substring(0, ARString.length() - 1);
				this.AR = Transformer.binToDecimal(rotatedARString);
			} else if ((instr.getRegisterSpecifier().substring(0)).contentEquals("1")) { // Unary no OP trap

			}
		} else if (instr.get5thBit().equals("1")) { // Nonunary no OP trap

		}
	}

	private void executeDecOut(Instruction instr) {
		if (instr.getRegisterSpecifier().contentEquals("000")) { // immediate
			String operand = instr.getOperand();
			int dec = Transformer.binToDecimal(operand);
			window.setTerminalArea(window.getTerminalArea() + "" + dec);
		} else if (instr.getRegisterSpecifier().contentEquals("001")) { // memory
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			int dec = Transformer.hexToDecimal(memoryDump.getMemory(hexVal));
			window.setTerminalArea(window.getTerminalArea() + "" + dec);
		}
		PC += 2;
	}

	private void executeStopBranch(Instruction instr) {
		if (instr.getRegisterSpecifier().equals("0")) {
			if (instr.getRegisterSpecifier().contentEquals("000")) { // Stop
				stopProgram = true;
				return;
			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("10")) { // branch specified
																					// address/unconditional

			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("11")) { // branch if less-than-or-equal

			}
		} else if (instr.get5thBit().contentEquals("1")) {
			if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("00")) { // branch if less than

			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("01")) { // branch if equal

			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("10")) { // branch if not equal

			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("11")) { // branch if greater or equal

			}
		}
	}

	private void executeShiftNegateInvertBranch(Instruction instr) {
		if (instr.get5thBit().equals("0")) {
			if (instr.getRegisterSpecifier().contentEquals("00")) { // branch greater

			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("10")) { // branch if V (overflow)

			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("11")) { // branch if C (carry)

			}
		} else if (instr.get5thBit().contentEquals("1")) {
			if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("00")) { // bitwise invert
				this.AR = ~AR & 0xff;
			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("01")) { // negate

			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("10")) { // shift left
				this.AR = (AR << 1);
			} else if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("11")) { // shift right
				this.AR = (AR >> 1);
			}
		}
	}
	
	private void incrementPC() {
		String twoStr = "10";
		Binary twoBin = new Binary(twoStr);
		this.PC = binCal.add(twoBin, PC);
	}

	private void setFlags(Number operand) {
		String binNum = operand.toString();
		if (binNum.charAt(0) == '1') {
			N = new Binary("1");
		} else if (binNum.equals("00000000")) {
			Z = new Binary("1");
		}
	}

	private void setFlagsForArithmetic(Number operandOne, Number operandTwo, String operation) {
		String bin1 = operandOne.toString();
		String bin2 = operandTwo.toString();

		// First check for overflow by analyzing the most significant bit
		char MSB1 = bin1.charAt(0);
		char MSB2 = bin2.charAt(0);
		char sum = Transformer.decimalToBinary(Transformer.binToDecimal(bin1) + Transformer.binToDecimal(bin2))
				.charAt(0);
		if (MSB1 == MSB2 && MSB1 != sum) {
			V = new Binary("1");
		}

		switch (operation) {
			case "Subtract":
				int s = 0;
				// Traverse both strings starting
				// from last characters
				int i = bin1.length() - 1, j = bin2.length() - 1;
				while (i >= 0 || j >= 0 || s == 1) {
					// Comput sum of last
					// digits and carry
					s -= ((i >= 0) ? bin1.charAt(i) - '0' : 0);
					s -= ((j >= 0) ? bin2.charAt(j) - '0' : 0);
					// Compute carry
					s /= 2;
					if (s == 1) {
						C = new Binary("1");
					}
					// Move to next digits
					i--;
					j--;
				}

				break;
			case "Addition":
				int a = 0;
				// Traverse both strings starting
				// from last characters
				int k = bin1.length() - 1, l = bin2.length() - 1;
				while (k >= 0 || l >= 0 || a == 1) {
					// Comput sum of last
					// digits and carry
					a += ((k >= 0) ? bin1.charAt(k) - '0' : 0);
					a += ((l >= 0) ? bin2.charAt(l) - '0' : 0);
					// Compute carry
					a /= 2;
					if (a == 1) {
						C = new Binary("1");
					}
					// Move to next digits
					k--;
					l--;
				}
				break;
		}
	}

	@Override
	public Map<String, Binary> getConditionRegisterBits() {
		return Map.ofEntries(entry("N", N), entry("Z", Z), entry("V", V), entry("C", C));
	}
}