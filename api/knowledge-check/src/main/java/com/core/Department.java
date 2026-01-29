package com.core;

public class Department {

    long id;
    String departmentName;

    public Department(long id, String departmentName) {
        this.id  = id;
        this.departmentName = departmentName;
    }

    @Override
    public String toString() {
        return this.id + ": " + this.departmentName;
    }
}
