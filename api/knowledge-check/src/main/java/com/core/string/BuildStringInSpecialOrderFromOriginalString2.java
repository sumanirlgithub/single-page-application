package com.core.string;

/**
 * In this task, you are given a string s, and your goal is to produce a new string following a specific pattern. You are to take characters in sets of three,
 * reverse the characters in each set, and then place them back into the string in their original positions, preserving the reverse order within each set.
 * If 1 or 2 characters remain at the end (because the length of the string is not divisible by 3), they should be left as they are.
 *
 * The string s contains only lowercase English letters, with its length ranging from 1 to 300, inclusive.
 *
 * For example, if you are given the input 'abcdef', the output should be 'cbafed'. For the input 'abcdefg', your function should provide 'cbafedg'.
 */
public class BuildStringInSpecialOrderFromOriginalString2 {
    public static void main(String[] args) {

        String s = "abcdef";
        //s = "abcdefg";
        //s = "ab";
        int reminder = s.length() % 3;
        System.out.println(s.length() % 3);
        StringBuilder result = new StringBuilder();
        String str = "";
        for (int i = 0; i < s.length() - reminder; i++) {
            StringBuilder builder = new StringBuilder();
            int j = 0;
            while (j < 3) {
                int cnt = i + j;
                if (cnt < s.length()) {
                    builder.append(s.charAt(cnt));
                }
                j++;
            } // end while loop
            str = builder.reverse().toString();
            str = result.append(str).toString();
            i = i + 2;
        } // end for loop

        for (int k = 0; k <reminder; k ++) {
            str  = str + s.charAt(s.length() - reminder + k);
        }

        System.out.println(str);    }
}
