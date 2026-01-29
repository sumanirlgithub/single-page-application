package com.java8.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MapExampleTransformUpperCase {

    public static void main(String[] args) {
        List<String> words = Arrays.asList("apple", "banana", "orange");

        // Using map to transform each element to uppercase
        List<String> upperCasedWords = words.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        // Print the transformed elements
        System.out.println(upperCasedWords);
    }
}