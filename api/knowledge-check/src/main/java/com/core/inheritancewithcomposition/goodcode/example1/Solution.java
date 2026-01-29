package com.core.inheritancewithcomposition.goodcode.example1;

/**
 * Person no longer has a work() method, making it more general.
 * Employee now uses composition to include a Person object instead of inheriting from it. This simplifies the hierarchy.
 * Manager still inherits from Employee, maintaining logical structure but with reduced complexity.
 */
class Person {
    String name;
    int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

class Employee {
    Person personDetails;
    String employeeId;

    Employee(Person personDetails, String employeeId) {
        this.personDetails = personDetails;
        this.employeeId = employeeId;
    }

    void fileTaxes() {
        System.out.println(personDetails.name + " filing taxes");
    }
}

class Manager extends Employee {
    Manager(Person personDetails, String employeeId) {
        super(personDetails, employeeId);
    }

    void holdMeeting() {
        System.out.println(personDetails.name + " holding a meeting");
    }
}