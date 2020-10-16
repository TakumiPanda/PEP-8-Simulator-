package utils;

//import org.graalvm.compiler.debug.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TransformerTests {
    Transformer transformer = new Transformer();
    @Test
    void testBinaryToDecimalSuccess(){
        int binary = 10101010;
<<<<<<< HEAD
        int decimalConversion = 170;
        Assert.assertEquals(decimalConversion, Transformer.transformBinaryToDecimal(binary));
=======
        int decimalConversion = -86;
        Assertions.assertEquals(decimalConversion, transformer.transformBinaryToDecimal(binary));
>>>>>>> 39a90a4d9c9a5aa6c4eae799455af2c869c251f3
    }
    @Test
    void testBinaryToDecimalFail() {
        int binary = 10101010;
<<<<<<< HEAD
        int decimalConversion = 170;
        Assert.assertNotEquals(decimalConversion + 1, Transformer.transformBinaryToDecimal(binary));
=======
        int decimalConversion = -86;
        Assertions.assertNotEquals(decimalConversion + 1, transformer.transformBinaryToDecimal(binary));
>>>>>>> 39a90a4d9c9a5aa6c4eae799455af2c869c251f3
    }
    @Test
    void testDecimalToBinarySuccess(){
        int decimal = 65;
        int binaryConversion = 1000001;
        Assert.assertEquals(binaryConversion, Transformer.transformDecimalToBinary(decimal));
    }
    @Test
    void testDecimalToBinaryFail() {
        int decimal = 65;
        int binaryConversion = 1000001;
        Assert.assertNotEquals(binaryConversion + 1, Transformer.transformDecimalToBinary(decimal));
    }
}
