package model;

public interface ControlUnit {

    /**
     * Starts the fetch-execute cycle in the Von-Nueman model.
     */
    void startCycle() throws InterruptedException;

    /**
     * Executes only a single instruction specified
     */

    void executeSingleInstruction(String instruction);

    /**
     * Gets the current Operand that is needed when displaying the internal state of
     * the machine in the View window.
     */
    Instruction getCurrentInstructionOperand();

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
}
