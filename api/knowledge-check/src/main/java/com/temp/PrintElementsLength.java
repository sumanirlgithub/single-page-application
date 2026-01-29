package com.temp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PrintElementsLength {

    public static void main(String[] args) {

        List<String> list = Arrays.asList("abc", "ab", "xyzp");

        List<Integer> newList = list.stream()
                .map(String::length)
                .toList();

        System.out.println(newList);
    }
}
