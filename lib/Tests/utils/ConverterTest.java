package Tests.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Converter;


class ConverterTest {

    @Test
    void testBinToHex() {
        String b = "1010";
        Converter converter = new Converter();
        String H = converter.binToHex(b);
        Assertions.assertEquals("A",H);
    }

    @Test
    void testHexToDecimal() {
        String h = "A";
        Converter converter = new Converter();
        int d = converter.hexToDecimal(h);
        Assertions.assertEquals(10,d);
    }

    @Test
    void testBinToDecimal() {
        String b = "1010";
        Converter converter = new Converter();
        int d = converter.binToDecimal(b);
        Assertions.assertEquals(10,d);
    }

    @Test
    void testHexToBinary() {
        String h = "ab";
        Converter converter = new Converter();
        String b = converter.hexToBinary(h);
        Assertions.assertEquals("1010 1011 ",b);
    }

    @Test
    void testDecimalToBinary() {
        int d = 10;
        Converter converter = new Converter();
        String b = converter.decimalToBinary(d);
        Assertions.assertEquals("1010",b);
    }


}