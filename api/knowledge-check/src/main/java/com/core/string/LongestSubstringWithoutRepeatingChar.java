package com.core.string;

import java.util.HashMap;
import java.util.Map;

public class LongestSubstringWithoutRepeatingChar {

    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        Map<Character, Integer> indexMap = new HashMap<>();
        int left = 0;
        int maxLen = 0;
        int windowLen;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // If character seen before and inside current window
            if (indexMap.containsKey(c) && indexMap.get(c) >= left) {
                left = indexMap.get(c) + 1; // change left index
            }

            indexMap.put(c, right);
            windowLen = (right - left) + 1; // change window length
            maxLen = Math.max(maxLen, windowLen);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcabcbb")); // 3
        System.out.println(lengthOfLongestSubstring("bbbbb"));    // 1
        System.out.println(lengthOfLongestSubstring("pwwkew"));   // 3
    }
}
