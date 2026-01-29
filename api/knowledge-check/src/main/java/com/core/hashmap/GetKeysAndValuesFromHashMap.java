package com.core.hashmap;

import java.util.HashMap;
import java.util.Map;

/**
 * Print all keys and values from HashMap
 */
public class GetKeysAndValuesFromHashMap {

    public static void main(String[] args) {
        Map<Character, Integer> countMap = new HashMap<>();
        countMap.put('a', 5);
        countMap.put('k', 3);
        countMap.put('m', 2);

        for (Character character : countMap.keySet()) {
            char c = (char) character;
            System.out.println(c + " -> " + countMap.get(c));
        }
    }
}
