package com.core.solidprinciples.interfacesegregation.goodcode;

class Solution {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive();
        car.openDoor();

        Bicycle bicycle = new Bicycle();
        bicycle.pedal();
    }
}

interface Pedalable {
    void pedal();
}

interface Drivable {
    void drive();

}

interface DoorOpenable {
    void openDoor();
}


class Car implements Drivable, DoorOpenable {
    @Override
    public void drive() {
        System.out.println("Car is driving");
    }

    @Override
    public void openDoor() {
        System.out.println("Car door is opening");
    }

}

class Bicycle implements Pedalable {

    @Override
    public void pedal() {
        System.out.println("Bicycle is pedaling");
    }
}