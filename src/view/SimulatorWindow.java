package view;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.MemoryDumpImpl;

public interface SimulatorWindow {

    public JPanel getMainPanel(); 

    public JTextArea getObjectCodeArea();

    public JTextArea getSourceCodeArea();

    public JTextArea getMemoryArea();

    public void setMemoryDump(MemoryDumpImpl updatedMemory);

    public void setTerminalArea(String output);

    public String getTerminalArea();

    /**
     * Resets the Object Code text box and Terminal text box.
     */
    public void reset();
}
