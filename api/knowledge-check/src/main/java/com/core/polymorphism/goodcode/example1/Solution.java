package com.core.polymorphism.goodcode.example1;

class Solution {
    public static void main(String[] args) {
        Feedable[] animals = {new Dog(), new Cat(), new Bird()};
        for (Feedable animal : animals) {
            animal.feedAnimal();
        }
    }
}

interface Feedable {
    void feedAnimal();
}

class Dog implements Feedable {
    @Override
    public void feedAnimal() {
        System.out.println("Feeding the dog with dog food.");
    }

}

class Cat implements Feedable {
    @Override
    public void feedAnimal() {
        System.out.println("Feeding the cat with cat food.");
    }
}

class Bird implements Feedable {
    @Override
    public void feedAnimal() {
        System.out.println("Feeding the bird with bird seeds.");
    }
}