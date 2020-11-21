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
	private boolean resetSystem = false;

	public Simulator() throws IOException {
		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		window = new SimulatorWindowImpl((MemoryDumpImpl) new ControlUnitImpl(window).getMemoryDump());
		controlUnit = new ControlUnitImpl(window);
		window.addObserver(this);
		controlUnit.getALU().addObserver(this);
		resetRegisters();
		frame.add(window.getMainPanel());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		// Case that is used for Internal state machine change
		if (arg instanceof Binary[]) {
			updateCPUComponents(window.getCPUComponents(), (Binary[]) arg);
			return;
		}

		if (resetSystem == true) {
			window.reset();
			resetRegisters();
			controlUnit.updateRegisters();
			controlUnit.getMemoryDump().wipeMemory();
			resetSystem = false;
			return;
		}
		if (arg instanceof String) {
			loadInstructionsIntoMemory();
			String argument = (String) arg;
			if (argument.equals("Single Step")) {
				controlUnit.executeNextInstruction();
				if (controlUnit.getCurrentInstruction().getOpcode().equals("0000")) {
					resetSystem = true;
				}
			} else if (argument.equals("Execute")){
				controlUnit.startCycle();
				resetSystem = true;
			}
		}

		// Update the memory dump.
		window.setMemoryDump((MemoryDumpImpl) controlUnit.getMemoryDump());
	}

	private void loadInstructionsIntoMemory() {
		String objectCode = window.getObjectCodeArea().getText();
		String sourceCode = window.getSourceCodeArea().getText();
		if (objectCode.length() != 0) {
			controlUnit.getMemoryDump().updateMemory(objectCode);
		}
		if (sourceCode.length() != 0) {
			controlUnit.getMemoryDump().updateMemoryAssembly(sourceCode);
		}
		window.getMemoryArea().setText(controlUnit.getMemoryDump().toString());
		window.getMemoryArea().setCaretPosition(0);
	}

	private void resetRegisters() {
		Binary[] registers = new Binary[7];
		for (int i = 0; i < registers.length; i++) {
			registers[i] = new Binary();
		}
		initializeCPUComponents(window.getCPUComponents(), registers);
		controlUnit.wipeRegisters();
	}

	private void updateCPUComponents(Map<String, JTextField> cpuComponents, Binary[] register) {
		cpuComponents.get("Accumulator").setText(formatBinaryToHex(register[2].getNumber()) + "");
		cpuComponents.get("Program Counter").setText(formatBinaryToHex(register[0].getNumber()) + "");
		cpuComponents.get("Instruction Specifier").setText(controlUnit.getInstructionSpecifier());
		cpuComponents.get("Operand Specifier").setText(formatBinaryToHex(controlUnit.getInstructionOperand()));
		cpuComponents.get("Index Register").setText(formatBinaryToHex(register[3].getNumber())+"");
		Map<String, Binary> conditionRegisterBits = controlUnit.getConditionRegisterBits();
		cpuComponents.get("N").setText(conditionRegisterBits.get("N").getNumber());
		cpuComponents.get("Z").setText(conditionRegisterBits.get("Z").getNumber());
		cpuComponents.get("V").setText(conditionRegisterBits.get("V").getNumber());
		cpuComponents.get("C").setText(conditionRegisterBits.get("C").getNumber());
	}


	private void initializeCPUComponents(Map<String, JTextField> cpuComponents, Binary[] register) {
		cpuComponents.get("Accumulator").setText(formatBinaryToHex("0"));
		cpuComponents.get("Program Counter").setText(formatBinaryToHex("0"));
		cpuComponents.get("Instruction Specifier").setText(controlUnit.getInstructionSpecifier());
		cpuComponents.get("Operand Specifier").setText(formatBinaryToHex("0"));
		cpuComponents.get("Index Register").setText(formatBinaryToHex("0"));
		Map<String, Binary> conditionRegisterBits = controlUnit.getConditionRegisterBits();
		cpuComponents.get("N").setText(conditionRegisterBits.get("N").getNumber());
		cpuComponents.get("Z").setText(conditionRegisterBits.get("Z").getNumber());
		cpuComponents.get("V").setText(conditionRegisterBits.get("V").getNumber());
		cpuComponents.get("C").setText(conditionRegisterBits.get("C").getNumber());
	}

	private String formatBinaryToHex(String binary) {
		return String.format("0x%04X", Integer.parseInt(binary, 2));
	}
}
