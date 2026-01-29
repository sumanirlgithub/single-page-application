package com.codingpractice.java8.v1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConvertSetToList {

    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("Suman");
        set.add("Aman");
        List<String> list = new ArrayList<>(set.stream().toList());
        System.out.println(list);

    }
}
