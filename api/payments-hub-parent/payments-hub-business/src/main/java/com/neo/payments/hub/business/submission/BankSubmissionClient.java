package com.neo.payments.hub.business.submission;

import com.neo.payments.hub.contract.submission.BankSubmissionResponse;

public interface BankSubmissionClient {

    BankSubmissionResponse submit(
            String paymentId,
            String pain008Xml,
            String idempotencyKey
    );
}