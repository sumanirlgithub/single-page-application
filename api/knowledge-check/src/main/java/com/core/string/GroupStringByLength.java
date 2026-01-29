package com.core.string;

import java.util.*;

/**
 * Group strings by their lengths from list and print the groups
 */
public class GroupStringByLength {

    public static void main(String[] args) {

        List<String> words = Arrays.asList("a", "bb", "ccc", "dd");

        Map<Integer, List<String>> groupedMap = new HashMap<>();

        for (String word : words) {
            int length = word.length();
            List<String> list = groupedMap.getOrDefault(length, new ArrayList<>());
            list.add(word);
            groupedMap.put(length, list);
        }

        System.out.println(groupedMap);

    }
}
