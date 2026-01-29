package com.core.methodoverloadingoverriding.goodcode.example1;

class Solution {
    public static void main(String[] args) {
        Pizza myPizza = new Pizza();
        myPizza.addTopping("Cheese");
        myPizza.addTopping(new String[]{"Cheese", "Pepperoni", "Olives"});
        myPizza.prepare();
    }
}

class Food {
    void prepare() {
        System.out.println("Preparing food...");
    }
}

class Pizza extends Food {
    @Override
    void prepare() {
        System.out.println("Preparing pizza...");
    }

    void addTopping(String... topping) {
        for (String s : topping) {
            System.out.println("Adding topping: " + s.toString());
        }

    }

}