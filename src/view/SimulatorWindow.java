package view;

import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.MemoryDumpImpl;

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
    public Map<String, JTextField> getCPUComponents();

    public void setCPUComponents(Map<String, JTextField> cpuComponents);

    /**
     * Resets the Object Code text box and Terminal text box.
     */
    public void reset();

    public JPanel getMainPanel(); 

    public JTextArea getObjectCodeArea();

    public JTextArea getSourceCodeArea();

    public JTextArea getMemoryArea();

    public void setMemoryDump(MemoryDumpImpl updatedMemory);

    public void setTerminalArea(String output);

    public String getTerminalArea();
}
