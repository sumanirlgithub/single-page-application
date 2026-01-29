package com.core.array;

public class FindSecondLargestNumber {

    public static void main(String[] args) {
        int[] nums = new int[] {3, 2, 1};
        Integer result = findSecondLargestNumber(nums);
    }

    public static Integer findSecondLargestNumber(int[] nums) {
        // TODO: Find the second largest number in nums
        int len = nums.length;
        if (len <= 1) {
            return null;
        }
        int largestNumber = Integer.MIN_VALUE;
        int secondLargetstNumber = Integer.MIN_VALUE;

        for (int i = 0; i < len; i++) {
            if (nums[i] > largestNumber) {
                secondLargetstNumber = largestNumber;
                largestNumber = nums[i];
            } else if (nums[i] < largestNumber && nums[i] > secondLargetstNumber) {
                secondLargetstNumber = nums[i];
            }
        }
        if (secondLargetstNumber == Integer.MIN_VALUE || secondLargetstNumber == largestNumber) {
            System.out.println("secondLargestNumber is null");
            return null;
        }
        System.out.println(secondLargetstNumber);
        return secondLargetstNumber;
    }
}
