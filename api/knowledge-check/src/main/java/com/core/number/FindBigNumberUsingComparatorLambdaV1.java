package com.core.number;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Find a big number from list using lambda expression
 */
public class FindBigNumberUsingComparatorLambdaV1 {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 18, 2, 18, 7, 3);

        int max = numbers.stream()
                //.max((a, b) -> a.compareTo(b))
                .max(Integer::compareTo)
                .orElseThrow(() -> new RuntimeException("List is empty"));

        System.out.println("largest number is: " + max);
    }
}
