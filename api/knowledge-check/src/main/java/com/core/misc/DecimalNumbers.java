package com.core.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ou are given an input array consisting of n integers ranging from 0 to 100, inclusive, where n represents the length of the array.
 * Your task is to write a function that returns a new array of strings. Each string should consist of two elements joined by a space:
 * an element from the input array paired with its geometric mean with the 'opposite' element. The 'opposite' element of any element in the array is
 * defined as the element at the corresponding position from the end of the array.
 */
public class DecimalNumbers {
    public static void main(String[] args) {

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numbers.size(); i ++) {
            String s1 = String.valueOf(numbers.get(i));
            int num = numbers.get(numbers.size() - (i +1));
            double d = Math.sqrt(num * numbers.get(i));
            String s2 = String.valueOf(String.format("%.2f", d));
            list.add(s1 + " " + s2);
        }
        System.out.println(list);

    }
}
