package com.java8.methodreference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodReferencesExample1 {

    public static void main(String[] args) {

        List<String> names = Arrays.asList("tom", "Sam", "barry");
        //convert the names to upper case using static method references
        List list = names.stream().map(String::toUpperCase).collect(Collectors.toList());
        list.forEach(name -> System.out.println(name));
    }
}
