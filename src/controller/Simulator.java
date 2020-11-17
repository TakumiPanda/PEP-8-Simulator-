package controller;

import model.Binary;
import model.ControlUnitImpl;
import model.MemoryDumpImpl;
import view.SimulatorWindowImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/**
 * Accepts input from the View SimulatorWindow and works with the model to
 * update accordingly.
 */
public class Simulator implements Observer {

	private ControlUnitImpl controlUnit;
	private SimulatorWindowImpl window;

	public Simulator() throws IOException {
		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		window = new SimulatorWindowImpl((MemoryDumpImpl) new ControlUnitImpl(window).getMemoryDump());
		controlUnit = new ControlUnitImpl(window);
		window.addObserver(this);
		controlUnit.getALU().addObserver(this);
		Binary[] registers = new Binary[7];
		for (int i = 0; i < registers.length; i++) {
			registers[i] = new Binary();
		}
		updateCPUComponents(window.getCPUComponents(),registers);
		frame.add(window.getMainPanel());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.pack();
	}

	private void updateCPUComponents(Map<String, JTextField> cpuComponents, Binary[] register) {
		cpuComponents.get("Accumulator").setText(formatBinaryToHex(register[2].getNumber()) + "");
		cpuComponents.get("Program Counter").setText(formatBinaryToHex(register[0].getNumber()) + "");
		cpuComponents.get("Instruction Specifier").setText(controlUnit.getInstructionSpecifier());
		cpuComponents.get("Operand Specifier").setText(formatBinaryToHex(controlUnit.getInstructionOperand()));
		cpuComponents.get("Index Register").setText(formatBinaryToHex(register[3].getNumber())+"");
		Map<String, Binary> conditionRegisterBits = controlUnit.getConditionRegisterBits();
		cpuComponents.get("N").setText(conditionRegisterBits.get("N").toString());
		cpuComponents.get("Z").setText(conditionRegisterBits.get("Z").toString());
		cpuComponents.get("V").setText(conditionRegisterBits.get("V").toString());
		cpuComponents.get("C").setText(conditionRegisterBits.get("C").toString());
	}

	private String formatBinaryToHex(String binary) {
		return String.format("0x%04X", Integer.parseInt(binary, 2));
	}

	@Override
	public void update(Observable o, Object arg) {
		// Case that is used for Internal state machine change
		if (arg instanceof Binary[]) {
			updateCPUComponents(window.getCPUComponents(), (Binary[]) arg);
			return;
		}

		controlUnit.getMemoryDump().updateMemory(window.getObjectCodeArea().getText());
		controlUnit.getMemoryDump().updateMemoryAssembly(window.getSourceCodeArea().getText());
		window.getMemoryArea().setText(controlUnit.getMemoryDump().toString());
		window.getMemoryArea().setCaretPosition(0);

		// Case that is used if single step is pressed, and only one instruction needs
		// to be executed.
		if (arg instanceof String) {
			controlUnit.executeSingleInstruction((String) arg);
		} else {
			controlUnit.startCycle();
		}
		// Update the memory dump.
		window.setMemoryDump((MemoryDumpImpl) controlUnit.getMemoryDump());
	}
}
