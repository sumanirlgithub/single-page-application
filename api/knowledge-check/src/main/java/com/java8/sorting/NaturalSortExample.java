package com.java8.sorting;

import java.util.List;
import java.util.stream.Collectors;

public class NaturalSortExample {

    public static void main(String[] args) {
        List<String> sortedNames = List.of("Zara", "Ramesh", "Amit").stream()
                .sorted()
                .collect(Collectors.toList());
        sortedNames.forEach(System.out::println);
    }
}
