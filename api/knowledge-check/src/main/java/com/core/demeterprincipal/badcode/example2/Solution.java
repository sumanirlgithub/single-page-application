package com.core.demeterprincipal.badcode.example2;

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
        System.out.println(customer.getName() + " received order: " + quantity + "x " + order.item);
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
}

class Order {
    String item;
    int quantity;

    public Order(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}