package com.temp;

public class PalindromeCheck {

    public static void main(String[] args) {

        String s = "madam";
        boolean palindrome = true;
        int loopCounter = 0;
        if (s.length() % 2 == 0) {
            loopCounter = (s.length() + 1) / 2;
        } else {
            loopCounter = (s.length() - 1) / 2;
        }

        for (int i = 0; i< loopCounter; i++ ) {

            if (s.charAt(i) != s.charAt(s.length() - ( i+ 1))) {
                palindrome = false;
                break;
            }

        }


        System.out.println(palindrome);

    }
}
