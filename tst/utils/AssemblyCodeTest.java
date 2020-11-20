package utils;

import model.ControlUnitImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssemblyCodeTest {

    ControlUnitImpl controlUnit = new ControlUnitImpl(null);
    AssemblyConverter assemblyConverter = new AssemblyConverter();


    @Test
    void storeInstructionTest() {
        // Load 1 into the Accumulator. Store the Accumulator(value 1) into Mem[A]
        String assemblyCode = "LDR 1,i \n" +
                              "STR 10,i\n" +
                              "STOP\n" +
                              ".END";
        executeAssemblyCode(assemblyCode);
        assertEquals("1", controlUnit.getMemoryDump().getMemory(0x000A) );
    }

    @Test
    void loadInstructionTest() {
        // Load the accumulator with the value of 10.
        String assemblyCode = "LDR 10, i\n" +
                              "STOP\n" +
                              ".END";
        executeAssemblyCode(assemblyCode);
        assertEquals("0000000000001010", controlUnit.getALU().getAR().getNumber().replaceAll("\\s",""));
    }

    @Test
    void addInstructionTest() {
        // Load the accumulator with 1 then Add 1. Accumulator should be 2(0010 in binary).
        String assemblyCode = "LDR 1, i\n" +
                              "ADDR 1, i\n" +
                              "STOP\n" +
                              ".END" ;
        executeAssemblyCode(assemblyCode);
        assertEquals("0000000000000010", controlUnit.getALU().getAR().getNumber().replaceAll("\\s",""));
    }

    @Test
    void subtractInstructionTest() {
        // Load the Accumulator with 1 then subtract 1. Accumulator should be 0
        String assemblyCode = "LDR 1, i\n" +
                              "SUBR 1, i\n" +
                              "STOP\n" +
                              ".END";
        executeAssemblyCode(assemblyCode);
        assertEquals("0000000000000000", controlUnit.getALU().getAR().getNumber().replaceAll("\\s",""));
    }

    private void executeAssemblyCode(String assemblyCode) {
        String[] convertedHexCode = assemblyConverter.generateHexString(assemblyCode).split("\\s+");
        for (String hexInstruction: convertedHexCode) {
            String convertedMachineCode = Transformer.hexToBinary(hexInstruction);
            controlUnit.executeSingleInstruction(convertedMachineCode);
        }
    }
}
