package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * collect squares of numbers into a new list.
 */
public class CollectToNewList {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3);
        List<Integer> result = numbers.stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println(result);
    }
}
