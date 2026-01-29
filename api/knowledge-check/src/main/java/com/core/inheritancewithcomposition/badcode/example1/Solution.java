package com.core.inheritancewithcomposition.badcode.example1;

/**
 * The hierarchy is too deep, with Manager extending Employee, which extends Person.
 * Person having a work() method might be inappropriate because not every person works, making the base class less general.
 * The inheritance might be forced where a Manager "is-a" Person, but the middle class Employee may not be necessary as a separate entity.
 */
class Person {
    String name;
    int age;

    void work() {
        System.out.println("Person working");
    }
}

class Employee extends Person {
    String employeeId;

    void fileTaxes() {
        System.out.println("Employee filing taxes");
    }
}

class Manager extends Employee {
    void holdMeeting() {
        System.out.println("Manager holding a meeting");
    }
}