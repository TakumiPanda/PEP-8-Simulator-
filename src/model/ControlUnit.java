package model;

import java.util.Map;

public interface ControlUnit {

    /**
     * Starts the fetch-execute cycle in the Von-Nueman model.
     */
    void startCycle() throws InterruptedException;

    /**
     * Fetches the instruction from memory, loads it into IR and executes the instruction.
     */
    void executeNextInstruction();

    /**
     * Executes only a single instruction specified
     */
    void executeSingleInstruction(String instr);

    /**
     * Controller needs access to the ALU for the internal State of the machine. In
     * particular it needs to registers state.
     * 
     * @return ArithmeticLogicUnit
     */
    ArithmeticLogicUnitImpl getALU();

    /**
     * The Controller needs access to the memory Dump object for both displaying and
     * manipulating.
     * 
     * @return
     */
    MemoryDump getMemoryDump();

    /**
     * The controller needs access to the condition code register bits for the
     * internal state machine to be displayed after each execution of instruction
     */
    Map<String, Binary> getConditionRegisterBits();
}
