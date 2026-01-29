package com.core.string;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Given a string, find the first unique character.
 */

public class FirstUniqueCharacterInString {

    public static void main(String[] args) {
        String input = "swiss";

        Character result = findFirstUniqueChar(input);

        if (result != null) {
            System.out.println("First unique character: " + result);
        } else {
            System.out.println("No unique character found");
        }
    }

    /**
     * I used LinkedHashMap to preserve insertion order while counting character frequency,
     * then returned the first character with frequency one.
     *
     */
    public static Character findFirstUniqueChar(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }

        Map<Character, Integer> countMap = new LinkedHashMap<>();

        // 1 Count characters
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        // 2 Find first unique
        for (Map.Entry<Character, Integer> entry : countMap.entrySet()) {
            if (entry.getValue() == 1) {
                return entry.getKey();
            }
        }

        return null;
    }
}
