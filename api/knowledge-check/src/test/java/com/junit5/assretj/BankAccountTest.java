package com.junit5.assretj;

import com.junit5.assertj.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.*;

public class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    public void setUp() {
        // This runs before EACH test method
        account = new BankAccount(100.00);
        System.out.println("Fresh account created with $100");
    }

    @Test
    public void shouldAddMoneyWhenDepositing() {
        // Arrange: Set up test data
        account = new BankAccount(100.00);

        // Act: Perform the action we're testing
        account.deposit(50.0);

        // Assert: Check if the result is what we expected
        assertEquals(150.0, account.getBalance());
    }

    @Test
    public void shouldWithdrawSuccessfullyWithSufficientFunds() {
        boolean result = account.withdraw(50.0);
        assertTrue(result); // Withdrawal should succeed

        assertEquals(50.0, account.getBalance());
    }

    @Test
    public void shouldWithdrawSuccessfullyWithInSufficientFunds() {
        boolean result = account.withdraw(150.0);
        assertFalse(result); // Withdrawal should fail

        assertEquals(100.0, account.getBalance()); // Balance unchanged
    }

}
