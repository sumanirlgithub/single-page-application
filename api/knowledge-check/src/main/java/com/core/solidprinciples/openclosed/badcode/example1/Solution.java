package com.core.solidprinciples.openclosed.badcode.example1;

/**
 * In this task, our focus shifts to the Open/Closed Principle, which emphasizes structuring your code so it's easy to add new features without disrupting existing functionality.
 * The given code calculates the pricing for different types of products but lacks flexibility for future expansions. We need to refactor it to allow
 * new product types to be introduced easily while maintaining existing logic.
 *
 * Your task is to redesign the code structure so that it aligns with the Open/Closed Principle. This involves making it easy to add new product types without
 * affecting the current codebase.
 */
class Solution {
    public static void main(String[] args) {
        ProductCalculator calculator = new ProductCalculator();
        double bookPrice = calculator.calculate("Book", 29.99);
        double foodPrice = calculator.calculate("Food", 0.99);

        System.out.println("Price of Book: $" + bookPrice);
        System.out.println("Price of Food: $" + foodPrice);
    }
}

class ProductCalculator {
    public double calculate(String type, double basePrice) {
        if (type.equals("Book")) {
            return calculateBookPrice(basePrice);
        } else if (type.equals("Food")) {
            return calculateFoodPrice(basePrice);
        }
        return basePrice;
    }

    private double calculateBookPrice(double basePrice) {
        // No discount or special pricing for books
        return basePrice;
    }

    private double calculateFoodPrice(double basePrice) {
        // Applying tax for food products
        return basePrice + basePrice * 0.07;
    }
}