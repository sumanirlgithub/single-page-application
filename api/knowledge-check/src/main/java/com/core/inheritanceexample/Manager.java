package com.core.inheritanceexample;

public class Manager extends Employee {
    // TODO: Add a private field teamSize to Manager
    // TODO: Implement a constructor in Manager that initializes name, age, employeeId, department, and teamSize
    // TODO: Create a displayManagerDetails method that calls displayEmployeeDetails() and then prints teamSize

    private int teamSize;

    public Manager(String name, int age, String employeeId, String department, int teamSize) {
        super(name, age, employeeId, department);
        this.teamSize = teamSize;
    }


    @Override
    public void displayEmployeeDetails() {
        super.displayEmployeeDetails();
        System.out.println("teamSize: " + teamSize);

    }

}