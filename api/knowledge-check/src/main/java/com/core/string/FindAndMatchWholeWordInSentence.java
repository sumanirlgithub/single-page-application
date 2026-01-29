package com.core.string;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.*;

/**
 * write a java program for whole word match, if is found then reverse the word in the sentence.
 */
public class FindAndMatchWholeWordInSentence {

    public static void main(String[] args) {

        List<String> sentences = Arrays.asList("this is a simple example.", "the name is bond. james bond.", "remove every single e");
        List<String> words = Arrays.asList("simple", "bond", "e");
        List<String> result = new ArrayList<>();

        for (int i = 0; i < sentences.size(); i++) {
            String sentence = sentences.get(i);
            String word = words.get(i);

            // Use regex to find whole words, case-insensitive
            String patternString = "\\b" + Pattern.quote(word) + "\\b";
            Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(sentence);

            StringBuffer sb = new StringBuffer();
            while (matcher.find()) {
                String matchedWord = matcher.group();
                String reversed = new StringBuilder(word).reverse().toString();

                // Preserve initial capitalization
                if (Character.isUpperCase(matchedWord.charAt(0))) {
                    reversed = reversed.substring(0, 1).toUpperCase() + reversed.substring(1).toLowerCase();
                } else {
                    reversed = reversed.toLowerCase();
                }

                matcher.appendReplacement(sb, Matcher.quoteReplacement(reversed));
            }
            matcher.appendTail(sb);

            result.add(sb.toString());
        }

        System.out.println(result);
    }
}
