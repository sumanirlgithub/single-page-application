package com.core.string;

/**
 * Capitalize First Letter of Each WordCapitalizeFirstLetter
 */
public class CapitalizeFirstLetter {

    public static void main(String[] args) {

        String s = "hello me";

        StringBuilder builder = new StringBuilder();

        String[] words = s.split(" ");
        for (String word : words) {
            int i = word.charAt(0);
            builder.append((char) (i - 32));
            for (int j = 1; j < word.length(); j++) {
                builder.append(word.charAt(j));
            }
            builder.append(" ");
        }
        System.out.println(builder.toString());
    }
}
