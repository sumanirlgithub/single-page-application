package com.core.solidprinciples.interfacesegregation.badcode;

/**
 * In this task, we will focus on implementing the Interface Segregation Principle. The existing code involves a Vehicle interface that combines
 * functionalities needed by different types of vehicles, leading to unused and unnecessary method implementations. Your job is to refactor the code by
 * breaking down this interface into smaller, more specific interfaces that cater to the needs of different types of vehicles.
 *
 * The current setup complicates the class design by forcing unrelated functionalities onto different vehicle types. Let's streamline the interfaces and
 * ensure that each vehicle implements only the methods relevant to its functionality!
 */
class Solution {
    public static void main(String[] args) {
        Car car = new Car();
        car.drive();
        car.openDoor();

        Bicycle bicycle = new Bicycle();
        bicycle.drive();
    }
}

interface Vehicle {
    void drive();
    void openDoor();
    void pedal();
}

class Car implements Vehicle {
    @Override
    public void drive() {
        System.out.println("Car is driving");
    }

    @Override
    public void openDoor() {
        System.out.println("Car door is opening");
    }

    @Override
    public void pedal() {
        // Not applicable for Car
    }
}

class Bicycle implements Vehicle {
    @Override
    public void drive() {
        // Bicycle doesn't drive like a car
    }

    @Override
    public void openDoor() {
        // Not applicable for Bicycle
    }

    @Override
    public void pedal() {
        System.out.println("Bicycle is pedaling");
    }
}