package com.core;

public class UserDeep implements Cloneable {

    public String name;
    public Department department;

    public UserDeep(String name, Department department) {
        this.name = name;
        this.department = department;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return new User(this.name, new Department(this.department.id, this.department.departmentName));
    }

    @Override
    public String toString() {
        return this.name + ": " + this.department;
    }
}