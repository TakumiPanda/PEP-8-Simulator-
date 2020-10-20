package controller;

import model.ControlUnit;
import view.SimulatorWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class Main implements Observer {

	private ControlUnit controlUnit;
	private SimulatorWindow window;

	public Main() throws IOException {
		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		window = new SimulatorWindow(new ControlUnit(window).memoryDump);
		controlUnit = new ControlUnit(window);
		window.addObserver(this);
		frame.add(window.getMainPanel());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
	@Override
	public void update(Observable o, Object arg) {
		String objectCode = window.getObjectCodeArea().getText();
		controlUnit.memoryDump.updateMemory(objectCode);
		window.getMemoryArea().setText(controlUnit.memoryDump.toString());
		window.getMemoryArea().setCaretPosition(0);
		try {
			window.reset();
			controlUnit.startCycle();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//Update the GUI components when fetch-execute cycle is finished.
		window.setMemoryDump(controlUnit.memoryDump);

	}

	public static void main(String[] args) throws IOException {
		new Main();
	}
}
