package com.core.number;

import java.util.Arrays;
import java.util.List;

/**
 * Check if any number in a list is even
 */
public class FindEvenNumber {

    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 3, 5);

        for (Integer n : numbers) {
            if (n % 2 == 0) {
                System.out.println("List has an event number");
                break;
            }
        }
    }


}
