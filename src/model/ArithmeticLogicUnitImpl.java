package model;

import java.util.Observable;

/**
 * Registers[0] -> PC
 * Registers[1] -> IR
 * Registers[2] -> AR
 * Register[3..6] -> Storage registers
 */
public class ArithmeticLogicUnitImpl extends Observable implements ArithmeticLogicUnit{

    private Binary[] registers;

    public ArithmeticLogicUnitImpl() {
        registers = new Binary[7];
    }

    @Override
    public void updateState(Binary[] reg) {
        registers = reg;
        setChanged();
        notifyObservers(registers);
    }

    public Binary[] getRegisters() {
        return registers;
    }
    public void setRegisters(Binary[] reg) {
        this.registers = reg;
    }
    public Binary getPC() {
        return registers[0];
    }
    public Binary getIR() {
        return registers[1];
    }
    public Binary getAR() {
        return registers[2];
    }
}
