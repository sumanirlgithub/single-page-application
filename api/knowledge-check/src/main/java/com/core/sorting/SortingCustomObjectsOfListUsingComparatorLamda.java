package com.core.sorting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortingCustomObjectsOfListUsingComparatorLamda {

    public static void main(String[] args) {
        // create a list of Employee objects:
        List<Employee> employees = Arrays.asList(
                new Employee("George", 10, 10000),
                new Employee("Robert", 12, 15000),
                new Employee("Kathy", 24, 25000)
        );

        Collections.sort(employees, (e1, e2) -> e1.getName().compareTo(e2.getName()));
        employees.forEach(System.out::println);
    }

}
