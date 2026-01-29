package com.core.string;

/**
 * Find first non-repeating character
 */
public class FindFirstNonRepeatingCharacter {

    public static void main(String[] args) {
        String str = "helohe";
        for (int i = 0; i < str.length(); i++) {
            int cnt = 0;
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(i) == str.charAt(j)) {
                    cnt++;
                }
            } // close 2nd for loop
            if (cnt == 1) {
                System.out.println(str.charAt(i));
                break;
            }
        } // close first for loop
    }
}
