package com.junit5.assretj;

import com.junit5.assertj.StringUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for StringUtils following TDD principles.
 * Each test method follows the pattern: Given-When-Then
 */
class StringUtilsTest {

    /**
     * Test: isEmpty() should return true for null string
     * This is our first RED test - it will fail because StringUtils doesn't exist yet
     */
    @Test
    void isEmpty_ShouldReturnTrue_WhenStringIsNull() {
        // Given: a null string
        String input = null;

        // When: checking if string is empty
        boolean result = StringUtils.isEmpty(input);

        // Then: result should be true
        assertTrue(result, "isEmpty() should return true for null string");
    }

    /**
     * Test: isEmpty() should return true for empty string
     */
    @Test
    void isEmpty_ShouldReturnTrue_WhenStringIsEmpty() {
        // Given: an empty string
        String input = "";

        // When: checking if string is empty
        boolean result = StringUtils.isEmpty(input);

        // Then: result should be true
        assertTrue(result, "isEmpty() should return true for empty string");
    }

    /**
     * Test: isEmpty() should return false for non-empty string
     */
    @Test
    void isEmpty_ShouldReturnFalse_WhenStringHasContent() {
        // Given: a string with content
        String input = "Hello";

        // When: checking if string is empty
        boolean result = StringUtils.isEmpty(input);

        // Then: result should be false
        assertFalse(result, "isEmpty() should return false for non-empty string");
    }

    /**
     * Test: reverse() should return null for null input
     */
    @Test
    void reverse_ShouldReturnNull_WhenInputIsNull() {
        // Given: a null string
        String input = null;

        // When: reversing the string
        String result = StringUtils.reverse(input);

        // Then: result should be null
        assertNull(result, "reverse() should return null for null input");
    }

    /**
     * Test: reverse() should return empty string for empty input
     */
    @Test
    void reverse_ShouldReturnEmpty_WhenInputIsEmpty() {
        // Given: an empty string
        String input = "";

        // When: reversing the string
        String result = StringUtils.reverse(input);

        // Then: result should be empty
        assertEquals("", result, "reverse() should return empty string for empty input");
    }

    /**
     * Test: reverse() should reverse normal string
     */
    @Test
    void reverse_ShouldReverseString_WhenInputIsValid() {
        // Given: a normal string
        String input = "hello";

        // When: reversing the string
        String result = StringUtils.reverse(input);

        // Then: result should be reversed
        assertEquals("olleh", result, "reverse() should return reversed string");
    }
}