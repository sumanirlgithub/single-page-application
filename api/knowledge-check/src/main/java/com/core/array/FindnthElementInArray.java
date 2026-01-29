package com.core.array;

import java.util.Arrays;

public class FindnthElementInArray {

    public static void main(String[] args) {
        int[] array = new int[] {2, 7, 3, 1, 10, 5};
        int n = 4;
        int[] numbers = Arrays.stream(array).sorted().toArray();
        System.out.println(numbers[numbers.length - n]);

    }
}

