package com.core.demeterprincipal.goodcode.example1;

class Solution {
    public static void main(String[] args) {
        User user = new User("John Doe");
        Library library = new Library();
        library.checkOutBook(user, "Java Programming");
    }
}

class Library {
    Book book;
    public void checkOutBook(User user, String bookTitle) {
        Book book = new Book(bookTitle);
        user.borrowBook(book);
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

    public void borrowBook(Book book) {
        System.out.println(this.getName() + " borrowed " + book.getTitle());
    }
}

class Book {
    String title;

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}