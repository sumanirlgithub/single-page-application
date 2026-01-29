package com.core.string;

/**
 * Remove all  spaces from sentence
 */
public class RemoveSpacesV1 {

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
