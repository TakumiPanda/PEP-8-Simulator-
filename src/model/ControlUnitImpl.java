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
	public void startCycle() {
		String formattedPCAddress = formatBinaryAddress(this.PC.getNumber().replace(" ", ""));
		String hexAddress = Transformer.binToHex(formattedPCAddress).replace(" ", "");
		String formattedHex = String.format("%06X", Integer.parseInt(hexAddress,16));
		String pcStr = memoryDump.fetch(Integer.parseInt(formattedHex, 16));
		this.IR.setNumber(pcStr);


		Instruction currentInstruction = Transformer.decodeInstruction(pcStr);
		executeInstruction(currentInstruction);

		if (stopProgram == false) {
			startCycle();
		}
	}
	private String formatBinaryAddress(String binAddress) {
		int lengthExtended = 16 - binAddress.length();
		for (int i = 0; i < lengthExtended; i++) {
			binAddress = "0" + binAddress;
		}
		return binAddress;
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
			case ("0000"):// stop  
				executeStop(instruction);
				break;
			case ("0000010"):
				executeBR(instruction);
				break;
			case ("0000011"):
				executeBRLE(instruction);	
				break;
			case ("0000100"):
				executeBRLT(instruction);
				break;
			case ("0000101"):
				executeBREQ(instruction);
				break;
			case ("0000110"):
				executeBRNE(instruction);
				break;
			case ("0000111"):
				executeBRGE(instruction);
				break;
			case ("0001000"):
				executeBRGT(instruction);
				break;
			case ("0001001"):
				executeBRN(instruction);
				break;
			case ("0001010"):
				executeBRC(instruction);
				break;
			case "0001110": //shift left
				executeShiftLeft(instruction);
				break;
			case "0001111": //Shift right
				executeShiftRight(instruction);
				break;
			case "0001101": //Negate
				executeNegate(instruction);
				break;
			case "0001100":
				executeBitwiseInvert(instruction);
				break;
			case "0010000": //rotate right
				executeRotateLeft(instruction);
				break;
			case "0010001": // rotate left
				executeRotateRight(instruction);
				break;
			case ("0011"):// decOut
				executeDecOut(instruction);
				break;
			case ("01001"):// char in
				executeCharIn(instruction);
				break;
			case ("01010"):// char out
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
		incrementPC();

		// Registers must be updated in the GUI Window.
		Binary[] updatedRegisters = ALU.getRegisters();
		updatedRegisters[0] = this.PC; 
		updatedRegisters[1] = this.IR; 
		updatedRegisters[2] = this.AR; 
		ALU.updateState(updatedRegisters);
	}

	private void executeAdd(Instruction instr) {
		if (instr.getRegisterSpecifier().contentEquals("000")) { // immediate
			Binary operandValue = new Binary(instr.getOperand());
			this.AR = binCalculator.add(operandValue,AR);
		} else if (instr.getRegisterSpecifier().contentEquals("001")) { // direct
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
			this.AR = binCalculator.add(memVal, AR);
		}
		incrementPC();
		incrementPC();
	}

	private void executeCharIn(Instruction instr) {
		// Wait for a character to be pressed in the terminal window
	}

	private void executeCharOut(Instruction instr) {
		String operand = instr.getOperand();
		char character = (char) Transformer.binToDecimal(operand);
		window.setTerminalArea(window.getTerminalArea() + "" + character);
	}

	private void executeLW(Instruction instr) {
		int address = Transformer.binToDecimal(instr.getOperand());
		Binary memBin = new Binary(Integer.toBinaryString(
				Transformer.hexToDecimal(memoryDump.getMemory(address))));
		this.AR = memBin;
	}

	private void executeSub(Instruction instr) {
		if (instr.getAddressingMode().contentEquals("000")) { // immediate
			Binary operandVal = new Binary (instr.getOperand());
			this.AR = binCalculator.subtract(AR, operandVal);
		} else if (instr.getAddressingMode().contentEquals("001")) { // direct
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
		if (instr.getRegisterSpecifier().contentEquals("0")){ //AC
			if (instr.getAddressingMode().contentEquals("000")) { //AR & immediate
				int valueInt = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
				int ARint = Integer.parseInt(AR.getNumber(),2);
				int andInt = (ARint & valueInt);
				String andStr = Integer.toBinaryString(andInt);
				Binary andBin = new Binary(andStr);
				this.AR = andBin;
			}else if(instr.getAddressingMode().contentEquals("001")) { //AR & memory
				int address = Transformer.binToDecimal(instr.getOperand());
				int memValue = Transformer.hexToDecimal(memoryDump.getMemory(address));
				int valueAR = Integer.parseInt(AR.getNumber(),2);
				int andInt = (valueAR & memValue);
				String andStr = Integer.toBinaryString(andInt);
				Binary andBin = new Binary(andStr);
				this.AR = andBin;
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
		if (instr.getRegisterSpecifier().contentEquals("0")){ //AC
		if (instr.getAddressingMode().contentEquals("000")) { //AR | immediate
			int value = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
			int ARvalue = Integer.parseInt(AR.getNumber(),2);
			int resultInt = (value | ARvalue);
			String resultStr = Integer.toBinaryString(resultInt);
			this.AR = new Binary(resultStr);
		}else if(instr.getAddressingMode().contentEquals("001")) { //AR | memory
			int address = Transformer.binToDecimal(instr.getOperand());
			int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
			int ARvalue = Integer.parseInt(AR.getNumber(),2);
			int resultInt = (value | ARvalue);
			String resultStr = Integer.toBinaryString(resultInt);
			this.AR = new Binary(resultStr);
		}
	}
	//work on later	
//	}else if(instr.get5thBit().contentEquals("1")){ //Reg
//		if (instr.getRegisterSpecifier().contentEquals("000")) { //Reg | immediate
//			
//		}else if(instr.getRegisterSpecifier().contentEquals("001")) { //Reg | memory
//			
//		}
//	}
	}

	//work on later
	private void executeCompare(Instruction instr) {}
////		Decimal dec;
//		if (instr.get5thBit().contentEquals("0")){ //AC
//			if (instr.getRegisterSpecifier().contentEquals("000")) { //AR Compare immediate
//				Binary operandBin = new Binary(instr.getOperand());
//				Binary resultBin = new Binary(Integer.toBinaryString(Number.compare(operandBin.getNumber(),AR.getNumber())));
//			}else if(instr.getRegisterSpecifier().contentEquals("001")) { //AR Compare memory
//				int address = Transformer.binToDecimal(instr.getOperand());
//				int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
//				this.AR = dec.compare(Integer.toString(value), Integer.toString(AR));
//			}
//		}
//		}else if(instr.get5thBit().contentEquals("1")){ //Reg
//			if (instr.getRegisterSpecifier().contentEquals("000")) { //Reg Compare immediate
//				
//			}else if(instr.getRegisterSpecifier().contentEquals("001")) { //Reg Compare memory
//				
//			}
//		}

	private void executeRotateRight(Instruction instr) {
		String ARString = AR.getNumber();
		String rotatedARString = ARString.substring(ARString.length() - 1)
				+ ARString.substring(0, ARString.length() - 1);
		this.AR = new Binary(rotatedARString);
	}

	private void executeRotateLeft(Instruction instr) {
		String ARString = AR.getNumber();
		String rotatedARString = ARString.substring(1, ARString.length()) + ARString.substring(0);
		this.AR = new Binary(rotatedARString);
	}

	private void executeNOPn(Instruction instr) {

	}

	private void executeNOP(Instruction instr) {

	}

	private void executeDecOut(Instruction instr) {
		if (instr.getAddressingMode().contentEquals("000")) { // immediate
			String operand = instr.getOperand();
			int dec = Transformer.binToDecimal(operand);
			window.setTerminalArea(window.getTerminalArea() + "" + dec);
		} else if (instr.getAddressingMode().contentEquals("001")) { // memory
			int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
			int dec = Transformer.hexToDecimal(memoryDump.getMemory(hexVal));
			window.setTerminalArea(window.getTerminalArea() + "" + dec);
		}
		incrementPC();
	}

	private void executeStop(Instruction instr) {
		stopProgram = true;
	}
	
	private void executeBR(Instruction instr) {

	} 

	private void executeBRLE(Instruction instr) {

	}
	
	private void executeBRLT(Instruction instr) {

	}

	private void executeBREQ(Instruction instr) {

	}

	private void executeBRNE(Instruction instr) {

	}

	private void executeBRGE(Instruction instr) {

	}

	private void executeBRGT(Instruction instr) {

	}
	
	private void executeBRC(Instruction instr) {
		
	}

	private void executeBRN(Instruction instr) {
		
	}

	private void executeShiftLeft(Instruction instr) {
		int ARInt = Integer.parseInt(AR.getNumber(), 2);
		int shiftedARInt = (ARInt << 1);
		String shiftedARStr = Integer.toBinaryString(shiftedARInt);
		this.AR = new Binary(shiftedARStr);
	}

	private void executeShiftRight(Instruction instr) {
		int ARInt = Integer.parseInt(AR.getNumber(), 2);
		int shiftedARInt = (ARInt >> 1);
		String shiftedARStr = Integer.toBinaryString(shiftedARInt);
		this.AR = new Binary(shiftedARStr);
	}

	private void executeNegate(Instruction instr) {
		int ARInt = Integer.parseInt(AR.getNumber(),2);
		ARInt *= -1; //2s comp
		String negateARStr = Integer.toBinaryString(ARInt);
		this.AR = new Binary(negateARStr);
	}

	private void executeBitwiseInvert(Instruction instr) {
		String onesCompStr = AR.getNumber();
		String replacedStr = onesCompStr.replace('0', '2').replace('1', '0').replace('2', '1'); //1s comp
		this.AR = new Binary(replacedStr);
	}
	
	private void incrementPC() {
		String twoStr = "01";
		Binary twoBin = new Binary(twoStr);
		this.PC = binCalculator.add(twoBin, PC);
	}

	private void setFlags(Number operand) {
		String binNum = operand.toString();
		if (binNum.charAt(0) == '1') {
			N = new Binary("1");
		} else if (binNum.equals("00000000")) {
			Z = new Binary("1");
		}
	}

	@Override
	public Map<String, Binary> getConditionRegisterBits() {
		return Map.ofEntries(entry("N", N), entry("Z", Z), entry("V", V), entry("C", C));
	}
}