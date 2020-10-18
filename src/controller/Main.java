package controller;

import java.awt.Color;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import model.ControlUnit;
import view.SimulatorWindow;

public class Main implements Observer {

	private ControlUnit controlUnit = new ControlUnit();
	private SimulatorWindow window;

	public Main() throws IOException {
		JFrame frame = new JFrame();
		frame.setBackground(Color.BLACK);
		window = new SimulatorWindow();
		window.addObserver(this);
		frame.add(window.getMainPanel());
		frame.setResizable(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	@Override
	public void update(Observable o, Object arg) {
		String objectCode = window.getObjectCodeArea().getText();
		controlUnit.memoryDump.updateMemory(objectCode);
		window.getMemoryArea().setText(controlUnit.memoryDump.toString());
		window.getMemoryArea().setCaretPosition(0);
		controlUnit.startCycle();
	}

	public static void main(String[] args) throws IOException {
		new Main();
	}
}
