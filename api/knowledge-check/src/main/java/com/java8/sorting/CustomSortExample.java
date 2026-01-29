package com.java8.sorting;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CustomSortExample {

    public static void main(String[] args) {
        List<Product> products = Arrays.asList(new Product("mobile", 100), new Product("laptop", 200));
        List<Product> sortedByPrice = products.stream()
                .sorted(Comparator.comparing(Product::getPrice)
                        .thenComparing(Product::getName))
                .collect(Collectors.toList());

        sortedByPrice.forEach(System.out::println);
    }
}
