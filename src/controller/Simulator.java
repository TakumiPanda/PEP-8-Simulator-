package controller;

import model.ControlUnitImpl;
import model.MemoryDumpImpl;
import view.SimulatorWindow;
import view.SimulatorWindowImpl;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

/**
 * Accepts input from the View SimulatorWindow and works with the model to update accordingly. 
 */
public class Simulator implements Observer {

	private ControlUnitImpl controlUnit;
	private SimulatorWindowImpl window;

	public Simulator() throws IOException {
		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		window = new SimulatorWindowImpl((MemoryDumpImpl)new ControlUnitImpl(window).getMemoryDump());
		controlUnit = new ControlUnitImpl(window);
		window.addObserver(this);
		frame.add(window.getMainPanel());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.pack();
	}
	@Override
	public void update(Observable o, Object arg) {
		String objectCode = window.getObjectCodeArea().getText();
		controlUnit.getMemoryDump().updateMemory(objectCode);
		window.getMemoryArea().setText(controlUnit.getMemoryDump().toString());
		window.getMemoryArea().setCaretPosition(0);
		try {
			window.reset();
			controlUnit.startCycle();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Update the GUI components when fetch-execute cycle is finished.
		window.setMemoryDump((MemoryDumpImpl)controlUnit.getMemoryDump());

	}
}
