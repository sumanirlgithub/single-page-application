package com.core.number;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Filter and print even numbers from a list.
 */
public class FindEvenNumbersFromList {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
        List<Integer> evenNumbers = new ArrayList<>();

        for (Integer n : numbers) {
            if (n % 2 == 0) {
                evenNumbers.add(n);
            }
        }

        System.out.println(evenNumbers);
    }
}
