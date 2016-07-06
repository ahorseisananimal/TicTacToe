package com.nastya.tictactoe.playground;

public class Playground {
    public static void main(String[] args) {
        Car car1 = new Car();
        car1.carColor = CarColor.BLUE;
        car1.type = CarType.HYBRID;

        Car car2 = new Car();
        car2.carColor = CarColor.RED;
        car2.type = CarType.DIESEL;

        car1.printInfo();
        car2.printInfo();
    }
}
