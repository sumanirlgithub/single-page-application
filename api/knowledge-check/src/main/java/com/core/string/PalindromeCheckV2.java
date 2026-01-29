package com.core.string;

/**
 * A palindrome is a word, phrase, number, or other sequence of characters which reads the same backward or forward
 */
public class PalindromeCheckV2 {

    public static void main(String[] args) {

        //String A = "madam";
        String A = "peep";
        int len = A.length();

        int loopIndx = len / 2;

        if (len % 2 != 0) {
            loopIndx = (len / 2 + 1);
        }

        String result =  "Yes";

        for (int i = 0; i < loopIndx; i++) {
            if (A.charAt(i) != A.charAt(len - (i + 1))) {
                result = "No";
                break;
            }
        }

        System.out.println(result);

    }

}
