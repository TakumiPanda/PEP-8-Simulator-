package model;


public class MemoryDump{
	public String[] dump;
	private static final int SIZE = 131073;
	
	public MemoryDump(){
		this.dump = new String[SIZE];
		for (int i = 0; i < SIZE - 1; i+=16){
			// objCode
			for (int offset = 1; offset <= 8; offset++){
				// FILL OBJECT CODE HERE
				this.dump[i + offset] = "00";
			}
			
			// decode
			for (int offset = 1; offset <= 8; offset++){
				// FILL IN DECODE HERE
				this.dump[i + 8 + offset] = ".";
			}
		}
	}
	
	public void updateMemory(String objCode) {
		int objCodeIndex = 1;
		int decodeIndex = 9;
		if (objCode.replace(" ",  "").length() % 2 != 0) {
			System.err.println("Invlid hex string.");
			return;	
		}
		//[1  2  3  4  5  6  7  8]  [9  10 11 12 13 14 15 16]
		//[17 18 19 20 21 22 23 24] [25 26 27 28 29 30 31 32]
		String[] hexCode = objCode.split("\\s+");
		for(int i = 0; i < hexCode.length; i++) {
			if (objCodeIndex % 8 == 0) {
				dump[objCodeIndex] = hexCode[i];
				objCodeIndex += 9;
			} else {
				dump[objCodeIndex] = hexCode[i];
				objCodeIndex ++;
			}


			if (decodeIndex % 16 == 0) {
				dump[decodeIndex] = String.valueOf((char)(int)Integer.valueOf(hexCode[i],16));
				decodeIndex += 9;
			} else {
				dump[decodeIndex] = String.valueOf((char)(int)Integer.valueOf(hexCode[i],16));
				decodeIndex ++;
			}

		}
	}
	
	public String toString(){
		StringBuilder output = new StringBuilder();
		int lineNumber = 0;
		for (int i = 0; i < SIZE - 1; i+=16){
			output.append(String.format("%04X", lineNumber));
			output.append(" | ");
			
			// objCode
			for (int offset = 1; offset <= 8; offset++){
				output.append(this.dump[i + offset] + " ");
			}
			
			output.append(" | ");
			
			// decode
			for (int offset = 1; offset <= 8; offset++){
				output.append(this.dump[i + 8 + offset]);
			}
			output.append("\n");
			lineNumber += 8;
			
		}
		
		return output.toString();
	}
	
	
	
	public static void main (String[] args)
	{
		MemoryDump m = new MemoryDump();
		
		//Sample run
		//m.updateMemory("41 20 73 69 6D 70 6C 65 20 4A 61 76 61 20 50 72 6F 67 72 61 6D");
		
		System.out.println(m.toString());
	}
}
