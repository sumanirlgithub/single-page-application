package com.multithreading.synchronization.bankaccount;

// TODO: Create a `BankAccount` class
// TODO: Add a private `balance` field
// TODO: Implement a `synchronized` method for `deposit(int amount)`
// TODO: Implement a `synchronized` method for `withdraw(int amount)`
// TODO: Implement a `synchronized` method for `getBalance()`

class BankAccount {

    private int balance;

    public synchronized void deposit(int amount) {
        this.balance = this.balance + amount;
    }

    public synchronized void withdraw(int amount) {

        if (amount > this.balance) {
            throw new RuntimeException("withdrawals amount is greater than balance");
        }

        this.balance = this.getBalance() - amount;
    }

    public synchronized int getBalance() {
        return this.balance;
    }
}