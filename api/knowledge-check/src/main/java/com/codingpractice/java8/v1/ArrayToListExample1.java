package com.codingpractice.java8.v1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ArrayToListExample1 {
    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4};
        List<Integer> list = IntStream.of(numbers).mapToObj(Integer::valueOf).collect(Collectors.toList());
        System.out.println(list);
    }
}
