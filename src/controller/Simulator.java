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
		updateCPUComponents(window.getCPUComponents(), new Binary[7]);
		frame.add(window.getMainPanel());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.pack();
	}

	private void updateCPUComponents(Map<String, JTextField> cpuComponents, Binary[] register) {
		cpuComponents.get("Accumulator").setText(register[2] + "");
		cpuComponents.get("Program Counter").setText(register[0] + "");
		cpuComponents.get("Instruction Specifier").setText(register[1] + "");
		cpuComponents.get("Operand Specifier").setText(controlUnit.getCurrentInstruction());
		Map<String, Binary> conditionRegisterBits = controlUnit.getConditionRegisterBits();
		cpuComponents.get("N").setText(conditionRegisterBits.get("N").toString());
		cpuComponents.get("Z").setText(conditionRegisterBits.get("Z").toString());
		cpuComponents.get("V").setText(conditionRegisterBits.get("V").toString());
		cpuComponents.get("C").setText(conditionRegisterBits.get("C").toString());
	}

	@Override
	public void update(Observable o, Object arg) {
		// Case that is used for Internal state machine change
		if (arg instanceof Binary[]) {
			updateCPUComponents(window.getCPUComponents(), (Binary[]) arg);
			return;
		}

		controlUnit.getMemoryDump().updateMemory(window.getObjectCodeArea().getText());
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
