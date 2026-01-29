package com.core.exceptionhandling.badcode.example1;

class Solution {
    public static void main(String[] args) {
        AccountService accountService = new AccountService();
        try {
            System.out.println("Start transfer fund");
            accountService.transferFunds("12345", "67890", 150.00);
        } catch (Exception e) {
            System.out.println("Transaction failed: " + e.getMessage());
        }
    }
}

class AccountService {
    private AccountRepository accountRepository = new AccountRepository();

    public void transferFunds(String fromAccountId, String toAccountId, double amount) throws Exception {
        try {
            accountRepository.withdraw(fromAccountId, amount);
            accountRepository.deposit(toAccountId, amount);
        } catch (Exception e) {
            throw new Exception("Transfer failed", e);
        }
    }
}

class AccountRepository {
    public void withdraw(String accountId, double amount) throws Exception {
        // Simulate withdrawal logic
        if (Math.random() < 0.5) {  // Simulating a random failure
            throw new Exception("Insufficient funds");
        }
        // Assumed logic for account not found
        if (accountId.equals("11111")) {
            throw new Exception("Account not found");
        }
    }

    public void deposit(String accountId, double amount) throws Exception {
        // Simulate deposit logic
        if (accountId.equals("11111")) {
            throw new Exception("Account not found");
        }
    }
}