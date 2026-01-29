package com.java8.methodreference;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MethodReferencesExample3 {

    public static void main(String[] args) {

        List<String> bikeBrands = Arrays.asList("Giant", "Scott", "GT");
        List<Bicycle> listOfBicycle = bikeBrands.stream()
                .map(Bicycle::new)
                .toList();
        listOfBicycle.forEach(System.out::println);
    }
}
