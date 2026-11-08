
package com.neo.payments.hub.model.payment;

public enum PaymentStatus {

    RECEIVED,
    VALIDATED,
    XML_GENERATED,
    SUBMITTED,
    ACCEPTED,
    REJECTED,
    SETTLED,
    RETURNED;

    public boolean canTransitionTo(PaymentStatus newStatus) {

        return switch (this) {

            case RECEIVED ->
                    newStatus == VALIDATED
                            || newStatus == REJECTED;

            case VALIDATED ->
                    newStatus == XML_GENERATED
                            || newStatus == REJECTED;

            case XML_GENERATED ->
                    newStatus == SUBMITTED
                            || newStatus == REJECTED;

            case SUBMITTED ->
                    newStatus == ACCEPTED
                            || newStatus == REJECTED;

            case ACCEPTED ->
                    newStatus == SETTLED
                            || newStatus == RETURNED;

            case SETTLED ->
                    newStatus == RETURNED;

            case REJECTED, RETURNED ->
                    false;
        };
    }
}
