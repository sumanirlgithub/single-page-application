package com.core.equalhashcode;

import com.core.equalhashcode.model.EmployeeOverrideEqualAndHashCode;

import java.util.HashSet;
import java.util.Set;

public class HashSetExample2 {
    public static void main(String[] args) {
        EmployeeOverrideEqualAndHashCode emp1= new EmployeeOverrideEqualAndHashCode("E001","jack","manager",1000);
        EmployeeOverrideEqualAndHashCode emp2= new EmployeeOverrideEqualAndHashCode("E001","jack","manager",1000);

        Set<EmployeeOverrideEqualAndHashCode> employeeSet = new HashSet<>();
        employeeSet.add(emp1);
        employeeSet.add(emp2);

        System.out.println(employeeSet.size());

    }
}

//  In this example as equals() and hasCode() methods are overridden in EmployeeOverrideEqualAndHashCode class
// As a result, the set will contain only one object.