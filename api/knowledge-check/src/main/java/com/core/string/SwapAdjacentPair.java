package com.core.string;


/**
 * You are given a string s. Your task is to write a function that returns a string in which every pair of adjacent characters in the original
 * string is swapped. If the string has an odd length, leave the last character as it is.
 *
 * It is not allowed to use Java built-in functions like reverse() or String.join() in this task, you should implement the solution without using them.
 *
 * For example, if you are given the string "abcdef", your function should return "badcfe". If the string is "hello", your function should return "ehllo".
 */
public class SwapAdjacentPair {

    public static void  main(String[] args) {
        String s = "abcdef";
        //String s = "hello";
        int len = s.length();
        int loopCnt = 0;
        if (len % 2 == 0) {
            loopCnt = len / 2;
        } else {
            loopCnt = (len - 1) / 2 ;
        }
        StringBuilder builder = new StringBuilder();
        int j = 0;
        for (int i = 0; i < loopCnt; i ++) {

            if (j < len -1) {
                builder.append(s.charAt(j + 1));
                builder.append(s.charAt(j));
                j = j + 2;
            }
        }

        if (len % 2 != 0) {
            builder.append(s.charAt(len - 1));
        }

        System.out.println(builder.toString());


    }
}
