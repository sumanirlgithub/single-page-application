package com.codingpractice.java8.revision;

public class LambdaAddition {

    public static void main(String[] args) {
        SumCalculator sumCalculator = (a, b) -> a + b;
        int result = sumCalculator.sum(3, 5);
        System.out.println(result);
    }

}
