package com.temp;

public class CapitalizeFirstLetter {

    public static void main(String[] args) {
        String s = "hello me";
        String[] words = s.split(" ");
        StringBuilder result = new StringBuilder();
        System.out.println((int) 'a');
        System.out.println((int) 'A');

        for (int i = 0; i < words.length; i++) {
            String word = words[i];

            StringBuilder builder = new StringBuilder();

            for (int j = 0; j < word.length(); j++) {
                if (j == 0) {
                    char c = word.charAt(j);
                    int n = ((int) c) - 32;
                    builder.append((char) n);

                } else {
                    builder.append(word.charAt(j));
                }
            }
            result.append(builder);
            if (i < words.length - 1)
                result.append(" ");
            }

        System.out.println(result);
    }
}
