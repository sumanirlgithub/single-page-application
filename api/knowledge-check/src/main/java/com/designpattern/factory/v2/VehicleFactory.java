package com.designpattern.factory.v2;

public class VehicleFactory {

    public static  Vehicle createVehicle(String vehicleType) {
        vehicleType = vehicleType.toUpperCase();
        switch (vehicleType) {
            case "Car":
                return new Car();
            case "motorcycle":
                return new Motorcycle();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }
}
