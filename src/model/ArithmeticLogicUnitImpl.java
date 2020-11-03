package model;

import java.util.Observable;

/**
 * Registers[0] -> PC
 * Registers[1] -> IR
 * Registers[2] -> AR
 * Register[3..6] -> Storage registers
 */
public class ArithmeticLogicUnitImpl extends Observable implements ArithmeticLogicUnit{

    private int[] registers;

    public ArithmeticLogicUnitImpl() {
        registers = new int[7];
    }

    @Override
    public void updateState(int[] reg) {
        registers = reg;
        setChanged();
        notifyObservers(registers);
    }

    public int[] getRegisters() {
        return registers;
    }
    public void setRegisters(int[] reg) {
        this.registers = reg;
    }
    public int getPC() {
        return registers[0];
    }
    public int getIR() {
        return registers[1];
    }
    public int getAR() {
        return registers[2];
    }

}
