package com.designpattern.factory.v2;

public class ClientProgram {

    public static void main(String[] args) {
        System.out.println("=== Vehicle Manufacturing System ===\n");

        Vehicle vehicleCar = VehicleFactory.createVehicle("car");
        Vehicle vehicleMotorCycle = VehicleFactory.createVehicle("motorcycle");

        // processVehicleOrder(vehicleCar)
        // processVehicleOrder(vehicleMotorCycle)

    }
}
