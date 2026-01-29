package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;

/**
 *
 * Reduce a list of integers to their sum.
 */
public class ReduceToSum {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 6, 5, 4);
        long sum = numbers
                .stream()
                .reduce(0, Integer::sum);
        System.out.println(sum);
    }
}
