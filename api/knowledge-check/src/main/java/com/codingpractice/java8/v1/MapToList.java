package com.codingpractice.java8.v1;

import java.util.*;

/**
 * Convert Map to List, print keys and values separately from Map
 */
public class MapToList {

    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(101, "Suman");
        map.put(102, "Aman");
        System.out.println(map);

        List<Map.Entry<Integer, String>> list = new ArrayList<>(map.entrySet());
        System.out.println(list);

        Set<Integer> keys = map.keySet();
        keys.stream().forEach(System.out::println);
        List<String> listV = new ArrayList<>(map.values());
        System.out.println(listV);
    }
}

