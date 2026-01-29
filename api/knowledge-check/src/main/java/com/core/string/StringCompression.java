package com.core.string;

/**
 * Compress a string by replacing consecutive repeating characters with
 * character + count.
 */
public class StringCompression {

    public static String compress(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        int count = 1;

        for (int i = 1; i <= s.length(); i++) {
            // End of string OR next char is different
            if (i == s.length() || s.charAt(i) != s.charAt(i - 1)) {
                sb.append(s.charAt(i - 1));
                sb.append(count);
                count = 1; // reset
            } else {
                count++;
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(compress("aabcccccaaa")); // a2b1c5a3
        System.out.println(compress("abc"));         // a1b1c1
        System.out.println(compress("aaba"));          // a2
    }
}
