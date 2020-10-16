package model;

import java.util.Observable;
import java.util.Observer;

public class ControlUnit implements Observer {

    public void startCycle() {
        System.out.println("Starting fetch-execute cycle");
        // Fetch the next instruction.
        // Decode the instruction.
        // Get data if needed.
        // Execute the instruction.
    }

    @Override
    public void update(Observable o, Object arg) {
        startCycle();
    }
}
