package com.core.string;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * Group strings by their lengths and print the groups using Java 8
 */
public class GroupStringsByLengthUsingJava8 {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("a", "bb", "ccc", "dd");
        Map<Integer, List<String>> grouped = words.stream()
                .collect(Collectors.groupingBy(String::length));
        System.out.println(grouped); // Output: {1=[a], 2=[bb, dd], 3=[ccc]}
    }
}