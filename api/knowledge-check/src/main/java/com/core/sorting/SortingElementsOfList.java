    package com.core.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortingElementsOfList {

    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();
        fruits.add("Apple");
        fruits.add("Orange");
        fruits.add("Banana");
        fruits.add("Grape");

        Collections.sort(fruits);
        // Print the sorted list - it will print fruits in ascending order.
        System.out.println(fruits);
    }

}
