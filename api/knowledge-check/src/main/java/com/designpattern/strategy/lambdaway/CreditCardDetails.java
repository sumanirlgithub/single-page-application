package com.designpattern.strategy.lambdaway;

public class CreditCardDetails {

    private String cardNumber;
    private String holderName;
    private String cvv;

    public CreditCardDetails(String cardNumber, String holderName, String cvv) {
        this.cardNumber = cardNumber;
        this.holderName = holderName;
        this.cvv = cvv;
    }

    // getters

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getHolderName() {
        return this.holderName;
    }
}
