package com.core.string;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Key point:
 * entrySet() does NOT guarantee order with HashMap.
 * So to get the first non-repeated character, we must use LinkedHashMap.
 */
public class FindFirstNonRepeatingCharacterUsingHashmap {

    public static void main(String[] args) {

        String s = "hello";

        Map<Character, Integer> map = new LinkedHashMap<>();

        // Count characters (insertion order preserved)
        for (char c : s.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        // Iterate using entrySet()
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                System.out.println(entry.getKey());
                break;
            }
        }

    }
}
