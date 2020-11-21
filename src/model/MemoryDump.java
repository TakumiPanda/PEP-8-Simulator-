package model;

public interface MemoryDump {
    /**
     * Source code is passed in and the method will place the instructions
     * into memory.
     * @param objCode
     */
    void updateMemory(String objCode);

    /**
     * Assembly code is passed in and the method will place instructions
     * into memory.
     * @param assemblyCode
     */
    void updateMemoryAssembly(String assemblyCode);

    /**
     * Grabs and instruction in String format from the memory.
     * @param address HEX address
     * @return Mem[address]
     */
    String fetch(int address);

    /**
     * M[hexAddress] = value
     * @param hexAddress
     * @param value
     */
    void setMemory(String hexAddress, int value);

    /**
     * Will return what ever is stored at that memory location.
     */
    String getMemory(int address);

    /**
     * Reset memory back to default.
     */
    void wipeMemory();
}
