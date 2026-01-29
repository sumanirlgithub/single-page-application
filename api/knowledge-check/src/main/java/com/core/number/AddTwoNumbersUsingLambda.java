package com.core.number;

/**
 * Add two integers using a lambda expression - reference method
 */
public class AddTwoNumbersUsingLambda {

    public static void main(String[] args) {

        /**
         * Because your lambda: (a, b) -> a + b
         * exactly matches an existing method: Integer.sum(int a, int b)
         * You can use method reference technique(Integer::sum) what which a short form of lambda
         */
        //Calculator calculator    = (a, b) -> a + b;
        Calculator calculator    = Integer::sum;
        int sum = calculator.calculateSum(3, 4);
        System.out.println(sum);
    }

    @FunctionalInterface
    interface Calculator {
        int calculateSum(int a, int b);
    }
}
