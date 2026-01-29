package com.core.string;

import java.util.HashMap;
import java.util.Map;


/**
 * Count occurrence of each character in string
 */
public class CountCharOccurrenceInString {
    public static void main(String[] args) {
        String s = "hello";
        Map<Character, Integer> countMap = new HashMap<>();

        for (char c : s.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        System.out.println(countMap);
    }
}
