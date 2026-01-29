package com.core.solidprinciples.dependencyinversion.badcode;

/**
 * Dependency Inversion Principle from the SOLID principles!
 *
 * The current code involves a printer system that directly interacts with specific printer implementations, leading to tight coupling. Your task is to
 * refactor the code by introducing an abstraction that allows the printer system to depend not on concrete printer implementations but on an abstraction.
 * This change will make the codebase more flexible and easier to extend or modify by promoting loose coupling.
 *
 * The goal is to implement the Dependency Inversion Principle by refactoring the code to utilize interfaces where necessary, allowing different printer
 * implementations to be swapped out without altering the printer system's code. Let's create a more modular design!
 */
class Solution {
    public static void main(String[] args) {
        PrintManager manager = new PrintManager();
        manager.printUsingInkjet("Inkjet Printing: Hello World!");
        manager.printUsingLaser("Laser Printing: Hello SOLID Principles!");
    }
}

class InkjetPrinter {
    public void print(String document) {
        System.out.println("Inkjet Printer: Printing " + document);
    }
}

class LaserPrinter {
    public void print(String document) {
        System.out.println("Laser Printer: Printing " + document);
    }
}

class PrintManager {
    private InkjetPrinter inkjetPrinter;
    private LaserPrinter laserPrinter;

    public PrintManager() {
        this.inkjetPrinter = new InkjetPrinter();
        this.laserPrinter = new LaserPrinter();
    }

    public void printUsingInkjet(String document) {
        inkjetPrinter.print(document);
    }

    public void printUsingLaser(String document) {
        laserPrinter.print(document);
    }
}