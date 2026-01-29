package com.core.string;

/**
 * Remove all  spaces from sentence
 */
public class RemoveSpaces {

    public static void main(String[] args) {

        String sentence = "   This   is    a   Java   program   ";
        System.out.println(sentence.trim()); // trim method remove leading and trailing spaces

        StringBuilder sentenceWithNoSpace = new StringBuilder();

        for (char c : sentence.trim().toCharArray()) {
            if (c != ' ') {
                sentenceWithNoSpace.append(c);
            }
        }
        System.out.println(sentenceWithNoSpace);
    }
}
