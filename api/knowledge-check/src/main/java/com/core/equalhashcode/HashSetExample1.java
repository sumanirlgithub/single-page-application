package com.core.equalhashcode;

import com.core.equalhashcode.model.Employee;

import java.util.HashSet;
import java.util.Set;

public class HashSetExample1 {
    public static void main(String[] args) {
        Employee emp1= new Employee("E001","jack","manager",1000);
        Employee emp2= new Employee("E001","jack","manager",1000);

        Set<Employee> employeeSet = new HashSet<>();
        employeeSet.add(emp1);
        employeeSet.add(emp2);

        System.out.println(employeeSet.size());

    }
}

// In this example as equals() and hasCode() methods are not overridden in Employee class
// As a result, the set will contain duplicate objects, violating the desired behavior.