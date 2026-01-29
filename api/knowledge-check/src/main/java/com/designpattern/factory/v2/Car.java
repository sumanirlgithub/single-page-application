package com.designpattern.factory.v2;

public class Car implements Vehicle {

    @Override
    public void start() {
        System.out.println("Can engine started with key ignition");
    }

    @Override
    public void stop() {
        System.out.println("Car engine stopped");
    }

    @Override
    public String getType() {
        return "Car";
    }

}
