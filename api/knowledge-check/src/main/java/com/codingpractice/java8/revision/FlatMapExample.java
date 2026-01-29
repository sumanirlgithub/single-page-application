package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlatMapExample {

    public static void main(String[] args) {

        List<List<String>> listOfList = Arrays.asList(
                Arrays.asList("abc", "mnp"),
                Arrays.asList("klm", "pqr")
        );

        List<String> list = listOfList.stream().flatMap(Collection::stream).collect(Collectors.toList());
       System.out.println(list);
    }
}
