package com.core.number;

import java.util.Comparator;

/**
 * Find a big number from any two numbers
 */
public class FindBigNumberUsingComparator {

    public static void main(String[] args) {

        int n1 = 10;
        int n2 = 15;
        int  result = new NumberComparator().compare(n1, n2);
        if (result < 0) {
            System.out.println("largest number is: " + n2);
        } else if ( result > 0) {
            System.out.println("largest number is: " + n1);
        } else {
            System.out.println("both numbers are equals");
        }

    }

    static class NumberComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer n1, Integer n2) {
            return n1.compareTo(n2) ;
        }
    }
}
