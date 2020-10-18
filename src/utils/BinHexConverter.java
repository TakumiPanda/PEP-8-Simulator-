package utils;

public class BinHexConverter {
	public int binaryToInt(String binary) {
		int decimal = Integer.parseInt(binary, 2);
		return decimal;
	}

	public String binaryToHex(String binary) {
		StringBuilder hex = new StringBuilder();
		int count = 1;
		if (binary.length() < 1) {
			return hex.toString();
		}
		if (binary.replace(" ", "").length() % 2 != 0) {
			return "Invalid binary string.";
		}
		for (int i = 0; i < binary.replace(" ", "").length(); i += 4) {
			String binString = binary.replace(" ", "").substring(i, i + 4);
			hex.append(Integer.toString(binaryToInt(binString), 16).toUpperCase());
			if (count % 2 == 0) {
				hex.append(" ");
			}
			count++;
		}
		return hex.toString();
	}

	public int hexToDecimal(String hex) {
		int decimal = Integer.parseInt(hex, 16);
		return decimal;
	}

	public String hexToBinary(String hex) {
		StringBuilder binary = new StringBuilder();
		if (hex.length() < 1) {
			return binary.toString();
		}
		if (hex.replace(" ", "").length() % 2 != 0) {
			return "Invalid hex string.";
		}
		String[] hexCode = hex.replace(" ", "").split("");
		for (int i = 0; i < hexCode.length; i++) {
			int binVal = hexToDecimal(hexCode[i]);
			binary.append(String.format("%4s", Integer.toBinaryString(binVal)).replace(" ", "0"));
			binary.append(" ");
		}
		return binary.toString();
	}

	public static void main(String[] args) {
		BinHexConverter b = new BinHexConverter();
		String hexString = "41 20 73 69 6D 70 6C 65 20 4A 61 76 61 20 50 72 6F 67 72 61 6D";
		System.out.println(b.hexToBinary(hexString));
		System.out.println(b.binaryToHex(b.hexToBinary(hexString)));
	}
}
