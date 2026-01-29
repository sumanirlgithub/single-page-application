package com.core;

public class ShallowCopyExample {

    public static void main(String[] args) throws CloneNotSupportedException {

        Department department = new Department(1111L, "test");
        User user = new User("John", department);
        User shallowCopyUser = (User) user.clone();
        shallowCopyUser.department.departmentName = "changed-value";

        System.out.println("Original Object: " + user);
        System.out.println("Shallow Copy User: " + shallowCopyUser);

    }
}
