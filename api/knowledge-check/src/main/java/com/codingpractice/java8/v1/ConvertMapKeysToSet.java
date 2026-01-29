package com.codingpractice.java8.v1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ConvertMapKeysToSet {

    public static void main(String[] arg) {
        Map<Integer, String> map = new HashMap<>();
        map.put(101, "Suman");
        map.put(102, "Aman");

        Set<Integer> set = map.keySet();
        System.out.println(set);
    }
}
