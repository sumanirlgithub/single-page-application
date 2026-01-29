package com.core.sorting;

import java.util.*;

public class SortHashMapByValue {

    public static void main(String[] args) {
                Map<String, Integer> map = new HashMap<>();
                map.put("Apple", 3);
                map.put("Banana", 1);
                map.put("Kiwi", 4);
                map.put("Cherry", 2);

                List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
                Comparator<Map.Entry<String, Integer>> valueComparator = Map.Entry.comparingByValue();
                list.sort(valueComparator.reversed());
                for (Map.Entry<String, Integer> entry : list) {
                    System.out.println(entry.getKey() + " -> " + entry.getValue());
                }
            }



}
