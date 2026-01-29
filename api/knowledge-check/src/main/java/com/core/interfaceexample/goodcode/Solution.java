package com.core.interfaceexample.goodcode;

class Solution {
    public static void main(String[] args) {
        PaymentType cashPayment = new Cash();
        PaymentType cardPayment = new CreditCard();

        StoreTransaction transaction = new StoreTransaction();
        transaction.performTransactionWithCash(cashPayment);
        transaction.performTransactionWithCard(cardPayment);
    }
}


interface PaymentType {
    void process();
}

class Cash implements PaymentType {
    @Override
    public void process() {
        System.out.println("Processing cash payment");
    }
}

class CreditCard implements PaymentType {
    @Override
    public void process() {
        System.out.println("Processing credit card payment");
    }
}

class StoreTransaction {
    void performTransactionWithCash(PaymentType paymentType) {
        paymentType.process();
    }

    void performTransactionWithCard(PaymentType paymentType) {
        paymentType.process();
    }
}