package com.core.inheritancewithcomposition.goodcode.example2;

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

class Account {
    double balance;
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
class CheckingAccount extends Account {

}

class SavingsAccount extends Account {

    public void addInterest() {
        double interest = balance * 0.05; // 5% interest
        balance += interest;
        System.out.println("Interest added, New Balance: " + balance);
    }
}

class LoanAccount extends Account {

    public void repayLoan(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Loan repayment, New Balance: " + balance);
        }
    }
}