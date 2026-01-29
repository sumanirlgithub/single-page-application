package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;

public class DistinctElements {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 2, 4, 5, 5);
        numbers.stream().distinct().forEach(System.out::println);
    }

}
