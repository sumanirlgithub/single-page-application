package com.core.exceptionhandling.badcode.example2;

class Solution {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        try {
            libraryService.borrowBook("12345", "user1");
        } catch (Exception e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }
}

class LibraryService {
    private BookRepository bookRepository = new BookRepository();

    public void borrowBook(String bookId, String userId) throws Exception {
        if (!bookRepository.isBookAvailable(bookId)) {
            throw new Exception("Book is not available");
        }
        if (!bookRepository.isValidUser(userId)) {
            throw new Exception("User is not valid");
        }
        bookRepository.updateBookStatus(bookId, false);
        System.out.println("Book borrowed successfully!");
    }
}

class BookRepository {
    public boolean isBookAvailable(String bookId) throws Exception {
        // Simulate a book availability check
        if (bookId.equals("00000")) {
            throw new Exception("Book not found");
        }
        return true;
    }

    public boolean isValidUser(String userId) throws Exception {
        // Simulate a user validation
        if (!userId.equals("user1")) {
            throw new Exception("Invalid user");
        }
        return true;
    }

    public void updateBookStatus(String bookId, boolean isAvailable) throws Exception {
        // Simulate updating book status
        if (bookId.equals("error")) {
            throw new Exception("Update failed");
        }
        System.out.println("Book status updated: " + (isAvailable ? "Available" : "Not available"));
    }
}