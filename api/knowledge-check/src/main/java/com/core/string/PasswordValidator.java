package com.core.string;

/**
 * Write a Java function to validate whether a given password meets the following criteria:
 * Must be at least 8 characters long
 * Must contain at least one uppercase letter
 * Must contain at least one lowercase letter
 * Must contain at least one digit
 * Must contain at least one special character (!@#$%^&*()-+)
 * Must not contain spaces
 */
public class PasswordValidator {

    public static void main(String[] args) {
        String password = "Password123!";
        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial  = false;
        boolean hasWhiteSpace  = false;

        String specialChars = "!@#$%^&*()-+";

        if (password == null) {
            throw new RuntimeException("Password can't be null");
        }

        if (password.length() < 8) {
            throw new RuntimeException("Password can't be null");
        }

        for(char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpper = true;
            } else if(Character.isLowerCase(c)) {
                hasLower = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialChars.indexOf(c) != -1) {
                hasSpecial = true;
            } else if (Character.isWhitespace(c)) {
                hasWhiteSpace = true;
            }

        } // end for loop

        if (hasUpper && hasLower & hasDigit && hasSpecial && !hasWhiteSpace) {
            System.out.println("Password is valid");
        } else {
            System.out.println("Password is invalid");
        }

    }

}
