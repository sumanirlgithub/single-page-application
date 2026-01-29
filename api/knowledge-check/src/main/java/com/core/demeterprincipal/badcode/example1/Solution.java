package com.core.demeterprincipal.badcode.example1;

class Solution {
    public static void main(String[] args) {
        User user = new User("John Doe");
        Library library = new Library();
        library.checkOutBook(user, "Java Programming");
    }
}

class Library {
    public void checkOutBook(User user, String bookTitle) {
        Book book = new Book(bookTitle);
        System.out.println(user.getName() + " borrowed " + book.title);
    }
}

class User {
    private String name;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Book {
    String title;

    public Book(String title) {
        this.title = title;
    }
}