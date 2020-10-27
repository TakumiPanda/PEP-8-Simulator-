package model;

public interface ControlUnit {
    
    /**
     * Starts the fetch-execute cycle in the Von-Nueman model.
     */
    void startCycle() throws InterruptedException;
}
