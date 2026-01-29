package com.core.array;

import java.util.ArrayList;
import java.util.List;

/**
 * ou are provided with an array of n integers, where n can range from 1 to 200, inclusive. Your task is to create a new array that takes two pairs of
 * 'opposite' elements from the original array at each iteration, starting from the center and moving towards both ends, to calculate the resulting
 * multiplication of each pair.
 *
 * By 'opposite' elements, we mean pairs of elements symmetrically located relative to the array's center. If the array's length is odd,
 * the center element doesn't have an opposite and should be included in the result array as is.
 *
 * Each element in the array can range from -100 to 100, inclusive.
 *
 * For example, if the input array is [1, 2, 3, 4, 5], the returned array should be [3, 8, 5]. This is because the center element 3 remains as it is,
 * the multiplication of 2 and 4 is 8, and the multiplication of 1 and 5 is 5.
 */
public class ArrayTraversal1 {
    public static void main(String[] args) {

        // TODO: Implement the solution here.
        int[] numbers = new int[] {1, 2, 3, 4, 5};
        //numbers = new int[] {1, 2, 3, 4, 5, 6};
        //numbers = new int[] {1, 2};
        //numbers = new int[] {1, 2, 3};
        List<Integer> result = new ArrayList<>();
        int j = 1;
        if (numbers.length % 2 == 0) {
            int centerIndex = (numbers.length / 2) - 1;
            for (int i = centerIndex; i >= 0; i--) {
                int val  = numbers[i] * numbers[i + j];
                result.add(val);
                j = j + 2;
            } // end for lopp
        }
        j = 2;
        if (numbers.length % 2 != 0) {
            int centerIndex = ((numbers.length + 1) / 2) - 1;
            result.add(numbers[centerIndex]);
            System.out.println(centerIndex);
            for (int i = centerIndex -1; i >= 0; i--) {
                int val  = numbers[i] * numbers[(i) + j];
                result.add(val);
                j = j + 2;
            } // end for lopp
        }
        System.out.println(result);

    }
}
