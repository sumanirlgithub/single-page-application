package com.core.inheritancewithcomposition.badcode.example3;

/**
 * Now, let’s focus on applying one of the key clean code principles: composition over inheritance. In this task, we have a piece of code in
 * which inheritance is not the right solution due to a weak "is-a" relationship. Your job is to refactor the code to use composition instead,
 * simplifying the structure and making it more logical and flexible.
 */
class Solution {
    public static void main(String[] args) {
        Hero hero = new Hero();
        hero.attack();

        Archer archer = new Archer();
        archer.attack();
    }
}

class Hero extends Sword {
    public void attack() {
        useSword();
    }
}

class Sword {
    public void useSword() {
        System.out.println("Swinging the sword!");
    }
}

class Archer extends Bow {
    public void attack() {
        useBow();
    }
}

class Bow {
    public void useBow() {
        System.out.println("Shooting an arrow!");
    }
}