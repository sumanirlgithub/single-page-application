package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;

/**
 *
 * count and print the number of elements in a list.
 */
public class CountElements {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 6, 5, 4);
        long count = numbers.stream().count();
        System.out.println(count);
    }
}
