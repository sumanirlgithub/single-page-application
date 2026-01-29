package com.codingpractice.java8.revision;

import java.util.Arrays;
import java.util.List;

public class CountElements {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("a", "b", "c", "h");
        long count = list.stream().count();
        System.out.println(count);
    }
}
