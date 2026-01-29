package com.core.equalhashcode;

import com.core.equalhashcode.model.Employee;

public class EmployeeTest2 {
    public static void main(String[] args) {
        Employee emp1= new Employee("E001","jack","manager",1000);
        Employee emp2= emp1;

        System.out.println("Using equals for comparing emp1 and emp2 -  "+ emp1.equals(emp2));
        System.out.println("Using == for comparing emp1 and emp2 -  "+ (emp1==emp2));
    }

}
