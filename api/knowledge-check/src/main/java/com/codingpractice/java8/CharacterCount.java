package com.codingpractice.java8;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Counting Characters
 * Given a string, write a program to count the occurrences of each character.
 */
public class CharacterCount {

    public static void main(String[] args) {
        String str = "iFollowMilindMehta";
        Map<Character, Long> charCount  =
                str.chars() // Convert string to a stream of char values (int)
                .mapToObj(c -> (char) c) // Convert int values back to chars
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(charCount);
    }
}
