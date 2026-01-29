package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;

public class SortNameInAlphabeticalOrder {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Suman", "Aman", "Ronak", "Abbey");
        names.stream().sorted().forEach(System.out::println);
    }
}
