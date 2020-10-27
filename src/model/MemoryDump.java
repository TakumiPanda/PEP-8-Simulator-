package model;

public interface MemoryDump {
    /**
     * 
     * @param objCode
     */
    void updateMemory(String objCode);

    /**
     * 
     * @param address
     * @return
     */
    String fetch(int address);

    /**
     * 
     * @param hexAddress
     * @param value
     */
    void setMemory(String hexAddress, int value);

    /**
     * 
     */
    String getMemory(int address);   
}
