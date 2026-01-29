package com.core.leapyear;

/**
 * Rules to Verify a Leap Year
 *
 * If a year is divisible by 4, it may be a leap year. So, we move to the next check.
 * If the year is divisible by 100, we need one more condition to confirm.
 * If the year is also divisible by 400, then it is a leap year.
 * However, if the year is not divisible by 100 but divisible by 4, it is still a leap year.
 * If none of the above conditions are met, then the year is not a leap year.
 */
class Solution {
    public static void main(String[] args) {
        int year = 2024;
        boolean leap = isLeapYear(year);
        System.out.println(year + " is a leap year: " + leap);
    }

    private static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 == 0 && year % 400 == 0) {
            return true;
        } else if (year % 4 == 0 && year % 100  != 0) {
            return true;
        }
        return false;

    }
}