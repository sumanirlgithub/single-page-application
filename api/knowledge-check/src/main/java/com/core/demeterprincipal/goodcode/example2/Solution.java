package com.core.demeterprincipal.goodcode.example2;

class Solution {
    public static void main(String[] args) {
        Customer customer = new Customer("Alice");
        DeliveryService deliveryService = new DeliveryService();
        deliveryService.processOrder(customer, "Pizza", 2);
    }
}

class DeliveryService {
    public void processOrder(Customer customer, String item, int quantity) {
        Order order = new Order(item, quantity);
        customer.printMessage(order);
    }
}

class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void printMessage(Order order) {
        System.out.println(this.getName() + " received order: " + order.getQuantity() + "x " + order.getItem());
    }
}

class Order {
    private String item;
    private int quantity;

    public Order(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public String getItem() {
        return this.item;
    }

    public int getQuantity() {
        return this.quantity;
    }

}