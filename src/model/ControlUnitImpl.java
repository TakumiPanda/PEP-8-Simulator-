package model;

import utils.Transformer;
import view.SimulatorWindow;

import java.util.Map;

import static java.util.Map.entry;

/**
 * 
 * @author
 * @version 2.9
 *
 */
public class ControlUnitImpl implements ControlUnit {

    private Instruction currentInstruction = new Instruction("0000", "0000");

    /**
     * Index register used to indexing array elements
     */
    private Binary IndexRegister = new Binary();

	/**
	 * PC is the pointer counter of the simulator.
	 */
    private Binary PC = new Binary();
    
    /**
     * AR is the accumulator register of the simulator.
     */
    private Binary AR = new Binary();
    /**
     * IR is the instruction register of the simulator.
     */
    private Binary IR = new Binary();

    /**
     * stopProgram is the boolean that'll halt the program.
     * Initialize to false. False means keep the simulator 
     * running. True means halt the program.
     */
    private boolean stopProgram = false;

    /**
     * N bit is set if result of operation in negative.
     */
    private Binary N = new Binary("0");
    /**
     * C bit is set if operation produced a carry (borrow on subtraction.
     */
    private Binary C = new Binary("0");
    /**
     * V bit is set if operation produced an overflow.
     */
    private Binary V = new Binary("0");
    /**
     * Z bit is set if result of operation is zero (All bits = 0
     */
    private Binary Z = new Binary("0");

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
        currentInstruction = Transformer.decodeInstruction(instr);
        this.IR.setNumber(currentInstruction.toString());
        executeInstruction(currentInstruction);
    }

    /**
     * 
     */
    @Override
    public void startCycle() {
        int addressOfInstructionInMemory = Transformer.binToDecimal("" + this.PC.getNumber());
        String instructionFromAddress = memoryDump.fetch(addressOfInstructionInMemory);
        this.IR.setNumber(instructionFromAddress);

        currentInstruction = Transformer.decodeInstruction(this.IR.getNumber());
        executeInstruction(currentInstruction);

        if (stopProgram == false) {
            startCycle();
        }
    }

    /**
     * Returns the current instruction from IR.
     */
    @Override
    public Instruction getCurrentInstruction() {
        return currentInstruction;
    }

    /**
     * Returns the current Instruction Specifier.
     * Should be 8 bits long
     */
    public String getInstructionSpecifier() {
        return this.IR.getNumber().substring(0,7);
    }

    /**
     * Returns the current instruction operand.
     * Should be 16 bits long
     */
    public String getInstructionOperand() {
        return currentInstruction.getOperand();
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
            case ("0000010"): //branch unconditional
                executeBR(instruction);
                break;
            case ("0000011"): //branch if less-than-or-equal
                executeBRLE(instruction);
                break;
            case ("0000100"): //branch if less-than
                executeBRLT(instruction);
                break;
            case ("0000101"): //branch if equal
                executeBREQ(instruction);
                break;
            case ("0000110"): //branch if not equal
                executeBRNE(instruction);
                break;
            case ("0000111"): //branch if greater-than-or-equal
                executeBRGE(instruction);
                break;
            case ("0001000"): //branch if greater-than
                executeBRGT(instruction);
                break;
            case ("0001001"): //branch if negative
                executeBRN(instruction);
                break;
            case ("0001010"): //branch if carry
                executeBRC(instruction);
                break;
            case ("0001110"): //shift left
                executeShiftLeft(instruction);
                break;
            case ("0001111"): //shift right
                executeShiftRight(instruction);
                break;
            case ("0001101"): //negate (2s comp)
                executeNegate(instruction);
                break;
            case ("0001100"): //invert (1s comp)
                executeBitwiseInvert(instruction);
                break;
            case ("0010000"): //rotate right
                executeRotateLeft(instruction);
                break;
            case ("0010001"): // rotate left
                executeRotateRight(instruction);
                break;
            case ("0011"): //decOut
                executeDecOut(instruction);
                break;
            case ("01001"): //char in
                executeCharIn(instruction);
                break;
            case ("01010"): //char out
                executeCharOut(instruction);
                break;
            case ("0111"): //add
                executeAdd(instruction);
                break;
            case ("1000"): //sub
                executeSub(instruction);
                break;
            case ("1001"): //and
                executeAnd(instruction);
                break;
            case ("1010"): //or
                executeOr(instruction);
                break;
            case ("1011"): //compare
                executeCompare(instruction);
                break;
            case ("1100"): //load memory
                executeLD(instruction);
                break;
            case ("1110"): //sw
                executeST(instruction);
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
     * If addressing mode last bit is "0",
     * Negates the AR value.
     * If addressing mode last bit is "1",
     * Negates the IndexRegister value.
     * 
     * @param instr 
     */
    private void executeNegate(Instruction instr) {
    	if (instr.getAddressingMode().substring(2).contentEquals("0")) {//AR
            int ARInt = Integer.parseInt(AR.getNumber(), 2);
            ARInt *= -1; //2s comp
            String negateARStr = Integer.toBinaryString(ARInt);
            this.AR = new Binary(negateARStr);
    	} else if (instr.getAddressingMode().substring(2).contentEquals("1")) { //Index Reg
    		int IndexInt = Integer.parseInt(IndexRegister.getNumber(), 2);
            IndexInt *= -1; //2s comp
            String negateIndexStr = Integer.toBinaryString(IndexInt);
            this.AR = new Binary(negateIndexStr);
    	}
    }

    /**
     * If addressing mode last bit is "0",
     * Shifts the AR value to the left.
     * If addressing mode last bit is "1",
     * Shifts the IndexRegister value to the left.
     * 
     * @param instr
     */
    private void executeShiftLeft(Instruction instr) {
    	if (instr.getAddressingMode().substring(2).contentEquals("0")) {//AR
    		int ARInt = Integer.parseInt(AR.getNumber(), 2);
            int shiftedARInt = (ARInt << 1);
            String shiftedARStr = Integer.toBinaryString(shiftedARInt);
            this.AR = new Binary(shiftedARStr);
    	} else if (instr.getAddressingMode().substring(2).contentEquals("1")) { //Index Reg
    		int IndexInt = Integer.parseInt(IndexRegister.getNumber(), 2);
            int shiftedIndexInt = (IndexInt << 1);
            String shiftedIndexStr = Integer.toBinaryString(shiftedIndexInt);
            this.IndexRegister = new Binary(shiftedIndexStr);
    	}
    }
    
  /**
   * If addressing mode last bit is "0",
   * Shifts the AR value to the right.
   * If addressing mode last bit is "1",
   * Shifts the IndexRegister value to the right.
   * 
   * @param instr
   */
    private void executeShiftRight(Instruction instr) {
    	if (instr.getAddressingMode().substring(2).contentEquals("0")) {//AR
    		int ARInt = Integer.parseInt(AR.getNumber(), 2);
            int shiftedARInt = (ARInt >> 1);
            String shiftedARStr = Integer.toBinaryString(shiftedARInt);
            this.AR = new Binary(shiftedARStr);
    	} else if (instr.getAddressingMode().substring(2).contentEquals("1")) { //Index Reg
    		int IndexInt = Integer.parseInt(IndexRegister.getNumber(), 2);
            int shiftedIndexInt = (IndexInt >> 1);
            String shiftedIndexStr = Integer.toBinaryString(shiftedIndexInt);
            this.IndexRegister = new Binary(shiftedIndexStr);
    	}
    }

    /**
     * If Register Specifier is "0" and addressing mode is "000",
     * adds the AR value with the operand value.
     * If Register Specifier is "0" and addressing mode is "001",
     * adds the AR value with a memory dump value.
     * If Register Specifier is "1" and addressing mode is "000",
     * adds the IndexRegister value with the operand value.
     * If Register Specifier is "1" and addressing mode is "001",
     * adds the IndexRegister value with a memory dump value.
     * 
     * @param instr
     */
    private void executeAdd(Instruction instr) {
    	if (instr.getRegisterSpecifier().contentEquals("0")) { //Accumulator
    		if (instr.getAddressingMode().contentEquals("000")) { // immediate
                Binary operandValue = new Binary(instr.getOperand());
                setFlags(this.AR, operandValue, "Addition");
                this.AR = binCalculator.add(operandValue, AR);
            } else if (instr.getAddressingMode().contentEquals("001")) { // direct
                int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
                Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
                setFlags(this.AR, memVal, "Addition");
                this.AR = binCalculator.add(memVal, AR);
            }
    	} else if (instr.getRegisterSpecifier().contentEquals("1")) { //Index Reg
    		if (instr.getAddressingMode().contentEquals("000")) { // immediate
                Binary operandValue = new Binary(instr.getOperand());
                setFlags(this.IndexRegister, operandValue, "Addition");
                this.IndexRegister = binCalculator.add(operandValue, IndexRegister);
            } else if (instr.getAddressingMode().contentEquals("001")) { // direct
                int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
                Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
                setFlags(this.IndexRegister, memVal, "Addition");
                this.IndexRegister = binCalculator.add(memVal, IndexRegister);
            }
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
     * If Register Specifier is "0" and addressing mode is "000",
     * loads immediate value into the AR.
     * If Register Specifier is "0" and addressing mode is "001",
     * loads a memory dump value into the AR.
     * If Register Specifier is "1" and addressing mode is "000",
     * loads immediate value into the IndexRegister.
     * If Register Specifier is "1" and addressing mode is "001",
     * loads a memory dump value into the IndexRegister.
     * 
     * 
     * @param instr
     */
    private void executeLD(Instruction instr) {
    	if (instr.getRegisterSpecifier().contentEquals("0")) { //Accumulator
    		if (instr.getAddressingMode().contentEquals("000")) { //immediate
    			this.AR.setNumber(instr.getOperand());
    		}
    		else if (instr.getAddressingMode().contentEquals("001")) { //direct
    			int address = Transformer.binToDecimal(instr.getOperand());
                String memBin = Integer.toBinaryString(
                        Transformer.hexToDecimal(memoryDump.getMemory(address)));
                this.AR.setNumber(memBin);
    		}
    	}else if (instr.getRegisterSpecifier().contentEquals("1")) { //Index Reg
    		if (instr.getAddressingMode().contentEquals("000")) { //immediate
    			this.IndexRegister.setNumber(instr.getOperand());
    		}
    		else if (instr.getAddressingMode().contentEquals("001")) { //direct
    			int address = Transformer.binToDecimal(instr.getOperand());
                String memBin = Integer.toBinaryString(
                        Transformer.hexToDecimal(memoryDump.getMemory(address)));
                this.IndexRegister.setNumber(memBin);
    		}
    	}
    }

    /**
     * If Register Specifier is "0" and addressing mode is "000",
     * subtracts the operand value with the AR value.
     * If Register Specifier is "0" and addressing mode is "001",
     * subtracts the memory dump value with the AR value
     * If Register Specifier is "1" and addressing mode is "000",
     * subtracts the operand value with the IndexRegister value.
     * If Register Specifier is "1" and addressing mode is "001",
     * subtracts the memory dump value with the IndexRegister value
     * 
     * @param instr
     */
    private void executeSub(Instruction instr) {
    	if (instr.getRegisterSpecifier().contentEquals("0")) { //Accumulator
    		if (instr.getAddressingMode().contentEquals("000")) { // immediate
    			Binary operandVal = new Binary(instr.getOperand());
                setFlags(this.AR, operandVal, "Subtraction");
                this.AR = binCalculator.subtract(AR, operandVal);
            } else if (instr.getAddressingMode().contentEquals("001")) { // direct
            	int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
                Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
                setFlags(this.AR, memVal, "Subtraction");
                this.AR = binCalculator.subtract(AR, memVal);
            }
    	} else if (instr.getRegisterSpecifier().contentEquals("1")) { //Index Reg
    		if (instr.getAddressingMode().contentEquals("000")) { // immediate
    			Binary operandVal = new Binary(instr.getOperand());
                setFlags(this.IndexRegister, operandVal, "Subtraction");
                this.IndexRegister = binCalculator.subtract(IndexRegister, operandVal);
            } else if (instr.getAddressingMode().contentEquals("001")) { // direct
            	int hexVal = Integer.parseInt(Transformer.binToHex(instr.getOperand()), 16);
                Binary memVal = new Binary(Transformer.hexToBinary(memoryDump.getMemory(hexVal)));
                setFlags(this.IndexRegister, memVal, "Subtraction");
                this.IndexRegister = binCalculator.subtract(IndexRegister, memVal);
            }
    	}
    }

    /**
     * If Register Specifier is "0",
     * stores the AR value in a memory dump address.
     * If Register Specifier is "1",
     * stores the IndexRegister value in a memory dump address.
     * 
     * @param instr
     */
    private void executeST(Instruction instr) {
    	if (instr.getRegisterSpecifier().contentEquals("0")) { //Accumulator
    		String hexAddressStr = Transformer.binToHex(instr.getOperand());
            memoryDump.setMemory(hexAddressStr, Integer.parseInt(this.AR.getNumber(), 2));
    	}else if (instr.getRegisterSpecifier().contentEquals("1")) { //Index Reg
    		String hexAddressStr = Transformer.binToHex(instr.getOperand());
            memoryDump.setMemory(hexAddressStr,Integer.parseInt(this.IndexRegister.getNumber(), 2));
    	}
        if (instr.getAddressingMode().equals("000")) { // immediate mode
            String hexAddress = Transformer.binToHex(instr.getOperand());
            memoryDump.setMemory(hexAddress, Integer.parseInt(this.AR.getNumber(), 2));
        } else if(instr.getAddressingMode().equals("001")) { // direct mode
            String hexAddress = Transformer.binToHex(instr.getOperand());
            String valueInAddress = memoryDump.getMemory(Integer.parseInt(hexAddress.replaceAll("\\s+",""), 16));
            memoryDump.setMemory(hexAddress, Integer.parseInt(valueInAddress, 16)) ;
        }
    }

    /**
     * If register specifier is "0" and addressing mode is "000",
     * bitwise and the AR value with the operand value.
     * If register specifier is "0" and addressing mode is "001",
     * bitwise and the AR value with a memory dump value.
     * If register specifier is "1" and addressing mode is "000",
     * bitwise and the IndexRegister value with the operand value.
     * If register specifier is "1" and addressing mode is "001",
     * bitwise and the IndexRegister value with a memory dump value.
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
		}else if(instr.getRegisterSpecifier().contentEquals("1")){ //Index Reg
			if (instr.getAddressingMode().contentEquals("000")) { //Index Reg & immediate
				int valueInt = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
                int Indexint = Integer.parseInt(IndexRegister.getNumber(), 2);
                int andInt = (Indexint & valueInt);
                String andStr = Integer.toBinaryString(andInt);
                Binary andBin = new Binary(andStr);
                this.IndexRegister = andBin;
			}else if(instr.getAddressingMode().contentEquals("001")) { //Index Reg & memory
				int address = Transformer.binToDecimal(instr.getOperand());
                int memValue = Transformer.hexToDecimal(memoryDump.getMemory(address));
                int valueIndex = Integer.parseInt(IndexRegister.getNumber(), 2);
                int andInt = (valueIndex & memValue);
                String andStr = Integer.toBinaryString(andInt);
                Binary andBin = new Binary(andStr);
                this.IndexRegister = andBin;
				}
			}
        }
    }

    /**
     * If addressing mode last bit is "0",
     * Bitwise inverts the AR value.
     * If addressing mode last bit is "1",
     * Bitwise inverts the IndexRegister value.
     * 
     * @param instr
     */
    private void executeBitwiseInvert(Instruction instr) {
    	if (instr.getAddressingMode().substring(2).contentEquals("0")) {//AR
    		String onesCompStr = AR.getNumber();
            String replacedStr = onesCompStr.replace('0', '2').replace('1', '0').replace('2', '1'); //1s comp
            this.AR = new Binary(replacedStr);
    	} else if (instr.getAddressingMode().substring(2).contentEquals("1")) {//Index Reg
    		String onesCompStr = IndexRegister.getNumber();
            String replacedStr = onesCompStr.replace('0', '2').replace('1', '0').replace('2', '1'); //1s comp
            this.IndexRegister = new Binary(replacedStr);
    	}
    }

    /**
     * If register specifier is "0" and addressing mode is "000",
     * bitwise and the AR value with the operand value.
     * If register specifier is "0" and addressing mode is "001",
     * bitwise and the AR value with a memory dump value.
     * If register specifier is "1" and addressing mode is "000",
     * bitwise and the IndexRegister value with the operand value.
     * If register specifier is "1" and addressing mode is "001",
     * bitwise and the IndexRegister value with a memory dump value.
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
        }else if(instr.getRegisterSpecifier().contentEquals("1")){ //Index Reg
        	if (instr.getAddressingMode().contentEquals("000")) { //Index Reg | immediate
        		int value = Integer.parseInt(Transformer.binToHex(instr.getOperand()));
                int indexValue = Integer.parseInt(IndexRegister.getNumber(), 2);
                int resultInt = (value | indexValue);
                String resultStr = Integer.toBinaryString(resultInt);
                this.IndexRegister = new Binary(resultStr);
        	}else if(instr.getRegisterSpecifier().contentEquals("001")) { //Index Reg | memory
        		int address = Transformer.binToDecimal(instr.getOperand());
                int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
                int indexValue = Integer.parseInt(IndexRegister.getNumber(), 2);
                int resultInt = (value | indexValue);
                String resultStr = Integer.toBinaryString(resultInt);
                this.IndexRegister = new Binary(resultStr);
        	}
        }
    }

    /**
     * If register specifier is "0" and addressing mode is "000",
     * compare the AR value with the operand value.
     * If register specifier is "0" and addressing mode is "001",
     * compare the AR value with a memory dump value.
     * If register specifier is "1" and addressing mode is "000",
     * compare the IndexRegister value with the operand value.
     * If register specifier is "1" and addressing mode is "001",
     * compare the IndexRegister value with a memory dump value.
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
		}else if(instr.getRegisterSpecifier().contentEquals("1")){ //Index Reg
			if (instr.getAddressingMode().contentEquals("000")) { //Index Reg Compare immediate
				Binary operandBin = new Binary(instr.getOperand());
                this.IndexRegister = new Binary(Integer.toBinaryString(
                		IndexRegister.compare(operandBin.getNumber(), IndexRegister.getNumber())));
			}else if(instr.getAddressingMode().contentEquals("001")) { //Index Reg Compare memory
				int address = Transformer.binToDecimal(instr.getOperand());
                int value = Transformer.hexToDecimal(memoryDump.getMemory(address));
                this.IndexRegister = new Binary(Integer.toBinaryString(
                		IndexRegister.compare(Integer.toString(value), IndexRegister.getNumber())));
			}
		}
	}

    /**
     * If addressing mode last bit is "0",
     * Rotates the AR value to the right.
     * If addressing mode last bit is "1",
     * Rotates the IndexRegister value to the right.
     * 
     * @param instr
     */
    private void executeRotateRight(Instruction instr) {
    	if (instr.getAddressingMode().substring(2).contentEquals("0")) { //AR
    		 String ARString = AR.getNumber();
    	     String rotatedARString = ARString.substring(ARString.length() - 1)
    	                + ARString.substring(0, ARString.length() - 1);
    	     this.AR = new Binary(rotatedARString);
    	} else if (instr.getAddressingMode().substring(2).contentEquals("1")) { //Index Reg
    		String indexString = IndexRegister.getNumber();
	        String rotatedARString = indexString.substring(indexString.length() - 1)
	                + indexString.substring(0, indexString.length() - 1);
	        this.AR = new Binary(rotatedARString);
    	}
    }

    /**
     * If addressing mode last bit is "0",
     * Rotates the AR value to the left.
     * If addressing mode last bit is "1",
     * Rotates the IndexRegister value to the left.
     * 
     * @param instr
     */
    private void executeRotateLeft(Instruction instr) {
    	if (instr.getAddressingMode().substring(2).contentEquals("0")) { //AR
    		String ARString = AR.getNumber();
            String rotatedARString = ARString.substring(1, ARString.length()) + ARString.substring(0);
            this.AR = new Binary(rotatedARString);
    	} else if (instr.getAddressingMode().substring(2).contentEquals("0")) { //Index Reg
    		String indexString = IndexRegister.getNumber();
            String rotatedIndexString = indexString.substring(1, indexString.length()) + indexString.substring(0);
            this.IndexRegister = new Binary(rotatedIndexString);
    	}   
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
        this.PC.setNumber(Transformer.formatBinaryAddress(this.PC.getNumber(), 16, "0"));
    }

    /**
     * Sets addressing bits when arithmetic operations are executed
     * @param binObjectOne binObjectTwo operation
     */
    private void setFlags(Binary binObjectOne, Binary binObjectTwo, String operation) {
        BinaryCalculator binaryCalc = new BinaryCalculator();
        String product = "";
        switch (operation) {
            case "Addition":
                product = binaryCalc.add(binObjectOne, binObjectTwo).getNumber();
                break;
            case "Subtraction":
                product = binaryCalc.subtract(binObjectOne, binObjectTwo).getNumber();
        }
        char MSBOne = binObjectOne.getNumber().charAt(0);
        char MSBTwo = binObjectTwo.getNumber().charAt(0);
        char MSBProduct = product.charAt(0);
        if (MSBOne == MSBTwo && MSBOne != MSBProduct){
            V.setNumber("1");
        }
        if (product.length() > 16) {
            C.setNumber("1");
            N.setNumber("0");
            Z.setNumber("0");
        }
        if (MSBProduct == '1') {
            N.setNumber("1");
            Z.setNumber("0");
        }
        if (product.equals("0000000000000000")) {
            Z.setNumber("1");
            N.setNumber("0");
            C.setNumber("0");
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