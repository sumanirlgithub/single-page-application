package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectSquaresToNewList {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);
        List<Integer> result = numbers.stream().map(n -> n * n).collect(Collectors.toList());
        System.out.println(result);
    }
}
