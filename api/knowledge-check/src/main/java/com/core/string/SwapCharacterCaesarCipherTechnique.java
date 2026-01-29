package com.core.string;

public class SwapCharacterCaesarCipherTechnique {
    // Simple text encryption using Caesar Cipher technique
    // The Caesar Cipher for `shift = 3` cyclically shifts every letter of the word by 3 positions:
    // a -> d, b -> e, c -> f, ..., x -> a, y -> b, z -> c

    // Implement the encryption logic by shifting each alphabet character
    public static String encryptText(String text) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {  // check if the character is a letter
                int shift = 3;
                // TODO: Use correct calculations to shift the character within the alphabet
                if (Character.isUpperCase(c)) {
                    String s = String.valueOf((char) ((c - 'A' + shift)%26 + 'A'));
                    encrypted.append(s);

                } else if (Character.isLowerCase(c)) {
                    String s = String.valueOf((char) ((c - 'a' + shift)%26 + 'a'));
                    encrypted.append(s);

                }

                // Hint: 'A' = 65, 'a' = 97
                // Hint 2: You can use the modulo (%) operator to wrap around the alphabet
            } else {
                encrypted.append(c);  // keep non-letter characters unchanged
            }
        }
        return encrypted.toString();
    }

    // Example text to encrypt
    public static void main(String[] args) {
        String originalText = "Hello, Java!";
        // The encrypted_text function call and println statement should remain the same as the solution
        String encryptedText = encryptText(originalText);
        System.out.println(encryptedText);  // Correct output after TODO should be 'Khoor, Mdyd!'
    }
}