package view;

import model.MemoryDumpImpl;

import javax.swing.*;
import java.util.Map;

public interface SimulatorWindow {

    /**
     * @return Map{
     * 	"N", nField
	 *	"Z", zField
	 *	"V", vField
	 *	"C", cField
	 *	"Accumulator", accumalatorField
	 *	"Index Register", indexRegisterField
	 *	"Stack Pointer", stackPointerField
	 *	"Program Counter", programCounterField
	 *	"Instruction Specifier", instructionSpecifierField
	 *	"Operand Specifier", operandSpecifierField
     *	"Operand", operandField
     *  }
     * 
     */
    Map<String, JTextField> getCPUComponents();

    /**
     * Resets the Object Code text box and Terminal text box.
     */
    void reset();

    JPanel getMainPanel();

    JTextArea getObjectCodeArea();

    JTextArea getSourceCodeArea();

    JTextArea getMemoryArea();

    void setMemoryDump(MemoryDumpImpl updatedMemory);

    void setTerminalArea(String output);

    String getTerminalArea();
}
