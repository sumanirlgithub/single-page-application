package com.core.interfaceexample.badcode;

class Solution {
    public static void main(String[] args) {
        Cash cashPayment = new Cash();
        CreditCard cardPayment = new CreditCard();

        StoreTransaction transaction = new StoreTransaction();
        transaction.performTransactionWithCash(cashPayment);
        transaction.performTransactionWithCard(cardPayment);
    }
}

class Cash {
    public void process() {
        System.out.println("Processing cash payment");
    }
}

class CreditCard {
    public void process() {
        System.out.println("Processing credit card payment");
    }
}

class StoreTransaction {
    void performTransactionWithCash(Cash cash) {
        cash.process();
    }

    void performTransactionWithCard(CreditCard card) {
        card.process();
    }
}