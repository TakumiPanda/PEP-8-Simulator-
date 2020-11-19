package model;

import org.junit.jupiter.api.Test;
import utils.Transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ControlUnitTest {

    ControlUnitImpl controlUnit = new ControlUnitImpl(null);

    @Test
    void executeLoadInstructionTest() {
        //Command will Load the A register with 1010(10 in decimal)
        String loadInstruction = "110000000000000000001010";
        controlUnit.executeSingleInstruction(loadInstruction);
        assertEquals("0000000000001010", controlUnit.getALU().getAR().getNumber().replaceAll("\\s",""));
    }

    @Test
    void executeStoreInstructionTest() {
        // Will first load the A register with the value of 1010(10 in decimal, A in Hex).
        //Command will store the A register(the value 10) into Memory[0001]
        String loadInstruction = "110000000000000000001010";
        String storeInstruction = "111000010000000000000001";
        controlUnit.executeSingleInstruction(loadInstruction);
        controlUnit.executeSingleInstruction(storeInstruction);
        assertEquals("a", controlUnit.getMemoryDump().getMemory(0x0001));
    }

    @Test
    void executeAddInstructionTest() {
        // Command will Load the A register with the value of 1(in binary). Then perform an ADD operation to increment by one
        // Result should be a value of 2 in the Accumulator, or 10(in binary)
        String loadInstruction = "110000000000000000000001";
        String addInstruction = "011100000000000000000001";
        controlUnit.executeSingleInstruction(Transformer.decodeInstruction(loadInstruction).toString());
        controlUnit.executeSingleInstruction(Transformer.decodeInstruction(addInstruction).toString());
        assertEquals("0000000000000010", controlUnit.getALU().getAR().getNumber().replaceAll("\\s",""));
    }

    @Test
    void executeSubtractInstructionTest() {
        // Command will Load the A register with the value of 1(in binary).
        // Then perform a Subtract operation by subtracting one. The answer should be 0
        String loadInstruction = "110000000000000000000001";
        String subtractInstruction = "100000000000000000000001";
        controlUnit.executeSingleInstruction(loadInstruction);
        controlUnit.executeSingleInstruction(subtractInstruction);
        assertEquals("0000000000000000", controlUnit.getALU().getAR().getNumber().replaceAll("\\s",""));
    }
}
