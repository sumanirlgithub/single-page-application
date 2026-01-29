package com.codingpractice.java8;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Finding Duplicates
 * Given a string, find characters that appear more than once.
 */
public class FindDuplicates {

    public static void main(String[] args) {
        String input = "iamsuman";
        Stream<Character> streamChars = input.chars().mapToObj(c -> (char) c);
        Map<Character, Long> map1 = streamChars.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Stream<Map.Entry<Character, Long>> newStream = map1.entrySet().stream().filter( e -> e.getValue() > 1);
        Map<Character, Long> map2 = newStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println(map2);
        System.out.println(map2.keySet());
    }
}
