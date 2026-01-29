package com.core.string;

/**
 * Reverse each word in the sentence.
 */
public class ReverseWordsV1 {

    public static void main(String[] args) {
        String s = "hello me";
        StringBuilder result = new StringBuilder();

        String[] words = s.split(" ");
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            StringBuilder builder = new StringBuilder();
            for (int j = word.length() -1 ; j >= 0; j-- ) {
                builder.append(word.charAt(j));
            }
            result.append(builder.toString());
            if (i < words.length - 1) {
                result.append( " ");
            }

        }
        System.out.println(result.toString());
    }
}
