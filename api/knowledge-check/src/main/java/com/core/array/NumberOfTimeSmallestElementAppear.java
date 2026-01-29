package com.core.array;

/**
 * You are given an array of integers. Your task is to write a function in Java that returns the number of times the smallest element appears in the array.
 *
 * Please note that built-in methods such as Collections.min() or Collections.frequency() should not be used in this task. Your goal is to
 * accomplish this task by iterating over the array elements manually. Try to solve the task by doing just a single array traversal.
 */
public class NumberOfTimeSmallestElementAppear {

    public static void main(String[] args) {
        int[] numbers = new int[] {2, 5, 3, 2, 6, 6, 2};
        int count = countMin(numbers);
    }

    public static int countMin(int[] numbers) {
        // TODO: Implement this function to count the smallest integer in the array.

        if (numbers.length == 0) {
            return 0;
        }
        int smallestNumber = numbers[0];
        int count = 1;
        for (int i = 1; i < numbers.length; i++) {

            if (numbers[i] == smallestNumber) {
                count++;
            } else if (numbers[i] < smallestNumber) {
                smallestNumber = numbers[i];
                count =  1;
            }
        } // end for loop
        System.out.println(count);
        return count;
    }
}
