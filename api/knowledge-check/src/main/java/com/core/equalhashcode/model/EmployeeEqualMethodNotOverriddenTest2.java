package com.core.equalhashcode.model;

public class EmployeeEqualMethodNotOverriddenTest2 {
    public static void main(String[] args) {
        EmployeeEqualMethodNotOverridden emp1= new EmployeeEqualMethodNotOverridden("E001","jack","manager",1000);
        EmployeeEqualMethodNotOverridden emp2= emp1;

        System.out.println("Using equals for comparing emp1 and emp2 -  "+ emp1.equals(emp2));
        System.out.println("Using == for comparing emp1 and emp2 -  "+ (emp1==emp2));
    }
}
