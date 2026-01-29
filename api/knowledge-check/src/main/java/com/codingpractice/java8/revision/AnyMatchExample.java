package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;

public class AnyMatchExample {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 3, 5, 7);

        boolean isEvenNumberPresent = numbers.stream().anyMatch(n -> n % 2 == 0);
        System.out.println(isEvenNumberPresent);

    }
}
