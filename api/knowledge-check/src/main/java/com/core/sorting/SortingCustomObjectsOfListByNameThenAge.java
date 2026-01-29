    package com.core.sorting;

import java.util.*;

    public class SortingCustomObjectsOfListByNameThenAge {

        public static void main(String[] args) {
            // create a list of Employee objects:
            List<Employee> employees = Arrays.asList(
                    new Employee("George", 10, 10000),
                    new Employee("Robert", 12, 15000),
                    new Employee("Kathy", 24, 25000)
            );

            employees.sort(
                    Comparator.comparing(Employee::getName)
                            .thenComparing(Employee::getAge));
            employees.forEach(System.out::println);
        }

    }
