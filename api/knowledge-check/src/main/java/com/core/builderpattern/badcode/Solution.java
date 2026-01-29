package com.core.builderpattern.badcode;

class Solution {
    public static void main(String[] args) {
        Order order = new Order("Laptop", 999.99, 2, "123 Main St, Anytown", null);
        System.out.println(order);
    }
}

class Order {
    private String productName;
    private double price;
    private int quantity;
    private String deliveryAddress;
    private String note;

    public Order(String productName, double price, int quantity, String deliveryAddress, String note) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.deliveryAddress = deliveryAddress;
        this.note = note;
    }

    @Override
    public String toString() {
        return "Order[productName=" + productName + ", price=" + price +
                ", quantity=" + quantity + ", deliveryAddress=" + deliveryAddress +
                ", note=" + note + "]";
    }
}