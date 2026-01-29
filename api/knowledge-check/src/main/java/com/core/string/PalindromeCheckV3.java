package com.core.string;

public class PalindromeCheckV3 {

    public static void main(String[] args) {
        //String s = "madam";
        //String s = "peep";

        String s = "taco cat";
        StringBuilder sentenceWithNoSpace = new StringBuilder();
        for (char c : s.trim().toCharArray()) {
            if (c != ' ') {
                sentenceWithNoSpace.append(c);
            }
        }
        System.out.println(s + " is palindrome : " + checkPalindrome(sentenceWithNoSpace.toString()));
    }

    private static boolean checkPalindrome(String s) {
        boolean isPalindrome = true;
        int len = s.length();

        // len = 5, m = 0, m = 4 -> 5 - 1 -> 5 - 0+1

        int cnt = s.length() / 2;
        if (s.length() % 2 != 0) {
            cnt = cnt + 1;
        }

        for (int i = 0; i < cnt; i++) {
            if (s.charAt(i) != s.charAt(len - (i + 1))) {
                isPalindrome = false;
                break;
            }
        }

        return isPalindrome;
    }
}
