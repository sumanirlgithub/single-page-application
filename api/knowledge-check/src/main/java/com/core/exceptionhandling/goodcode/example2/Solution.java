package com.core.exceptionhandling.goodcode.example2;

class Solution {
    public static void main(String[] args) {
        LibraryService libraryService = new LibraryService();
        try {
            libraryService.borrowBook("12345", "user1");
        } catch (LibraryServiceException e) {
            System.out.println("Operation failed: " + e.getMessage());
        }
    }
}

class LibraryService {
    private BookRepository bookRepository = new BookRepository();

    public void borrowBook(String bookId, String userId) {
        if (!bookRepository.isBookAvailable(bookId)) {
            throw new LibraryServiceException("Book is not available");
        }
        if (!bookRepository.isValidUser(userId)) {
            throw new UserInvalidException("User is not valid");
        }
        bookRepository.updateBookStatus(bookId, false);
        System.out.println("Book borrowed successfully!");
    }
}

class BookRepository {
    public boolean isBookAvailable(String bookId) {
        // Simulate a book availability check
        if (bookId.equals("00000")) {
            throw new BookProcessingException("Book not found");
        }
        return true;
    }

    public boolean isValidUser(String userId) {
        // Simulate a user validation
        if (!userId.equals("user1")) {
            throw new UserInvalidException("Invalid user");
        }
        return true;
    }

    public void updateBookStatus(String bookId, boolean isAvailable) {
        // Simulate updating book status
        if (bookId.equals("error")) {
            throw new BookProcessingException("Update failed");
        }
        System.out.println("Book status updated: " + (isAvailable ? "Available" : "Not available"));
    }
}

class LibraryServiceException extends RuntimeException {
    LibraryServiceException(String message) {
        super(message);
    }
}

class BookProcessingException extends RuntimeException {
    BookProcessingException(String message) {
        super(message);

    }

}

class UserInvalidException extends RuntimeException {
    UserInvalidException(String message) {
        super(message);

    }

}