package com.core.array;

import java.util.ArrayList;
import java.util.List;

/**
 * Your task is to create a Java function called getPrimeFactors(int n) that will return all unique prime factors of an integer n in a list.
 * A prime factor of n is a prime number that divides n without leaving a remainder. The expected complexity is
 *
 * Note that returned prime factors should be unique and sorted in ascending order in the resulting list.
 */
public class GetPrimeFactors {

    public static void main(String[] args) {
        int n = 18;
        List<Integer> factors = getPrimeFactors(n);
    }

    public static List<Integer> getPrimeFactors(int n) {
        // TODO: Implement the function that returns all prime factors of n
        // n = 18;
        List<Integer> factors = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            boolean isPrime = true;
            //System.out.println(i);
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0) {
                    isPrime = false;
                    break;
                }
            } // end 2nd for loop

            if (isPrime) {
                if (n % i == 0) {
                    System.out.println(i + " is a smallest prime number");
                    factors.add(i);
                    int q = n / i;
                    if (q != 1 && isPrimeNumber(q)) {
                        factors.add(q);
                        break;
                    }
                }
            }

        } // end first loop

        System.out.println(factors);
        return factors;
    }

    static boolean isPrimeNumber(int i) {
        for (int j = 2; j <= Math.sqrt(i); j++) {
            if (i % j == 0) {
                return false;
            }
        } // end 2nd for loop
        return true;
    }

}
