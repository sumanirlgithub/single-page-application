package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * print names sorted in alphabetical order from a list.
 */
public class SortNameInAlphabeticOrder {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Charlie", "Alice", "Bob");
        names.stream().sorted().forEach(System.out::println);
    }
}
