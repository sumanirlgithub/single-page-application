package com.temp;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Capitalize First Letter of Each Word *
 *
 */
public class Main {

    public static void main(String[] args) {
        String input = "java developer interview";

        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i< words.length; i++) {
            String word  = words[i];
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j< word.length(); j++) {
                if (j == 0) {
                    if (Character.isLowerCase(word.charAt(j))) {
                        int ascValue = ((int) word.charAt(j) - 32);
                        builder.append((char) ascValue);
                    } else {
                        builder.append(word.charAt(j));
                    }

                } else {
                    builder.append(word.charAt(j));
                }
            } //end for loop
            result.append(builder.toString());
            result.append(" ");

        } // end for loop
        System.out.println(result.toString());

    }
}

