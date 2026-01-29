package com.core.solidprinciples.openclosed.goodcode.example1;

class Solution {
    public static void main(String[] args) {
        ProductCalculator calculator = new ProductCalculator();
        PriceCalculator book = new Book();
        PriceCalculator food = new Food();
        double bookPrice = calculator.calculate(book, 29.99);
        double foodPrice = calculator.calculate(food, 0.99);

        System.out.println("Price of Book: $" + bookPrice);
        System.out.println("Price of Food: $" + foodPrice);
    }
}

interface PriceCalculator {
    double calculatePrice(double basePrice);

}

class Book implements PriceCalculator {
    @Override
    public double calculatePrice(double basePrice) {
        // No discount or special pricing for books
        return basePrice;
    }
}

class Food implements PriceCalculator {
    @Override
    public double calculatePrice(double basePrice) {
        // Applying tax for food products
        return basePrice + basePrice * 0.07;
    }
}

class ProductCalculator {
    public double calculate(PriceCalculator priceCalculator, double basePrice) {
        return priceCalculator.calculatePrice(basePrice);
    }
}