package com.nastya.tictactoe.playground;

public class Car {
    public CarColor carColor;
    public CarType type;

    public void printInfo() {
        System.out.println("my color is: " + carColor.getRussianName()
                + ";");
    }
}
