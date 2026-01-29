package com.codingpractice.java8.v1;

import java.util.Arrays;
import java.util.List;

public class ArrayToListExample2 {
    public static void main(String[] args) {
                String[] array = {"Apple", "Banana", "Cherry"};
                List<String> list = Arrays.asList(array);
                System.out.println("List: " + list);
    }
}

