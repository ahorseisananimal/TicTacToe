package com.nastya.tictactoe.playground;

public enum CarColor {
    RED("Красный"), BLUE("Синий");

    private String russianName;


    CarColor(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }
}
