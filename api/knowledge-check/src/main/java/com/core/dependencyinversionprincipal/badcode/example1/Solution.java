package com.core.dependencyinversionprincipal.badcode.example1;

/**
 * This is a tight-coupling code.
 */
class Solution {
    public static void main(String[] args) {
        Car car = new Car();
        car.start();
    }
}

class GasEngine {
    void start() {
        System.out.println("Gas engine starting...");
    }
}

class Car {
    private GasEngine engine;

    Car() {
        engine = new GasEngine(); // Direct dependency
    }

    void start() {
        engine.start();
    }
}