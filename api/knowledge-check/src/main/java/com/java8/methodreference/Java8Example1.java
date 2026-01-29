package com.java8.methodreference;

import java.util.List;

public class Java8Example1 {

    public static void main(String[] args) {
        List<String> names = List.of("Ram", "Shyam", "Ravi");
        names.stream()
                .filter(name -> name.startsWith("R"))
                .map(String::toUpperCase)
                .forEach(System.out::println);
    }
}
