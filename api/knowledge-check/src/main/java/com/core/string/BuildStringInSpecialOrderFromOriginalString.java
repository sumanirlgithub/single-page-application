package com.core.string;

/**
 * In this task, you are given a string composed of lowercase English alphabet letters ('a' to 'z'). T
 * he length of the string will range from 1 to 100 characters. Your challenge is to create a new string resulting from a
 * unique order of character selection from the original string.
 *
 * You need to develop a Java method, public String specialOrder(String inputString), which takes inputString as an argument.
 * The resulting string begins with the last character of the inputString, then selects the second-to-last character, continuing in reverse
 * order until you reach the middle character of the string. Then, start with the first character of the inputString, proceed to the second character,
 * and continue in this manner until you reach the middle character.
 *
 * For example, if the inputString is "abcdefg", the method should return "gfedabc".
 */
public class BuildStringInSpecialOrderFromOriginalString {
    public static void main(String[] args) {

        String inputString = "abcdefg";
        //inputString = "abcddcba";
        System.out.println(inputString);
        int len = inputString.length();
        int loopCnt = 0;
        if (len % 2 == 0) {
            loopCnt = len / 2;
        } else {
            loopCnt = (len + 1)  / 2;
            loopCnt = loopCnt - 1;
        }
        System.out.println(loopCnt);
        StringBuilder builder = new StringBuilder();
        StringBuilder result = new StringBuilder();
        for (int i = loopCnt; i < len; i++) {
            builder.append(inputString.charAt(i));
        }
        String s1 = builder.reverse().toString();

        for (int i = 0; i < loopCnt; i++) {
            result.append(inputString.charAt(i));
        }
        System.out.println(result.toString());

        String s2 = result.toString();

        String s = s1 + s2;
        System.out.println(s);


    }
}
