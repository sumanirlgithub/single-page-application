package com.core.array;

/**
 * You are given an array of integers. Your job is to return the count of even and odd integers in the given array without using any built-in Java methods.
 *
 * Your function should return an array in the format {even_count, odd_count}, where even_count represents the number of even integers and
 * odd_count represents the number of odd integers in the provided array.
 */
public class CountOfEventAndOdd {

    public static void main(String[] args) {
        int[] nums = new int[] {1, 2, 3, 4, 5};
        int[] result = solution(nums);
    }

    public static int[] solution(int[] nums) {
        // TODO: implement the function to return an array {even_count, odd_count}
        if (nums.length == 0) {
            return new int[] {0, 0};
        } else {
            int event_count = 0;
            int odd_count = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] % 2 == 0) {
                    event_count++;
                } else {
                    odd_count++;
                }
            }
            int[] result = new int[]{event_count, odd_count};
             System.out.println("event number count: " + result[0]);
            System.out.println("odd number count: " + result[1]);
            return result;
        }
    }
}
