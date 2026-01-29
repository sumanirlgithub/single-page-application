package com.core.encapsulation.goodcode;

import java.util.ArrayList;
import java.util.List;

class Solution {
    public static void main(String[] args) {
        Library library = new Library();
        library.addBook("Clean Code");
        library.addBook("Design Patterns");
        library.addBook("Refactoring");

        library.removeBook("Design Patterns");

        System.out.println("Library Books:");
        for (String bookTitle : library.getBooks()) {
            System.out.println(bookTitle);
        }
    }
}

class Library {
    private List<String> books;

    public Library() {
        books = new ArrayList<>();
    }

    public List<String> getBooks() {
        List<String> books = new ArrayList<>(this.books);
        return books;
    }
    public void removeBook(String tile) {
        this.books.remove(tile);
    }

    public void addBook(String title) {
        if (this.books.contains(title)) {
            throw new RuntimeException("duplicate book can not be added");
        }
        this.books.add(title);
    }

}