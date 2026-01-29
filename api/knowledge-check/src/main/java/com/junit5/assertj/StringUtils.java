package com.junit5.assertj;

/**
 * Utility class for common string operations.
 * Built using Test-Driven Development principles.
 */
public class StringUtils {

    /**
     * Private constructor to prevent instantiation of utility class
     */
    private StringUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Checks if a string is null or empty.
     *
     * @param str the string to check
     * @return true if string is null or empty, false otherwise
     */
    public static boolean isEmpty(String str) {
        // Minimal implementation to make tests pass
        return str == null || str.isEmpty();
    }

    /**
     * Reverses a string.
     *
     * @param str the string to reverse
     * @return the reversed string, or null if input is null
     */
    public static String reverse(String str) {
        // Handle null input
        if (str == null) {
            return null;
        }

        // Handle empty string
        if (str.isEmpty()) {
            return "";
        }

        // Reverse the string using StringBuilder for efficiency
        return new StringBuilder(str).reverse().toString();
    }
}