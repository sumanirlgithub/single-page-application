package com.core.inheritancewithcomposition.badcode.example2;

/**
 * You'll be given three independent classes from a simple banking application, all of which have duplicated functionality.
 * Your task is to identify these redundancies and refactor the code by extracting common functionality into a parent class.
 * This will make the code more organized, maintainable, and easier to extend.
 */
class Solution {
    public static void main(String[] args) {
        CheckingAccount checkingAccount = new CheckingAccount();
        checkingAccount.deposit(100);
        checkingAccount.withdraw(50);

        SavingsAccount savingsAccount = new SavingsAccount();
        savingsAccount.deposit(200);
        savingsAccount.addInterest();

        LoanAccount loanAccount = new LoanAccount();
        loanAccount.withdraw(300);
        loanAccount.repayLoan(100);
    }
}

class CheckingAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + ", New balance: " + balance);
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ", New balance: " + balance);
        } else {
            System.out.println("Withdrawal failed. Insufficient balance.");
        }
    }
}

class SavingsAccount {
    private double balance;

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount + ", New balance: " + balance);
        }
    }

    public void addInterest() {
        double interest = balance * 0.05; // 5% interest
        balance += interest;
        System.out.println("Interest added, New Balance: " + balance);
    }
}

class LoanAccount {
    private double balance;

    public void withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ", New balance: " + balance);
        } else {
            System.out.println("Withdrawal failed. Insufficient balance.");
        }
    }

    public void repayLoan(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Loan repayment, New Balance: " + balance);
        }
    }
}