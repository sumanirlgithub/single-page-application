package com.codingpractice.java8.v1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConvertMapValuesToSet {

    public static void main(String[] arg) {
        Map<Integer, String> map = new HashMap<>();
        map.put(101, "Suman");
        map.put(102, "Aman");

        Set<String> set = new HashSet<>(map.values());
        System.out.println(set);
    }
}
