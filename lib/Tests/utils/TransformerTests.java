package Tests.utils;

//import org.graalvm.compiler.debug.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import utils.Transformer;

public class TransformerTests {
	Transformer transformer = new Transformer();

	@Test
    void testBinaryToDecimalSuccess(){
        int binary = 10101010;
        int decimalConversion = -86;
        Assert.assertEquals(decimalConversion, Transformer.transformBinaryToDecimal(binary));
    }

	@Test
    void testBinaryToDecimalFail() {
        int binary = 10101010;

        int decimalConversion = -85;
        Assert.assertNotEquals(decimalConversion + 1, Transformer.transformBinaryToDecimal(binary));  

    }

	@Test
	void testDecimalToBinarySuccess() {
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
