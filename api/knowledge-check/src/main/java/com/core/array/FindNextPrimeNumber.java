package com.core.array;

/**
 * You are given an integer number,
 *
 * Your task is to write a function nextPrime(n), that takes an integer n as input and returns the smallest prime number larger than n.
 *
 * Here are some examples:
 *
 * nextPrime(7) should return 11, because 11 is the next prime number after 7.
 * nextPrime(13) should return 17, because 17 is the next prime number after 13.
 * nextPrime(50) should return 53, because 53 is the next prime number after 50.
 */
public class FindNextPrimeNumber {

    public static void main(String[] args) {
        int result = nextPrime(7);
    }

    public static int nextPrime(int n) {
        // TODO: implement the function to find the next prime number after n

        int i = n + 1;
        boolean keepGoing = true;
        int nextPrimeNumber = 1;
        while(keepGoing) {
            boolean isPrimeNumber = true;
            for (int j = 2; j <= Math.sqrt(i); j++) {
                if (i % j == 0 ) {
                    isPrimeNumber = false;
                }
            } // end for loop

            if (isPrimeNumber) {
                System.out.println(i + " is a next prime number");
                keepGoing = false;
                nextPrimeNumber = i;
            }
            i++;
        } // end while loop

        return nextPrimeNumber;
    }

}
