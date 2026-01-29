package com.core.number;

import java.util.Arrays;
import java.util.List;

/**
 * Calculate sum from list of numbers using Java 8 reduce
 */
public class CalculateSumFromNumbersUsingReduce {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 8, 5);
        /**
         * Because your lambda: (a, b) -> a + b
         * exactly matches an existing method: Integer.sum(int a, int b)
         * You can use method reference technique(Integer::sum) what which a short form of lambda
         */
        int sum = numbers.stream()
                //.reduce(0, ((a, b) -> a + b));
                .reduce(0, (Integer::sum));
        System.out.println(sum);
    }
}
