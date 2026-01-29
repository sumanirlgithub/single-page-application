package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * check if any number in a list is even.
 */
public class AnyMatchExample {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        boolean hasEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        System.out.println(hasEven); // Output: true
    }
}
