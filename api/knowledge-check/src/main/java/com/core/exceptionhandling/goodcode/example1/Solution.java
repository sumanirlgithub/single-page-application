package com.core.exceptionhandling.goodcode.example1;

class Solution {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        try {
            System.out.println("Transaction");
            accountService.transferFunds("11111", "67890", 150.00);
        } catch (AccountServiceException e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}

class AccountService {
    private AccountRepository accountRepository = new AccountRepository();

    public void transferFunds(String fromAccountId, String toAccountId, double amount) {
        try {
            accountRepository.withdraw(fromAccountId, amount);
            accountRepository.deposit(toAccountId, amount);
        } catch (Exception e) {
            throw new AccountServiceException("Transfer failed", e);
        }
    }
}

class AccountRepository {
    public void withdraw(String accountId, double amount) {
        // Simulate withdrawal logic
        if (Math.random() < 0.5) {  // Simulating a random failure
            throw new InsufficientFundException("Insufficient funds");
        }
        // Assumed logic for account not found
        if (accountId.equals("11111")) {
            throw new AccountNotFoundException("Account not found");
        }
    }

    public void deposit(String accountId, double amount) {
        // Simulate deposit logic
        if (accountId.equals("11111")) {
            throw new AccountNotFoundException("Account not found");
        }
    }
}

class AccountNotFoundException extends RuntimeException  {
    AccountNotFoundException(String message) {
        super(message);
    }
}

class InsufficientFundException extends RuntimeException  {
    InsufficientFundException(String message) {
        super(message);
    }
}

class AccountServiceException extends RuntimeException  {
    AccountServiceException(String message, Exception e) {
        super(message, e);
    }

}