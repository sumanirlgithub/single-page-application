package com.core.encapsulation.badcode;
import java.util.ArrayList;
import java.util.List;

class Solution {
    public static void main(String[] args) {
        Library library = new Library();
        library.books.add("Clean Code");
        library.books.add("Design Patterns");
        library.books.add("Refactoring");
        library.books.remove("Design Patterns");

        System.out.println("Library Books:");
        for (String bookTitle : library.books) {
            System.out.println(bookTitle);
        }
    }
}

class Library {
    public List<String> books;

    public Library() {
        books = new ArrayList<>();
    }
}