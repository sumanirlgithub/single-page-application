package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * print the lengths of each element (strings) in a list.
 */
public class PrintElementsLength {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("Java", "Python", "JavaScript");
        List<Integer> result = words.stream().map(e -> e.length()).collect(Collectors.toList());
        System.out.println(result);
    }
}
