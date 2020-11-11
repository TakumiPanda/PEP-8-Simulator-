package view;

import java.io.IOException;

import controller.Simulator;

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

This command will print out Hello in Assembly
STRO 0x0048,d ; Output a 'H'
STRO 0x0065,d ; Output a 'e'
STRO 0x006C,d ; Output a 'l'
STRO 0x006C,d ; Output a 'l'
STRO 0x006F,d ; Output a 'o'
STOP          ; End program
.END
*/
