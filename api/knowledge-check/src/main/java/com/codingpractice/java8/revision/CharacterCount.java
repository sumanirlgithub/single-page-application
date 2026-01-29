package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CharacterCount {

    public static void main(String[] args) {

        String input = "iamsuman";

        Stream<Character> charStream = input.chars().mapToObj(c -> (char) c);
        Map<Character, Long> charCount = charStream.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        System.out.println(charCount);

    }
}
