package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * print distinct elements from a list of integers.
 */
public class DistinctElements {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 2, 3, 4, 4);
        List<Integer> result = numbers.stream().distinct().collect(Collectors.toList());
        System.out.println(result);
    }
}
