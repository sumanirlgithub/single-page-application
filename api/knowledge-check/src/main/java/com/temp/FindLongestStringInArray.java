package com.temp;

import java.util.*;

/**
 * Find the first longest string in a given array.
 */
public class FindLongestStringInArray {

    public static void main(String[] args) {

        String[] input = {"Milind", "Mehta", "follow", "java8", "Microservice1", "Springboot", "Microservice2", "hello"};

        Map<Integer, List<String>> map = new HashMap<>();

        for (String s : Arrays.stream(input).toList()) {
            int length = s.length();
            List<String>  list = map.getOrDefault(length, new ArrayList<>());
            list.add(s);
            map.put(length, list);
        }

        int maxKey = map.keySet().stream()
                .max(Integer::compareTo)
                .orElse(0);
        
        System.out.println(map.get(maxKey)
                .stream().
                findFirst()
                .orElse(""));
    }

}
