package com.core.string;

public class PalindromeCheck {

    public static void main(String[] args) {

        //String inputString = "madam";
        String inputString = "Taco cat";
        boolean isPalindrome = true;
        // char chU = 'A';
        // char ch = 'Z';
        // char chL = 'a';
        // System.out.println((int)chU);
        // System.out.println((int)ch);
        // System.out.println((int)chL);

        int leftIndex = 0;
        int rightIndex = inputString.length() - 1;

        while (leftIndex < rightIndex) {
            char ch1 = inputString.charAt(leftIndex);
            char ch2 = inputString.charAt(rightIndex);

            if (!Character.isLetter(ch1)) {
                leftIndex++;
                continue;
            } else {
                ch1 = inputString.charAt(leftIndex);
                int asciValueCh1 = (int) ch1;
                if (asciValueCh1 >= 65 && asciValueCh1 <= 90) {
                    ch1 = (char) (asciValueCh1 + 32); // convert to lower case
                }
            }

            if (!Character.isLetter(ch2)) {
                rightIndex--;
                continue;
            } else {
                ch2 = inputString.charAt(rightIndex);
                int asciValueCh2 = (int) ch2;
                if (asciValueCh2 >= 65 && asciValueCh2 <= 90) {
                    ch2 = (char) (asciValueCh2 + 32); // convert to lower case
                }

            }

            if (ch1 != ch2) {
                isPalindrome = false;
                break;
            }  else  {
                leftIndex++;
                rightIndex--;
            }

        } // end while loop

        System.out.print(isPalindrome);

    }


}
