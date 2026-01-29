package com.core.polymorphism.badcode.exmple1;

class Solution {
    public static void main(String[] args) {
        Animal[] animals = {new Dog(), new Cat(), new Bird()};
        for (Animal animal : animals) {
            feedAnimal(animal);
        }
    }

    private static void feedAnimal(Animal animal) {
        if (animal instanceof Dog) {
            System.out.println("Feeding the dog with dog food.");
        } else if (animal instanceof Cat) {
            System.out.println("Feeding the cat with cat food.");
        } else if (animal instanceof Bird) {
            System.out.println("Feeding the bird with bird seeds.");
        }
    }
}

class Animal {}

class Dog extends Animal {}

class Cat extends Animal {}

class Bird extends Animal {}