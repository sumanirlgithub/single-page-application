package com.core.number;

import java.util.*;

/**
 * Print distinct elements from a list of integers.
 */
public class DistinctNumbersFromList {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 6, 5, 2, 5, 7);
        List<Integer> distinctNumbers = new ArrayList<>();

        Map<Integer, Integer> countMap = new HashMap<>();
        for (Integer n : numbers) {
            countMap.put(n, countMap.getOrDefault(n, 0) + 1);
        }

        for (Map.Entry<Integer, Integer> integerIntegerEntry : countMap.entrySet()) {
            Integer i = integerIntegerEntry.getKey();
            if (countMap.get(i) == 1) {
                distinctNumbers.add(i);
            }
        }
        System.out.println(distinctNumbers);
    }
}
