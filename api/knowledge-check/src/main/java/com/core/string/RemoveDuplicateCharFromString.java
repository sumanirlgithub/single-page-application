package com.core.string;

import java.util.stream.Stream;

public class RemoveDuplicateCharFromString {

    public static void main(String[] args) {
        String str = "hello";

        // convert string to stream of characters
        Stream<Character> streamChars = str.chars()
                .mapToObj(c -> (char) c);
        streamChars = streamChars.distinct();
        streamChars.forEach(System.out::println);

    }
}
