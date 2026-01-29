package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * limit to 3 elements and skip 1 element in a list, then print.
 */
public class LimitAndSkip {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        numbers.stream().limit(3).skip(1).forEach(System.out::println);

    }
}
