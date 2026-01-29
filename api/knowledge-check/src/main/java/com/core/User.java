package com.core;

public class User implements Cloneable {

    public String name;
    public Department department;

    public User(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return this.name + ": " + this.department;
    }
}