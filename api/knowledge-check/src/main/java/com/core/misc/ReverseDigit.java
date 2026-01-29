package com.core.misc;

/**
 * Your task is to construct a function that accepts an integer n and returns the integer with the same digits as n, but in reverse order.
 * You should implement your solution using a while loop.
 *
 * For instance, if the input is 12345, the output should be 54321.
 *
 * Do not use built-in functions that convert the integer to another data type, such as a string, to reverse it.
 * Solve the problem purely using mathematical operations and loop constructs. Note that when the result has leading zeros,
 * you should consider only the integer value (e.g., 1230 becomes 321 after the operation).
 */
public class ReverseDigit {

    public static void main(String[] args) {
        int n = 12;
        int result = 0;
        while ( n > 0) {
            int lastDigit = n % 10;
            if (lastDigit != 0) {
                result = result * 10 + lastDigit;
            }
            n = n / 10;
        }
        System.out.println(result);
    }
}
