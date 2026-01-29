package com.core.builderpattern.goodcode;



class Solution {
    public static void main(String[] args) {
        Order.Builder builder = new Order.Builder("Laptop", 999.99);
        Order order = builder.quantity(2).deliveryAddress("123 Main St, Anytown").build();
        System.out.println(order);
    }
}

class Order {
    private String productName;
    private double price;
    private int quantity;
    private String deliveryAddress;
    private String note;

    private Order(String productName, double price, int quantity, String deliveryAddress, String note) {
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

    public static class Builder {
        private String productName;
        private double price;
        private int quantity;
        private String deliveryAddress;
        private String note;

        public Builder(String productName, double price) {
            this.productName = productName;
            this.price = price;
        }
        public Builder quantity(int quantity) {
            this.quantity = quantity;
            return this;
        }

        public Builder deliveryAddress(String deliveryAddress) {
            this.deliveryAddress = deliveryAddress;
            return this;
        }

        public Builder note(String note) {
            this.note = note;
            return this;
        }
        public Order build() {
            return new Order(productName, price, quantity, deliveryAddress, note);
        }
    }
}