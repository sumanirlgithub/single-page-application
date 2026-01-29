package com.core.string;

/**
 * Reverse each word in the sentence.
 */
public class ReverseWords {

    public static void main(String[] args) {
        String words = "Hello Suman";
        String[] splitWords = words.split(" ");
        StringBuilder reverseWords = new StringBuilder();
        for (int i = 0; i < splitWords.length; i++) {
            StringBuilder builder = new StringBuilder(splitWords[i]);
            reverseWords.append(builder.reverse());
            reverseWords.append(" ");
        }
        System.out.println(reverseWords.toString()); // this output is as expected.

    }
}
