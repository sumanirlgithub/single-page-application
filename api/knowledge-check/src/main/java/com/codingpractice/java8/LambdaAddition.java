package com.codingpractice.java8;

import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * Add two integers using a lambda expression
 */
public class LambdaAddition {

    public static void main(String[] args) {
        SumCalculator sumCalculator = (a, b) -> a + b;
        int result = sumCalculator.sum(3, 5);
        System.out.println(result);
    }
}
