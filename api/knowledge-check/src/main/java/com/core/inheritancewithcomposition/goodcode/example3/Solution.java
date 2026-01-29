package com.core.inheritancewithcomposition.goodcode.example3;

class Solution {
    public static void main(String[] args) {
        Sword sword = new Sword();
        Hero hero = new Hero(sword);
        hero.attack();

        Bow bow = new Bow();
        Archer archer = new Archer(bow);
        archer.attack();
    }
}

class Hero {
    private Sword sword;
    public Hero(Sword sword) {
        this.sword = sword;
    }
    public void attack() {
        this.sword.useSword();
    }

    public Sword getSword() {
        return this.sword;
    }
}

class Sword {
    public void useSword() {
        System.out.println("Swinging the sword!");
    }
}

class Archer {
    private Bow bow;
    public Archer(Bow bow) {
        this.bow = bow;
    }
    public void attack() {
        this.bow.useBow();

    }

    public Bow getBow() {
        return this.bow;
    }
}

class Bow {
    public void useBow() {
        System.out.println("Shooting an arrow!");
    }
}