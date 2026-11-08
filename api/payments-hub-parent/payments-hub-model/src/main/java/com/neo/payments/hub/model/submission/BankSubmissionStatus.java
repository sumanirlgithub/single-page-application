
package com.neo.payments.hub.model.submission;

public enum BankSubmissionStatus {

    READY,
    PROCESSING,
    SUBMITTED,
    ACCEPTED,
    REJECTED,
    RETRYABLE,
    UNKNOWN
}
