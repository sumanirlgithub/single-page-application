package com.neo.payments.hub.dao.payment.strategy;


import com.neo.payments.hub.contract.payment.PaymentMethod;
import com.neo.payments.hub.model.payment.Payment;
import com.neo.payments.hub.model.payment.PaymentRecord;

public abstract class PaymentDetailsStrategy<T extends Payment> {

    protected abstract Class<T> paymentClass();

    protected abstract PaymentMethod paymentMethod();

    public boolean isApplicable(Payment payment) {
        return paymentClass().isInstance(payment);
    }

    public boolean supports(PaymentRecord record) {
        return paymentMethod() == record.paymentMethod();
    }

    protected abstract void saveDetails(T payment);

    protected abstract T findDetails(PaymentRecord record);

    public final void save(Payment payment) {

        T typedPayment = paymentClass().cast(payment);

        saveDetails(typedPayment);
    }

    public final T findById(PaymentRecord record) {

        return findDetails(record);
    }
}