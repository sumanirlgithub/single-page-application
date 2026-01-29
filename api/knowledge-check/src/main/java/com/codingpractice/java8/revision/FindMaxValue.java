package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;

public class FindMaxValue {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 8, 2, 4, 3);
        System.out.println(numbers.stream().max(Integer::compare).get());
    }
}
