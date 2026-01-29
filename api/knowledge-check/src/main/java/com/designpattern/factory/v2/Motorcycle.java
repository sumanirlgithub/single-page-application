package com.designpattern.factory.v2;

public class Motorcycle implements Vehicle {

    @Override
    public void start() {
        System.out.println("Motorcycle engine started");
    }

    @Override
    public void stop() {
        System.out.println("Motorcycle engine stopped");
    }

    @Override
    public String getType() {
        return "Motorcycle";
    }
}
