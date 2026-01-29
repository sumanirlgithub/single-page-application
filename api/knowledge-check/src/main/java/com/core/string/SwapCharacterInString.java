package com.core.string;

/**
 * Humans often make mistakes when they are typing quickly. In some cases, they may press two keys simultaneously,
 * resulting in swapped consecutive characters in the text. Your task is to craft a Java function that helps identify such typos. Specifically,
 * you are asked to construct a function called spotSwaps that behaves as follows:
 *
 * both comprise only lowercase English letters. The function should return the zero-based index of the swap in the source string
 * if exactly one swap of consecutive elements can fix the target string to match the source string; otherwise, the function should return -1.
 * The index of the swap is the index of the left element in the swapped pair.
 *
 * Note:
 *
 * Characters can be swapped at most once.
 * Don't check for swaps at the last position of the string, since there is no character with which to swap.
 * If multiple swaps are needed, or if no swaps are needed, or it's impossible to fix via swaps, return -1.
 */
public class SwapCharacterInString {

    public static void main(String[] args) {

        String source = "hello";
        String target = "hlelo";
        StringBuilder sourceBuilder = new StringBuilder(source);
        StringBuilder targetBuilder = new StringBuilder(target);
        int index = -1;
        for (int i = 0; i < sourceBuilder.length(); i ++) {
            if (sourceBuilder.charAt(i) != targetBuilder.charAt(i)) {
                if (i < sourceBuilder.length() - 1) {
                    char c1 = targetBuilder.charAt(i);
                    char c2 = targetBuilder.charAt(i + 1);
                    targetBuilder.setCharAt(i, c2);
                    targetBuilder.setCharAt(i + 1, c1);
                    if (sourceBuilder.toString().equalsIgnoreCase(targetBuilder.toString())) {
                        index = i;
                    }
                }

            }
        }
        System.out.println(index);
    }
}
