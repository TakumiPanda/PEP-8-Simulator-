package model;

import java.util.Map;

import static java.util.Map.entry;

import utils.Transformer;
import view.SimulatorWindow;

/**
 * 
 * @author
 * @version 2.0
 *
 */
public class ControlUnitImpl implements ControlUnit {

	/**
	 * PC is the pointer counter of the simulator.
	 */
    private Binary PC = new Binary("0000000000000000");
    
    /**
     * AR is the accumulator register of the simulator.
     */
    private Binary AR = new Binary("0000000000000000");
    
    /**
     * IR is the instruction register of the simulator.
     */
    private Binary IR = new Binary("0000000000000000");

    /**
     * stopProgram is the boolean that'll halt the program.
     * Initialize to false. False means keep the simulator 
     * running. True means halt the program.
     */
    private boolean stopProgram = false;

    /**
     * N bit is set if result of operation in negative.
     */
    private Binary N = new Binary("" + 0);
    /**
     * C bit is set if operation produced a carry (borrow on subtraction.
     */
    private Binary C = new Binary("" + 0);
    /**
     * V bit is set if operation produced an overflow.
     */
    private Binary V = new Binary("" + 0);
    /**
     * Z bit is set if result of operation is zero (All bits = 0
     */
    private Binary Z = new Binary("" + 0);

    /**
     * binCalculator is the BinaryCalculator object for the simulator.
     */
    private BinaryCalculator binCalculator = new BinaryCalculator();
    
    /**
     * ALU is the ArithmeticLogicUnitImpl object for the simulator
     */
    private ArithmeticLogicUnitImpl ALU = new ArithmeticLogicUnitImpl();
    
    /**
     * window is the SimulatorWindow object for the simulator.
     */
    private SimulatorWindow window;
    
    /**
     * memoryDump is the MemoryDumpImpl object for the simulator.
     */
    private MemoryDumpImpl memoryDump = new MemoryDumpImpl();

    /**
     * Parameterized constructor. Creates an ControlUnitImpl 
     * object.
     * 
     * @param window SimulatorWindow object
     */
    public ControlUnitImpl(SimulatorWindow window) {
        this.window = window;
    }

    /**
     * Executes a single instruction.
     */
    @Override
    public void executeSingleInstruction(String instr) {
        Instruction instruction = Transformer.decodeInstruction(instr);
        executeInstruction(instruction);
    }

    /**
     * 
     */
    @Override
    public void startCycle() {
        String formattedPCAddress = formatBinaryAddress(this.PC.getNumber().replace(" ", ""));
        String hexAddress = Transformer.binToHex(formattedPCAddress).replace(" ", "");
        String formattedHex = String.format("%06X", Integer.parseInt(hexAddress, 16));
        String pcStr = memoryDump.fetch(Integer.parseInt(formattedHex, 16));
        this.IR.setNumber(pcStr);


        Instruction currentInstruction = Transformer.decodeInstruction(pcStr);
        executeInstruction(currentInstruction);

        if (stopProgram == false) {
            startCycle();
        }
    }

    /**
     * Formats the length of the Binary address. Adds zero extension
     * to the current binary address.
     * 
     * @param binAddress String binary address.
     * @return String binary address.
     */
    private String formatBinaryAddress(String binAddress) {
        int lengthExtended = 16 - binAddress.length();
        for (int i = 0; i < lengthExtended; i++) {
            binAddress = "0" + binAddress;
        }
        return binAddress;
    }

    /**
     * Returns the current instruction from IR.
     */
    @Override
    public String getCurrentInstruction() {
        return this.IR.getNumber();
    }

    /**
     * Returns the ALU (Arithmetic Logic Unit) Impl.
     */
    @Override
    public ArithmeticLogicUnitImpl getALU() {
        return ALU;
    }

    /**
     * Returns the memoryDump.
     */
    @Override
    public MemoryDump getMemoryDump() {
        return memoryDump;
    }

    /**
     * Decodes the given instruction.
     * 
     * @param instruction instruction to be decoded.
     */
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

    /**
     * Negates the AR value.
     * 
     * @param instr 
     */
    private void executeNegate(Instruction instr) {
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        ARInt *= -1; //2s comp
        String negateARStr = Integer.toBinaryString(ARInt);
        this.AR = new Binary(negateARStr);
    }

    /**
     * Shifts the AR value to the left.
     * 
     * @param instr
     */
    private void executeShiftLeft(Instruction instr) {
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        int shiftedARInt = (ARInt << 1);
        String shiftedARStr = Integer.toBinaryString(shiftedARInt);
        this.AR = new Binary(shiftedARStr);
    }

    /**
     * Shifts the AR value to the right.
     * 
     * @param instr
     */
    private void executeShiftRight(Instruction instr) {
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        int shiftedARInt = (ARInt >> 1);
        String shiftedARStr = Integer.toBinaryString(shiftedARInt);
        this.AR = new Binary(shiftedARStr);
    }

    /**
     * Adds the AR value with the operand value if addressing mode
     * is "000". Else, adds the AR value with a memory dump
     * value if addressing mode is "001". NEED TESTING
     * 
     * @param instr
     */
    private void executeAdd(Instruction instr) {
        if (instr.getAddressingMode().contentEquals("000")) { // immediate
            Binary operandValue = new Binary(instr.getOperand());
            this.AR = binCalculator.add(operandValue, AR);
        } else if (instr.getAddressingMode().contentEquals("001")) { // direct
            int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
            Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
            this.AR = binCalculator.add(memVal, AR);
        }
        incrementPC();
        incrementPC();
    }

    /**
     * Takes in an character input from the simulator window.
     * 
     * @param instr
     */
    private void executeCharIn(Instruction instr) {
        // Wait for a character to be pressed in the terminal window
    }

    /**
     * Outputs an ascii character from the operand
     * to the simulator window. 
     * 
     * @param instr
     */
    private void executeCharOut(Instruction instr) {
        String operand = instr.getOperand();
        char character = (char) Transformer.binToDecimal(operand);
        window.setTerminalArea(window.getTerminalArea() + "" + character);
    }

    /**
     * Loads a memory dump value into the AR.
     * 
     * @param instr
     */
    private void executeLW(Instruction instr) {
        int address = Transformer.binToDecimal(instr.getOperand());
        Binary memBin = new Binary(Integer.toBinaryString(
                Transformer.hexToDecimal(memoryDump.getMemory(address))));
        this.AR = memBin;
    }

    /**
     * subtracts the operand value with the AR value if addressing mode
     * is "000". Else, subtracts the memory dump value with the AR value
     * if addressing mode is "001".
     * 
     * @param instr
     */
    private void executeSub(Instruction instr) {
        if (instr.getAddressingMode().contentEquals("000")) { // immediate
            Binary operandVal = new Binary(instr.getOperand());
            this.AR = binCalculator.subtract(AR, operandVal);
        } else if (instr.getAddressingMode().contentEquals("001")) { // direct
            int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
            Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
            this.AR = binCalculator.subtract(AR, memVal);
        }
    }

    /**
     * Stores the AR value in a memory dump address.
     * 
     * @param instr
     */
    private void executeSW(Instruction instr) {
        String hexAddress = Transformer.binToHex(instr.getOperand());
        memoryDump.setMemory(hexAddress, Integer.parseInt(this.AR.getNumber(), 2)); //double check if radix is 2 or 16
    }

    /**
     * If addressing mode is "000" and register specifier is "0",
     * bitwise and the AR value with the operand value.
     * If addressing mode is "001" and register specifier is "0",
     * bitwise and the AR value with a memory dump value.
     * 
     * @param instr
     */
    private void executeAnd(Instruction instr) {
        if (instr.getRegisterSpecifier().contentEquals("0")) { //AC
            if (instr.getAddressingMode().contentEquals("000")) { //AR & immediate
                int valueInt = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
                int ARint = Integer.parseInt(AR.getNumber(), 2);
                int andInt = (ARint & valueInt);
                String andStr = Integer.toBinaryString(andInt);
                Binary andBin = new Binary(andStr);
                this.AR = andBin;
            } else if (instr.getAddressingMode().contentEquals("001")) { //AR & memory
                int address = Transformer.binToDecimal(instr.getOperand());
                int memValue = Transformer.hexToDecimal(memoryDump.getMemory(address));
                int valueAR = Integer.parseInt(AR.getNumber(), 2);
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

    /**
     * Bitwise inverts the AR value.
     *
     * @param instr
     */
    private void executeBitwiseInvert(Instruction instr) {
        String onesCompStr = AR.getNumber();
        String replacedStr = onesCompStr.replace('0', '2').replace('1', '0').replace('2', '1'); //1s comp
        this.AR = new Binary(replacedStr);
    }

    /**
     * If addressing mode is "000" and register specifier is "0",
     * bitwise or the AR value with the operand value.
     * If addressing mode is "001" and register specifier is "0",
     * bitwise or the AR value with a memory dump value.
     * 
     * @param instr
     */
    private void executeOr(Instruction instr) {
        if (instr.getRegisterSpecifier().contentEquals("0")) { //AC
            if (instr.getAddressingMode().contentEquals("000")) { //AR | immediate
                int value = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
                int ARvalue = Integer.parseInt(AR.getNumber(), 2);
                int resultInt = (value | ARvalue);
                String resultStr = Integer.toBinaryString(resultInt);
                this.AR = new Binary(resultStr);
            } else if (instr.getAddressingMode().contentEquals("001")) { //AR | memory
                int address = Transformer.binToDecimal(instr.getOperand());
                int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
                int ARvalue = Integer.parseInt(AR.getNumber(), 2);
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

    /**
     * If addressing mode is "000" and register specifier is "0",
     * compare the AR value with the operand value.
     * If addressing mode is "001" and register specifier is "0",
     * compare the AR value with a memory dump value.
     * 
     * @param instr
     */
    private void executeCompare(Instruction instr) {
        if (instr.getRegisterSpecifier().contentEquals("0")) { //AC
            if (instr.getAddressingMode().contentEquals("000")) { //AR Compare immediate
                Binary operandBin = new Binary(instr.getOperand());
                this.AR = new Binary(Integer.toBinaryString(
                        AR.compare(operandBin.getNumber(), AR.getNumber())));
            } else if (instr.getAddressingMode().contentEquals("001")) { //AR Compare memory
                int address = Transformer.binToDecimal(instr.getOperand());
                int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
                this.AR = new Binary(Integer.toBinaryString(
                        AR.compare(Integer.toString(value), AR.getNumber())));
            }
        }
    }
//		}else if(instr.get5thBit().contentEquals("1")){ //Reg
//			if (instr.getRegisterSpecifier().contentEquals("000")) { //Reg Compare immediate
//				
//			}else if(instr.getRegisterSpecifier().contentEquals("001")) { //Reg Compare memory
//				
//			}
//		}
//	}

    /**
     * Rotates the AR value to the right.
     * 
     * @param instr
     */
    private void executeRotateRight(Instruction instr) {
        String ARString = AR.getNumber();
        String rotatedARString = ARString.substring(ARString.length() - 1)
                + ARString.substring(0, ARString.length() - 1);
        this.AR = new Binary(rotatedARString);
    }

    /**
     * Rotates the AR value to the left.
     * 
     * @param instr
     */
    private void executeRotateLeft(Instruction instr) {
        String ARString = AR.getNumber();
        String rotatedARString = ARString.substring(1, ARString.length()) + ARString.substring(0);
        this.AR = new Binary(rotatedARString);
    }

    /**
     * 
     * 
     * @param instr
     */
    private void executeNOPn(Instruction instr) {

    }

    /**
     * 
     * 
     * @param instr
     */
    private void executeNOP(Instruction instr) {

    }

    /**
     * If addressing mode is "000", outputs a decimal integer
     * from the operand value. If addressing mode is "001",
     * outputs a decimal integer from the memory address.
     * 
     * 
     * @param instr
     */
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

    /**
     * Halts the simulator.
     * 
     * @param instr
     */
    private void executeStop(Instruction instr) {
        stopProgram = true;
    }

    /**
     * Branch unconditionally.
     * 
     * @param instr
     */
    private void executeBR(Instruction instr) {
        Binary addr = new Binary(instr.getOperand());
        this.PC = addr;
    }

    /**
     * Branch if less-than-or-equal.
     * 
     * @param instr
     */
    private void executeBRLE(Instruction instr) {
    	int addrInt = Integer.parseInt(instr.getOperand(), 2);
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        if (ARInt <= addrInt) {
            this.PC = new Binary(Integer.toBinaryString(addrInt));
        }
    }

    /**
     * Branch if less-than.
     * 
     * @param instr
     */
    private void executeBRLT(Instruction instr) {
        if ((instr.getRegisterSpecifier().substring(0, 1)).contentEquals("00")) { // branch if less than
            int addrInt = Integer.parseInt(instr.getOperand(), 2);
            int ARInt = Integer.parseInt(AR.getNumber(), 2);
            if (ARInt < addrInt) {
                this.PC = new Binary(Integer.toBinaryString(addrInt));
            }
        }
    }

    /**
     * Branch if equal to.
     * 
     * @param instr
     */
    private void executeBREQ(Instruction instr) {
        int addrInt = Integer.parseInt(instr.getOperand(), 2);
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        if (ARInt == addrInt) {
            this.PC = new Binary(Integer.toBinaryString(addrInt));
        }
    }

    /**
     * Branch if not equal to.
     * 
     * @param instr
     */
    private void executeBRNE(Instruction instr) {
        int addrInt = Integer.parseInt(instr.getOperand(), 2);
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        if (ARInt != addrInt) {
            this.PC = new Binary(Integer.toBinaryString(addrInt));
        }
    }

    /**
     * Branch if greater-than-or-equal.
     * 
     * @param instr
     */
    private void executeBRGE(Instruction instr) {
    	int addrInt = Integer.parseInt(instr.getOperand(), 2);
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        if (ARInt >= addrInt) {
            this.PC = new Binary(Integer.toBinaryString(addrInt));
        }
    }

    /**
     * Branch if greater-than.
     * 
     * @param instr
     */
    private void executeBRGT(Instruction instr) {
        int addrInt = Integer.parseInt(instr.getOperand(), 2);
        int ARInt = Integer.parseInt(AR.getNumber(), 2);
        if (ARInt > addrInt) {
            this.PC = new Binary(Integer.toBinaryString(addrInt));
        }
    }

    /**
     * Branch if carry.
     * 
     * @param instr
     */
    private void executeBRC(Instruction instr) {
        if (Integer.parseInt(C.getNumber()) == 1) {
            this.PC = new Binary(instr.getOperand());
        }
    }

    /**
     * Branch if negative.
     * 
     * @param instr
     */
    private void executeBRN(Instruction instr) {
    	if (Integer.parseInt(N.getNumber()) == 1) {
            this.PC = new Binary(instr.getOperand());
        }
    }

    /**
     * Branch if overflow.
     * 
     * @param instr
     */
    private void executeBRV(Instruction instr) {
        if (Integer.parseInt(V.getNumber()) == 1) {
            this.PC = new Binary(instr.getOperand());
        }
    }

    /**
     * Increments PC by one.
     */
    private void incrementPC() {
        String twoStr = "01";
        Binary twoBin = new Binary(twoStr);
        this.PC = binCalculator.add(twoBin, PC);
    }

    /**
     * 
     * @param operand
     */
    private void setFlags(Number operand) {
        String binNum = operand.toString();
        if (binNum.charAt(0) == '1') {
            N = new Binary("1");
        } else if (binNum.equals("00000000")) {
            Z = new Binary("1");
        }
    }

    /**
     * 
     */
    @Override
    public Map<String, Binary> getConditionRegisterBits() {
        return Map.ofEntries(entry("N", N), entry("Z", Z), entry("V", V), entry("C", C));
    }
}