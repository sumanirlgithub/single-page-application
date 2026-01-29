package com.core.factorypattern.badcode;

class Solution {
    public static void main(String[] args) {
        FruitStore fruitStore = new FruitStore();

        Apple apple = fruitStore.orderApple();
        System.out.println(apple.getDescription());

        Banana banana = fruitStore.orderBanana();
        System.out.println(banana.getDescription());
    }
}

class Apple {
    String getDescription() {
        return "This is an apple.";
    }
}

class Banana {
    String getDescription() {
        return "This is a banana.";
    }
}

class FruitStore {
    Apple orderApple() {
        return new Apple(); // Direct dependency
    }

    Banana orderBanana() {
        return new Banana(); // Direct dependency
    }
}