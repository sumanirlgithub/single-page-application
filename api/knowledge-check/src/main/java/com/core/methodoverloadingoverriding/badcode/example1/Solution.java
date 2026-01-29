package com.core.methodoverloadingoverriding.badcode.example1;

class Solution {
    public static void main(String[] args) {
        Pizza myPizza = new Pizza();
        myPizza.addTopping("Cheese");
        myPizza.addToppings(new String[]{"Cheese", "Pepperoni", "Olives"});
        myPizza.preparePizza();
    }
}

class Food {
    void prepare() {
        System.out.println("Preparing food...");
    }
}

class Pizza extends Food {
    void preparePizza() {
        System.out.println("Preparing pizza...");
    }

    void addTopping(String topping) {
        System.out.println("Adding topping: " + topping);
    }

    void addToppings(String[] toppings) {
        System.out.print("Adding multiple toppings: ");
        for (String topping : toppings) {
            System.out.print(topping + " ");
        }
        System.out.println();
    }
}