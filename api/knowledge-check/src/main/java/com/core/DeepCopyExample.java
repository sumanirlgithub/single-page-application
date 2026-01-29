package com.core;

public class DeepCopyExample {

    public static void main(String[] args) throws CloneNotSupportedException {

        UserDeep user = new UserDeep("John", new Department(1111L, "test"));
        User deepCopyUser = (User) user.clone();
        deepCopyUser.department.departmentName = "changed-value";

        System.out.println("Original Object: " + user);
        System.out.println("Shallow Copy User: " + deepCopyUser);

    }
}
