package controller;

import model.ControlUnit;
import view.SimulatorWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        ControlUnit controlUnit = new ControlUnit();
        JFrame frame = new JFrame();
        frame.setBackground(Color.BLACK);
        SimulatorWindow window = new SimulatorWindow();
        window.addObserver(controlUnit);
        frame.add(window.getMainPanel());
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

}
