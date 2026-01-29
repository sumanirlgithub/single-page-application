package com.core.array;

/**
 * You are given a number n. Your task is to write a function that will return the n-th prime number.
 *
 * For example, if n is 1, the function should return 2. If n is 3, the function should return the third prime number, which is 5.
 */
public class GetPrimeNumber {

    public static void main(String[] args) {
        int n = 6;
        int nthPrimeNumber = nthPrime(n);
        System.out.println(nthPrimeNumber);
    }

    public static int nthPrime(int n) {
        // TODO: implement the function
        //n = 6;
        int nthPrimeNumber = 2;
        boolean keepGoing = true;
        int totalCount = 1;
        int i = 2;
        while (keepGoing) {
            boolean isPrime = true;
            for (int j = 2; j <= Math.sqrt(i); j ++) {
                if ( i % j == 0) {
                    isPrime = false;
                    break;
                }
            } //  for loop
            if (isPrime) {
                if (totalCount == n) {
                    nthPrimeNumber = i;
                    break;
                }
                totalCount++;
            }
            i++;

        } // while loop

        return nthPrimeNumber;
    }

}
