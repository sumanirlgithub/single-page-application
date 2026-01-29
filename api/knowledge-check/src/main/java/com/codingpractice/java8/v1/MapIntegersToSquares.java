package com.codingpractice.java8.v1;


import java.util.Arrays;
import java.util.List;

/**
 * Map integers to their squares and print results.
 */
public class MapIntegersToSquares {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream()
                .map(num -> num * num)
                .forEach(System.out::println);
    }

}
