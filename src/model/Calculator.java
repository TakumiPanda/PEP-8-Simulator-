package model;

import utils.Transformer;

public class Calculator {

    public Number executeOperation(Number numOne, Number numTwo, int base, String operation) {
        String aStr = numOne.getNumber() + "";
        String bStr = numTwo.getNumber() + "";
        Binary a = new Binary(aStr);
        int aInt = Integer.parseInt(aStr, base);
        int bInt = Integer.parseInt(bStr, base);
        int result = 0;
        switch (operation) {
            case "Add":
               result = aInt + bInt;
               break;
            case "Subtract":
                result = aInt - bInt;
                break;
            case "Divide":
                result = aInt / bInt;
                break;
            case "Multiply":
                result = aInt * bInt;
                break;
            case "Mod":
                result = aInt % bInt;
        }
        String resultStr = "";
        switch (numOne.getClass().getName()) {
            case "model.Binary":
                resultStr = Transformer.decimalToBinary(result);
                resultStr = (result < 0) ? Transformer.formatAddress(resultStr, a.getNumber().length(), "1") :
                        Transformer.formatAddress(resultStr, a.getNumber().length(), "0");
                return new Binary(resultStr);
            case "model.Decimal":
                resultStr = "" + result;
                return new Decimal(resultStr);
            case "model.Hexadecimal":
                resultStr = Transformer.decimalToHex(result);
                return new Hexadecimal(resultStr);
            default:
                throw new IllegalArgumentException("Invalid values");
        }
    }
}
