package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * filter and print even numbers from a list.
 */
public class FilterEvenNumbers {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

//        numbers.stream()
//                .filter( n -> n % 2 == 0)
//                .forEach(System.out::println);

        List<Integer> evenNumbers = numbers
                .stream()
                .filter( n -> n % 2 == 0)
                .collect(Collectors.toList());
        System.out.println(evenNumbers);
    }
}
