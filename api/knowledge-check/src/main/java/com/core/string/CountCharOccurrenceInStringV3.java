package com.core.string;

import java.util.*;

/**
 * Given a string, find characters that appear more than once.
 */
public class CountCharOccurrenceInStringV3 {

    public static void main(String[] args) {
        String s = "helloe";

        Map<Character, Integer> countMap = new HashMap<>();
        List<Character> chars = new ArrayList<>();

        for (Character c: s.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> characterIntegerEntry : countMap.entrySet()) {
            char c = characterIntegerEntry.getKey();
            if (countMap.get(c) > 1) {
                chars.add(c);
            }
        }

        System.out.println(chars);
    }
}
