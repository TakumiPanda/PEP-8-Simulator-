package view;

import controller.Simulator;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            new Simulator();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*
This command will print Hello in Hex Code
500048 500065 50006C 50006C 50006F 00

Command will print Hello in Machine Code
010100000000000001001000 010100000000000001100101 010100000000000001101100 010100000000000001101100 010100000000000001101111 00000000

Command will execute the Branch unconditional instruction
000001010000000000000000 00000000

Command will Load 0111 into A register than Store A register value into Memory[1010]
110000000000000000000111 111000010000000000001010 00000000

Command will Load the A register with the value of 1(in binary). Then perform an ADD operation to increment by one
110000000000000000000001 011100000000000000000001 00000000

This command will print out Hello in Assembly
charo 0x0048,d ; Output a 'H'
charo 0x0065,d ; Output a 'e'
charo 0x006C,d ; Output a 'l'
charo 0x006C,d ; Output a 'l'
charo 0x006F,d ; Output a 'o'
STOP          ; End program
.END


Program that does something
	CHARI   1,d
	LDr     5,i
while:   	LDr 4,d
         	BREQ    endWh
         	CHARO   1,d
         	CHARI   1,d
         	BR      while
endWh:   	STOP

*/
