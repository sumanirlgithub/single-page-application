package com.core.solidprinciples.dependencyinversion.goodcode;

class Solution {
    public static void main(String[] args) {
        Printable injectPrint = new InkjetPrinter();
        Printable laserPrint = new LaserPrinter();
        PrintManager manager = new PrintManager();

        manager.print(injectPrint, "Inkjet Printing: Hello World!");
        manager.print(laserPrint, "Laser Printing: Hello SOLID Principles!");
    }
}

interface Printable {
    void print(String document);
}


class InkjetPrinter implements Printable {
    @Override
    public void print(String document) {
        System.out.println("Inkjet Printer: Printing " + document);
    }
}

class LaserPrinter implements Printable {
    @Override
    public void print(String document) {
        System.out.println("Laser Printer: Printing " + document);
    }
}

class PrintManager {
    public void print(Printable printable, String document) {
        printable.print(document);
    }

}