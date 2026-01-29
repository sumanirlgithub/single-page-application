package com.core.string;

/**
 * Count occurrence of each letter in a word
 */
public class CountCharOccurrenceInString2 {
    public static void main(String[] args) {
        String input = "hello";
        int[] frequency = new int[256]; // Covers extended ASCII
        for (char ch : input.toCharArray()) {
            int index = (int) ch;
            int currentCount = frequency[index];
            frequency[index] = currentCount + 1;
        }
        for (int i = 0 ; i < frequency.length; i++) {
            if (frequency[i] > 0) {

                System.out.println("'" + (char) i + "' : " + frequency[i]);
            }
        } // close for loop

    }
}
