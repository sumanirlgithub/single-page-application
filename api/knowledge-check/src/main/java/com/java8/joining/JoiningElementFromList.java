package com.java8.joining;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JoiningElementFromList {
    public static List<String> split(String s, String delimiter) {
        return Arrays.asList(s.split(delimiter));
    }

    // Modify this function to use Java's Stream API for joining strings.
    public static String join(List<String> list, String delimiter) {
        // #TODO: Replace the following loop operations with Stream API string join ensuring it works also for variable number of fields
        StringBuilder joined = new StringBuilder();

        String s = list.stream().collect(Collectors.joining(delimiter));
        return s;
    }

    public static void main(String[] args) {
        String astronautsData = "Buzz Aldrin,1930;Yuri Gagarin,1934;Valentina Tereshkova,1937";
        List<String> astronautsList = split(astronautsData, ";");
        List<String> cleanedAstronauts = new ArrayList<>();

        for (String astronaut : astronautsList) {
            cleanedAstronauts.add(join(split(astronaut, ","), " "));
        }

        for (String cleanedAstronaut : cleanedAstronauts) {
            System.out.println(cleanedAstronaut); // Should output: Buzz Aldrin 1930, Yuri Gagarin 1934, Valentina Tereshkova 1937
        }
    }
}
