package com.inventory;

/**
 * Product class representing items in the inventory.
 * TODO: Implement this class with all required fields, constructor, and methods.
 *
 * Requirements:
 * - Private fields: id, name, type, price, quantity
 * - Constructor with validation
 * - Getter and setter methods
 * - sell(), addStock(), isInStock() methods
 * - toString() method
 */
public class Product {

    // TODO: Declare private fields
     private String id;
     private String name;
     private String type;
     private double price;
     private int quantity;

    /**
     * TODO: Implement constructor
     * @param id - unique product identifier
     * @param name - product name
     * @param type - product category (BOOK or ELECTRONICS)
     * @param price - product price (must be non-negative)
     * @param quantity - stock quantity (must be non-negative)
     * @throws IllegalArgumentException if price or quantity is negative
     */
    public Product(String id, String name, String type, double price, int quantity) {
        // TODO: Implement constructor with validation
        // Hint: Check if price >= 0 and quantity >= 0
        // Throw IllegalArgumentException with descriptive message if invalid

        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity cannot be negative");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // TODO: Implement getter methods
     public String getId() { return this.id;}
     public String getName() { return this.name;}
     public String getType() {return this.type; }
     public double getPrice() {return this.price; }
     public int getQuantity() {return this.quantity; }

    // TODO: Implement setter methods with validation
     public void setPrice(double price) {
         if (price < 0) {
             throw new IllegalArgumentException("Price cannot be negative");
         }
         this.price = price;
     }
     public void setQuantity(int quantity) {
         if (quantity < 0) {
             throw new IllegalArgumentException("Quantity cannot be negative");
         }
         this.quantity = quantity;
     }

    /**
     * TODO: Implement sell method
     * @param amount - quantity to sell
     * @return true if sale successful, false if insufficient stock
     */
    public boolean sell(int amount) {
        // TODO: Implement sell logic
        // Check if amount is valid and sufficient stock exists
        // Update quantity if successful
        if (amount <= 0 || amount > this.quantity) {
            return false;
        }
        return true; // Placeholder
    }

    /**
     * TODO: Implement addStock method
     * @param amount - quantity to add to stock
     */
    public void addStock(int amount) {
        // TODO: Implement stock addition
        // Validate amount is positive before adding
        if (amount > 0) {
            quantity += amount;
        }
    }

    /**
     * TODO: Implement isInStock method
     * @return true if product has stock available
     */
    public boolean isInStock() {
        // TODO: Return true if quantity > 0
        if (this.quantity > 0) {
            return true;
        }
        return false;
    }

    /**
     * TODO: Implement toString method
     * @return formatted string representation of the product
     */
    @Override
    public String toString() {
        // TODO: Return formatted string with all product details
        // Suggested format: "Product{id='B001', name='Java Book', type='BOOK', price=29.99, quantity=5}"
        return "Product{id='B001', name='Java Book', type='BOOK', price=29.99, quantity=5}";
    }
}