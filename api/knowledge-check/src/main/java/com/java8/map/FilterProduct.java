package com.java8.map;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FilterProduct {
    public static void main(String[] args) {
        Map<String, Integer> salesData = new HashMap<>();
        salesData.put("ProductA", 500);
        salesData.put("ProductB", 1200);
        salesData.put("ProductC", 300);

        // Filter products with sales above 400
        Map<String, Integer> filteredData = salesData.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 400)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        System.out.println("Filtered Sales Data:");
        System.out.println(filteredData);
    }
}