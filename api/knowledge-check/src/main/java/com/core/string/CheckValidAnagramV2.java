package com.core.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Two strings are anagrams if they are equal in length, nad they have the exact same characters with the exact same frequencies.
 * Example - listen and silent
 * here we are using only one map which is more efficient memory wise
 */
public class CheckValidAnagramV2 {

    public static void main(String[] args) {
        String s1 = "listen";
        String s2 = "silent";

        System.out.println(isAnagram(s1, s2));
    }

    public static boolean isAnagram(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) {
            return false;
        }

        Map<Character, Integer> countMap = new HashMap<>();

        // Count characters in s
        for (char c : s1.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }

        // Decrease count using t
        for (char c : s2.toCharArray()) {
            if (!countMap.containsKey(c)) {
                return false;
            }

            countMap.put(c, countMap.get(c) - 1);

            if (countMap.get(c) == 0) {
                countMap.remove(c);
            }
        }

        return countMap.isEmpty();
    }
}

