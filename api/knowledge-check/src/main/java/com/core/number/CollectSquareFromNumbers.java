package com.core.number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Collect squares of numbers into a new list
 */
public class CollectSquareFromNumbers {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4);

        List<Integer> squares = new ArrayList<>();

        for (Integer n : numbers) {
            squares.add(n * n);
        }

        System.out.println(squares);
    }
}
