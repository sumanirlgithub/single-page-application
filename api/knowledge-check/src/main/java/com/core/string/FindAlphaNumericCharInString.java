package com.core.string;

public class FindAlphaNumericCharInString {

    public static void main(String[] args) {
        String s = "Hello, 0123";
        for (char c : s.toCharArray()) {

            if ((c >= '0' & c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')){
                System.out.println(c +" is alphanumeric");
            } else {
                System.out.println(c +" is not alphanumeric");
            }
        }

    }
}
