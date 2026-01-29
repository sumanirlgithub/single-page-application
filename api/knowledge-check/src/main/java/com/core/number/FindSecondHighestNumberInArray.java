package com.core.number;

/**
 * Find 2nd highest number in given array.
 */
public class FindSecondHighestNumberInArray {

    public static void main(String[] args) {

        int[] arr = {5, 9, 2, 13, 42, 56, 24, 36};
        int highest = 0;
        int secondHighest = 0;

        for (int num : arr) {
            if (num > highest) {
                secondHighest = highest;
                highest = num;
            } else if (num > secondHighest && num < highest) {
                secondHighest = num;
            }
        }

        if (secondHighest == 0) {
            throw new IllegalArgumentException("No second highest element");
        }

        System.out.println(secondHighest);

    }
}
