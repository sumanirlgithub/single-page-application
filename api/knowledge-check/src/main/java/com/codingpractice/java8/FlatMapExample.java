package com.codingpractice.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Using flatMap, print characters from lists where each element is a list itself.
 */
public class FlatMapExample {

    public static void main(String[] args) {
        List<List<String>> listOfList = Arrays.asList(
                Arrays.asList("abc", "klm", "mnp"), Arrays.asList("edf", "xyz", "pqr"));

        //listOfList.stream().flatMap(list -> list.stream()).forEach(System.out::println);
        //listOfList.stream().flatMap(List::stream).forEach(System.out::println);
        List<String> newList = listOfList.stream().flatMap(List::stream).toList();
        System.out.println(newList);
    }
}
