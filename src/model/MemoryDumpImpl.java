package model;

import utils.AssemblyConverter;
import utils.Transformer;

import java.util.ArrayList;
import java.util.List;

public class MemoryDumpImpl implements MemoryDump {
    private String[] hexDump;
    private String[] charDump;
    private List<String> binInstructions = new ArrayList<>();
    private static final int SIZE = 65536;

    public MemoryDumpImpl() {
        wipeMemory();
    }

    @Override
    public void updateMemory(String objCode) {
        //Check if Binary or Hex
        String[] instructions = objCode.split("\\s+");
        if (instructions[0].length() == 6) { // Hex code
            placeInstructionsIntoMemory(objCode);
            for (String instruction: instructions) {
                String machineCode = Transformer.hexToBinary(instruction);
                binInstructions.add(machineCode);
            }
        } else { //Machine code
            //Fill memory list if first time launching
            String code = "";
            //Convert each instruction to Hex
            for (String instruction : instructions) {
                code += Transformer.binToHex(instruction) + " ";
                binInstructions.add(instruction);
            }
            placeInstructionsIntoMemory(code);
        }
    }

    public void updateMemoryAssembly(String assemblyCode) {
        AssemblyConverter assemblyConverter = new AssemblyConverter();
        String objCode = assemblyConverter.generateHexString(assemblyCode);
        if (!objCode.isBlank()) {
            updateMemory(objCode);
        }
    }

    private void placeInstructionsIntoMemory(String objCode) {
        int objCodeIndex = 0;
        int decodeIndex = 0;
        if (objCode.length() < 1) {
            return;
        }
        if (objCode.replace(" ", "").length() % 2 != 0) {
            System.err.println("Invalid hex string.");
            return;
        }
        // [1 2 3 4 5 6 7 8] [9 10 11 12 13 14 15 16]
        // [17 18 19 20 21 22 23 24] [25 26 27 28 29 30 31 32]
        for (int i = 0; i < objCode.replace(" ", "").length(); i += 2) {
            String hexCode = objCode.replace(" ", "").substring(i, i + 2);
            hexDump[objCodeIndex] = hexCode;
            objCodeIndex++;
            try {
                charDump[decodeIndex] = String.valueOf((char) (int) Integer.valueOf(hexCode, 16));
                decodeIndex++;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String fetch(int address) {
        return (binInstructions.size() == 0)? "000000000000000000000000": binInstructions.get(address);
    }

    @Override
    public String getMemory(int address) {
        if (address < 0x0000 || address > 0xFFFF) {
            throw new IllegalArgumentException("Invalid address");
        }
        //return dump[address * 2 - address % 8];
        return hexDump[address];
    }

    @Override
    public void setMemory(String hexAddress, int value) {
        int hexA = Integer.parseInt(String.valueOf(Transformer.hexToDecimal(hexAddress)));
        String hexVal = Integer.toHexString(value);

        //dump[hexA * 2 - hexA % 8] = hexVal;
        hexDump[hexA] = hexVal;
        charDump[hexA] = String.valueOf((char) (int) Integer.valueOf(hexVal, 16));
    }

    @Override
    public void wipeMemory() {
        binInstructions.clear();
        this.hexDump = new String[SIZE];
        this.charDump = new String[SIZE];
        for (int i = 0; i < SIZE - 1; i += 8) {
            // objCode
            for (int offset = 0; offset <= 7; offset++) {
                // FILL OBJECT CODE HERE
                //this.dump[i + offset] = "00";
                this.hexDump[i + offset] = "00";
            }

            // decode
            for (int offset = 0; offset <= 7; offset++) {
                // FILL IN DECODE HERE
                //this.dump[i + 8 + offset] = "00";
                this.charDump[i + offset] = ".";
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        int lineNumber = 0;
        for (int i = 0; i < SIZE - 1; i += 8) {
            output.append(String.format("%-" + 5 + "s", String.format("%04X", lineNumber)));
            output.append("|");

            // objCode
            for (int offset = 0; offset <= 7; offset++) {
                output.append(this.hexDump[i + offset] + " ");
            }

            output.append("| ");

            // decode
            for (int offset = 0; offset <= 7; offset++) {
                output.append(this.charDump[i + offset]);
            }
            output.append("\n");
            lineNumber += 8;

        }

        return output.toString();
    }
}
