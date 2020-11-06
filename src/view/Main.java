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
This command will print Hello
500048 500065 50006C 50006C 50006F 00
*/
