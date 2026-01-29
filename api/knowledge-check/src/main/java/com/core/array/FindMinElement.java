package com.core.array;

/**
 * You are given an array of integers. Your task is to write a function findMinElement, that returns the minimum number from the array without using
 * Java's built-in methods such as Arrays.stream().min().
 *
 * If the array is empty, your function should return null.
 */
public class FindMinElement {
    public static void main(String[] args) {

        int[] array = new int[]{3, 6, 2, 5, 4, 9};
        if (array.length != 0) {
            int minNumber = array[0];
            for (int i = 1; i < array.length -1 ; i++) {
                if (array[i] <= array[i + 1]) {
                    if (array[i] < minNumber) {
                        minNumber = array[i];
                    }
                } else {
                    if (array[i + 1] < minNumber) {
                        minNumber = array[i + 1];
                    }
                }
            }
            if (array[array.length -1] < minNumber) {
                minNumber = array[array.length -1];
            }
            System.out.println(minNumber);
        }
    }
}
