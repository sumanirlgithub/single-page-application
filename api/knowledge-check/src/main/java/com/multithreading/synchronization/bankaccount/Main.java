package com.multithreading.synchronization.bankaccount;

/**
 * Now, let's use all the skills you've learned to create a thread-safe BankAccount class. In this task, you will:
 *
 * Create a BankAccount class with a private balance field.
 * Implement synchronized methods for deposit(int amount) and withdraw(int amount).
 * The withdraw(int amount) method should ensure that withdrawals do not exceed the current balance.
 * In the Main class, create multiple threads to simulate concurrent deposits and withdrawals.
 * Use the join() method to wait for all threads to complete their operations.
 * Finally, verify the correct balance by printing it at the end.
 * This task will help you gain hands-on experience with thread synchronization and ensure safe modification of shared resources.
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // TODO: Create a BankAccount instance
        // TODO: Create and start multiple threads in a similar way to simulate deposits and withdrawals
        // TODO: Ensure each thread calls `deposit` or `withdraw` appropriately
        // TODO: Wait for all threads to finish using `join`
        // TODO: Print the final balance

        BankAccount account = new BankAccount();

        // Example Thread to Deposit
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(1);
            }
        });


        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                account.withdraw(1);
            }
        });

        t1.join();
        t2.join();

        System.out.println("final balance: " + account.getBalance());

    }
}