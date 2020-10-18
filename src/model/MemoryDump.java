package model;

public class MemoryDump {
	public String[] dump;
	private static final int SIZE = 131073;

	public MemoryDump() {
		this.dump = new String[SIZE];
		for (int i = 0; i < SIZE - 1; i += 16) {
			// objCode
			for (int offset = 0; offset <= 7; offset++) {
				// FILL OBJECT CODE HERE
				this.dump[i + offset] = "00";
			}

			// decode
			for (int offset = 0; offset <= 7; offset++) {
				// FILL IN DECODE HERE
				this.dump[i + 8 + offset] = ".";
			}
		}
	}

	public void updateMemory(String objCode) {
		int objCodeIndex = 0;
		int decodeIndex = 8;
		int lineNumber = 0;
		if (objCode.length() < 1) {
			return;
		}
		if (objCode.replace(" ", "").length() % 2 != 0) {
			System.err.println("Invlid hex string.");
			return;
		}
		// [1 2 3 4 5 6 7 8] [9 10 11 12 13 14 15 16]
		// [17 18 19 20 21 22 23 24] [25 26 27 28 29 30 31 32]
		for (int i = 0; i < objCode.replace(" ", "").length(); i += 2) {
			String hexCode = objCode.replace(" ", "").substring(i, i + 2);
			if (objCodeIndex % 7 - lineNumber == 0 && objCodeIndex != 0) {
				dump[objCodeIndex] = hexCode;
				objCodeIndex += 9;
			} else {
				dump[objCodeIndex] = hexCode;
				objCodeIndex++;
			}

			if (decodeIndex % 15 - lineNumber == 0 && decodeIndex != 0) {
				dump[decodeIndex] = String.valueOf((char) (int) Integer.valueOf(hexCode, 16));
				decodeIndex += 9;
				lineNumber++;
			} else {
				try {
					dump[decodeIndex] = String.valueOf((char) (int) Integer.valueOf(hexCode, 16));
					decodeIndex++;
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}

		}
	}

	public String fetch(int address) {
		if (address < 0x0000 || address > 0xFFFF) {
			throw new IllegalArgumentException("Invalid address");
		}
		return dump[address * 2 - address % 8] + dump[(address + 1) * 2 - (address + 1) % 8]
				+ dump[(address + 2) * 2 - (address + 2) % 8];
	}

	public String getMemory(int address) {
		if (address < 0x0000 || address > 0xFFFF) {
			throw new IllegalArgumentException("Invalid address");
		}
		return dump[address * 2 - address % 8];
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder();
		int lineNumber = 0;
		for (int i = 0; i < SIZE - 1; i += 16) {
			output.append(String.format("%-" + 5 + "s", String.format("%04X", lineNumber)));
			output.append("|");

			// objCode
			for (int offset = 0; offset <= 7; offset++) {
				output.append(this.dump[i + offset] + " ");
			}

			output.append("| ");

			// decode
			for (int offset = 0; offset <= 7; offset++) {
				output.append(this.dump[i + 8 + offset]);
			}
			output.append("\n");
			lineNumber += 8;

		}

		return output.toString();
	}

	public static void main(String[] args) {
		MemoryDump m = new MemoryDump();

		// Sample run
		m.updateMemory("41 20 73 69 6D 70 6C 65 20 4A 61 76 61 20 50 72 6F 67 72 61 6D");

		System.out.println(m.fetch(0x000));
	}
}
