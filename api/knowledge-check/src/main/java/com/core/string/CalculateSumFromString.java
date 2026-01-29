package com.myproj;

import java.util.ArrayList;
import java.util.List;

/**
 * 	Can you write a simple calculate function, which takes a string as input and calculates it using addition and subtraction to return an integer value.
 * 	NOTE: eval or a similar function should not be used for this answer.
 *
 * 	Example 1:
 * 	Input: "1 + 1"
 * 	Output: 2
 *
 * 	Example 2:
 * 	Input: "5 - 2"
 * 	Output: 3
 *
 * 	Example 3:
 * 	Input: "5 + 4 + 1"
 * 	Output: 10
 */
public class CalculateSumFromString {

    public static void main(String[] args) {
        String input = "5 + 4 + 1 + 2 - 3";
        int sum = Integer.parseInt(String.valueOf(input.charAt(0)));
        for( int i =0; i < input.length(); i ++ ) {
            if (input.charAt(i) >= '0' && input.charAt(i) <= '9') {
                        if (i < input.length() - 1) {
                            int n1 = Integer.parseInt(String.valueOf(input.charAt(i + 4)));
                            if (input.charAt(i + 2) == '+') {
                                sum = sum + n1;
                            } if (input.charAt(i + 2) == '-') {
                                sum = sum - n1;
                            }
                        }
            }
        }
        System.out.println("result: " + sum);
    }
}
