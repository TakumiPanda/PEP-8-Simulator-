package model;

public interface ArithmeticLogicUnit {
    /**
     * Update the state of the registers stored in memory.
     * When an instruction is executed the addressing bits
     * sometimes will change.
      * @param reg
     */
    void updateState(Binary[] reg);
}
