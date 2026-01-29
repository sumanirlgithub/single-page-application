package com.temp;

import java.util.Arrays;
import java.util.List;

public class LimitAndSkip {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        numbers.stream()
                .limit(3)
                .skip(1)
                .forEach(System.out::println);

    }
}
