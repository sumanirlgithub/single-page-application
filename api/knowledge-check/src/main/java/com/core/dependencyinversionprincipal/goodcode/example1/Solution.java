package com.core.dependencyinversionprincipal.goodcode.example1;

/**
 * This is loose coupling or decoupling code.
 */
class Solution {
    public static void main(String[] args) {
        Engine gasEngine = new GasEngine();
        Car car = new Car(gasEngine);
        car.start();
    }
}

interface Engine {
    void start();
}
class GasEngine implements Engine{
    @Override
    public void start() {
        System.out.println("Gas engine starting...");
    }
}

class Car {
    private Engine engine;

    Car(Engine engine) {
        this.engine = engine;
    }

    void start() {
        engine.start();
    }
}