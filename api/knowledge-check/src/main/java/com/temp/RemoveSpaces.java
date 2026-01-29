package com.temp;

import java.util.Arrays;

public class RemoveSpaces {

    public static void main(String[] args) {
        String s = "   This   is    a   Java   program   ";

        StringBuilder builder = new StringBuilder();
        String[] words = s.split(" ");
        for (String word : words) {
            builder.append(word);
        }

        System.out.println(builder.toString());

    }
}
