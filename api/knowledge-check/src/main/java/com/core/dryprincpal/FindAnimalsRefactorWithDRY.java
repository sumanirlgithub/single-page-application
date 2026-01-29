package com.core.dryprincpal;

import java.util.ArrayList;
import java.util.List;

public class FindAnimalsRefactorWithDRY {
    public static void main(String[] args) {
        List<Animal> animals = List.of(
                new Animal("Whale", Type.MAMMAL),
                new Animal("Shark", Type.FISH),
                new Animal("Tiger", Type.MAMMAL),
                new Animal("Dolphin", Type.MAMMAL),
                new Animal("Salmon", Type.FISH)
        );

        List<Animal> mammals = new ArrayList<>();
        List<Animal> fish = new ArrayList<>();
        for (Animal animal : animals) {
            mammals = findAnimals(animal, mammals, fish);
        }
        System.out.println("Mammals: " + mammals);
        System.out.println("Fish: " + fish);
    }
    private static List<Animal> findAnimals(Animal animal, List<Animal> mammals, List<Animal> fish) {
        if (animal.type() == Type.MAMMAL) {
            mammals.add(animal);
        } else if (animal.type() == Type.FISH) {
            fish.add(animal);
        }
        return mammals;
    }
}

