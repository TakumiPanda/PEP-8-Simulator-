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
110000000000000000000111 111000000000000000001010 00000000

Command will Load the A register with the value of 1(in binary). Then perform an ADD operation to increment by one
110000000000000000000001 011100000000000000000001 00000000
In assembly
LDR 1, i
ADDR 1, i
STOP
.END

Command will Load the A register with the value of 1. Then add a -1 to it. Should trigger the Z addressing bit.
110000000000000000000001 100000000000000000000001 00000000
LDR 1, i
SUBR 1, i
STOP
.END

This command will print out Hello in Assembly
CHARO 72,i
CHARO 101,i
CHARO 108,i
CHARO 108,i
CHARO 111,i
STOP
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
