package com.core.dryprincpal;

import java.util.ArrayList;
import java.util.List;

public class FindAnimalsOriginal {
    public static void main(String[] args) {
    List<Animal> animals = List.of(
            new Animal("Whale", Type.MAMMAL),
            new Animal("Shark", Type.FISH),
            new Animal("Tiger", Type.MAMMAL),
            new Animal("Dolphin", Type.MAMMAL),
            new Animal("Salmon", Type.FISH)
    );
    List<Animal> mammals = findMammals(animals);
    List<Animal> fish = findFish(animals);
    System.out.println("Mammals: " + mammals);
    System.out.println("Fish: " + fish);
}

    private static List<Animal> findMammals(List<Animal> animals) {
        List<Animal> mammals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.type() == Type.MAMMAL) {
                mammals.add(animal);
            }
        }
        return mammals;
    }

    private static List<Animal> findFish(List<Animal> animals) {
        List<Animal> fish = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.type() == Type.FISH) {
                fish.add(animal);
            }
        }
        return fish;
    }
}



