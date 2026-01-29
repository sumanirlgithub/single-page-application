package com.core.factorypattern.goodcode;


class Solution {
    public static void main(String[] args) {
        FruitStore fruitStore = new FruitStore();

        Apple apple = fruitStore.orderApple();
        System.out.println(apple.getDescription());

        Banana banana = fruitStore.orderBanana();
        System.out.println(banana.getDescription());
    }
}

class Apple implements Fruit {

    @Override
    public String getDescription() {
        return "This is an apple.";
    }
}

class Banana implements Fruit {

    @Override
    public String getDescription() {
        return "This is a banana.";
    }
}

interface Fruit {
    String getDescription();
}

class FruitFactory {
    public Fruit createFruit(String type)  {
        if (type.equalsIgnoreCase("apple")) {
            return new Apple();
        } else if (type.equalsIgnoreCase("banana")) {
            return new Banana();
        } else {
            throw new RuntimeException("Unsupported fruit type");
        }
    }

}

class FruitStore {
    FruitFactory factory = new FruitFactory();
    Apple orderApple() {
        return (Apple) factory.createFruit("apple");
    }

    Banana orderBanana() {
        return (Banana) factory.createFruit("banana");
    }
}