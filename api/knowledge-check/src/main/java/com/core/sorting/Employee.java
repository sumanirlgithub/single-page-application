package com.core.sorting;

public class Employee {
    private String name;
    private int age;

    private int salary;

    Employee(String name, int age, int salary){
        this.name=name;
        this.age=age;
        this.salary = salary;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    @Override
    public String toString() {
        return this.name + ":" + this.age + ":" + this.salary;
    }


}