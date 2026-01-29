package com.core.array;

/**
 * You are provided with two integers, a and b. Your task is to write a Java function that checks whether both a and b are coprime or not.
 * Two numbers are said to be coprime or mutually prime if the only positive integer that divides both of them is 1
 *
 * System.out.println(solution.areCoprime(15, 28));   // Output: true
 * System.out.println(solution.areCoprime(12, 18));   // Output: false
 */
public class FindTwoNumbersAreCoPrime {

    public static void main(String[] args) {
        int a = 17;
        int b = 51;

        boolean result = areCoprime(a, b);
        System.out.println(result);
    }

    public static  boolean areCoprime(int a, int b) {
        // TODO: implement
        //a = 17;
        //b = 51;

        if (gcd(a, b) == 1) {
            return true;
        }
        return false;
    }

    /**
     * Find the greatest common divisor of two numbers - the Euclidean algorithm
     *
     */
    static private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return Math.abs(a);
    }
}
