package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;

public class PrintElementsLength {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("abc", "pq", "xyzp");
        list.stream().map(str -> str.length()).forEach(System.out::println);

    }

}
