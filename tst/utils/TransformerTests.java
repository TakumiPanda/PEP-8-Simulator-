package utils;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Assertions;

public class TransformerTests {
    Transformer transformer = new Transformer();
    @Test
    void testBinaryToDecimalSuccess(){
        int binary = 10101010;
        int decimalConversion = -86;
        Assertions.assertEquals(decimalConversion, transformer.transformBinaryToDecimal(binary));
    }
    @Test
    void testBinaryToDecimalFail() {
        int binary = 10101010;
        int decimalConversion = -86;
        Assertions.assertNotEquals(decimalConversion + 1, transformer.transformBinaryToDecimal(binary));
    }
    @Test
    void testDecimalToBinarySuccess(){
        int decimal = 65;
        int binaryConversion = 1000001;
        Assertions.assertEquals(binaryConversion, transformer.transformDecimalToBinary(decimal));
    }
    @Test
    void testDecimalToBinaryFail() {
        int decimal = 65;
        int binaryConversion = 1000001;
        Assertions.assertNotEquals(binaryConversion + 1, transformer.transformDecimalToBinary(decimal));
    }
}
