package com.core.misc;

public class CheckOddDigitInNumber {

    public static void main(String[] args) {

        int n = 43172;
        //int n = 111;
        //int n = 246;
        int finalResult = 0;
        int result = 1;
        boolean hasOddDigit = false;
        while (n > 0) {
            // use n % 10 to get last digit
            int lastDigit = n % 10;
            //System.out.println(lastDigit);
            if (lastDigit % 2 != 0) {
                result = result * lastDigit;
                hasOddDigit = true;
            } 
            // remove the last digit from n
            n = n / 10;
            //System.out.println(1 / 10);
        }
        if (hasOddDigit) {
            finalResult = result;
        }
        System.out.println(finalResult);

    }
}