package com.inventory;

/**
 * Strategy pattern implementation for calculating different types of discounts.
 * TODO: Implement the Strategy pattern with multiple discount algorithms.
 *
 * Requirements:
 * - Student discount: 10% off books only
 * - Bulk discount: 15% off when buying 5+ items
 * - No discount option
 * - Return discount amount and description
 */
public class DiscountCalculator {

    /**
     * Inner class to hold discount calculation results.
     * TODO: Implement this class with discount amount and description.
     */
    public static class DiscountResult {
        // TODO: Implement DiscountResult class
         private double discountAmount;
         private String description;

        // TODO: Add constructor
         public DiscountResult(double discountAmount, String description) {
             this.discountAmount = discountAmount;
             this.description = description;
         }

        // TODO: Add getter methods
         public double getDiscountAmount() {return this.discountAmount; }
         public String getDescription() {return this.description; }
    }

    /**
     * TODO: Implement strategy method for calculating discounts
     * @param product - the product being purchased
     * @param quantity - quantity being purchased
     * @param discountType - type of discount to apply (STUDENT, BULK, NONE)
     * @return DiscountResult with amount and description
     */
    public static DiscountResult calculateDiscount(Product product, int quantity, String discountType) {
        // TODO: Implement discount calculation logic

        // Initialize variables
         double discountAmount = 0.0;
         String description = "No discount applied";

        // TODO: Implement switch statement or if-else logic for discount types
        switch (discountType.toUpperCase()) {
            case "STUDENT":
            //   - Check if product type is "BOOK"
            //   - Apply 10% discount if applicable
            //   - Set appropriate description
                if (product.getType().equals("BOOK")) {
                    discountAmount = product.getPrice() * quantity * .10;
                    description = "10% discount on books for Student";
                } else {
                    description = "Student discount only apply to books";
                }
                break;

             case "BULK":
            //   - Check if quantity >= 5
            //   - Apply 15% discount if applicable
            //   - Set appropriate description
                 if (quantity >= 5) {
                     discountAmount = product.getPrice() * quantity * .15;
                     description = "15% discount applied for bulk order";
                 } else {
                     description = "15% discount bulk order require at least 5 items";
                 }

             case "NONE":
             default:
            //   - No discount applied
                 break;

        }

        // TODO: Return new DiscountResult with calculated values
        return new DiscountResult(discountAmount, description);
    }

    // TODO: Optional - Add helper methods
    // private static boolean isEligibleForStudentDiscount(Product product) { }
    // private static boolean isEligibleForBulkDiscount(int quantity) { }
}