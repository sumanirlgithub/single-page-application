package com.temp;

import java.util.HashMap;

public class CountCharOccurrenceInString {

    public static void main(String[] args) {

        String s = "abca";

        HashMap<Character, Integer> mapCount = new HashMap<>();
        for (char c : s.toCharArray()) {
            mapCount.put(c, mapCount.getOrDefault(c, 0) + 1);
        }
        System.out.println(mapCount);
    }
}
