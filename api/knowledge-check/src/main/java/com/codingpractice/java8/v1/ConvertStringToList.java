package com.codingpractice.java8.v1;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConvertStringToList {
    public static void main(String[] args) {
        String inputString = "Hello";
        // Convert String to Stream<Character>
        Stream<Character> charStream = inputString.chars()
                .mapToObj(c -> (char) c);
        // collect the characters into a List
        List<Character> charList = inputString.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        System.out.println("List of characters: " + charList);
    }
}
