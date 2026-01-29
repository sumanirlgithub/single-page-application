package com.java8.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FilterProductToSet {
    public static void main(String[] args) {
        Map<String, Integer> salesData = new HashMap<>();
        salesData.put("ProductA", 500);
        salesData.put("ProductB", 1200);
        salesData.put("ProductC", 300);

        // Filter products with sales above 400
        Set filteredData = salesData.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 400)
                .collect(Collectors.toSet());

        System.out.println("Filtered Sales Data:");
        System.out.println(filteredData);


        Set filteredDataOnlyValues = salesData.values()
                .stream()
                .filter(value -> value > 400)
                .collect(Collectors.toSet());

        System.out.println("Filtered Sales Data:");
        System.out.println(filteredDataOnlyValues);


    }
}