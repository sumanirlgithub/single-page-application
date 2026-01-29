package com.java8.filter;

import java.util.Arrays;

/**
 * count occurrences of each element in an array.
 */
public class CountOccurrences {

    public static void main(String[] args) {
        int[] num = {10, 20, 30, 20, 10, 20, 50};
        int[] uniqueNum = Arrays.stream(num).distinct().toArray();
        for(int i = 0; i < uniqueNum.length; i++) {
            int finalI = i;
            long count = Arrays.stream(num)
                    .filter(n -> n == num[finalI])
                    .count();
            System.out.println(uniqueNum[i] + " -> " + count);
        }
    }
}
