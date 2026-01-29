package com.core.number;

import java.util.Comparator;

/**
 * Find a big number from any two numbers using lambda expression
 */
public class FindBigNumberUsingComparatorLambda {

    public static void main(String[] args) {

        int a = 10;
        int b = 15;

        Comparator<Integer> comparator = Integer::compareTo;
        int result= comparator.compare(a, b);
        if (result < 0) {
            System.out.println("largest number is: " + b);
        } else if ( result > 0) {
            System.out.println("largest number is: " + a);
        } else {
            System.out.println("both numbers are equals");
        }
    }
}
