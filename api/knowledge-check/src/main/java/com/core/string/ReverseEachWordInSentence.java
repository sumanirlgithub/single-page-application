package com.core.string;

public class ReverseEachWordInSentence {

    public static void main(String[] args) {

        String sentence = "hello me";
        String[] words = sentence.split(" ");

        StringBuilder result = new StringBuilder();

        int cnt = 0;
        for (String word : words) {
            cnt++;
            StringBuilder builder = new StringBuilder();
            for (int j = word.length() - 1; j >= 0; j--) {
                builder.append(word.charAt(j));
            }
            result.append(builder.toString());
            if (cnt < words.length) {
                result.append(" ");
            }
        }
        System.out.println(result.toString());

    }
}
