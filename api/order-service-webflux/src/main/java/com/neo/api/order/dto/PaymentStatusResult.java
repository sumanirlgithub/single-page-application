package com.neo.api.order.dto;

import com.neo.api.order.model.PaymentInsightResponse;
import lombok.Builder;

@Builder
public class PaymentStatusResult {

    private String orderId;
    private boolean success;
    private String paymentStatus;
    private String message;

    /**
     * Factory methods
     * @param orderId
     * @param response
     * @return
     */
    public static PaymentStatusResult success(String orderId, PaymentInsightResponse response) {
        return new PaymentStatusResult(
                orderId,
                true,
                response.getStatus(),      // e.g. SUCCESS
                "Payment processed successfully"
        );
    }

    /**
     * Factory methods
     * @param orderId
     * @param ex
     * @return
     */
    public static PaymentStatusResult failure(String orderId, Throwable ex) {
        return new PaymentStatusResult(
                orderId,
                false,
                "FAILED",
                ex.getMessage()
        );
    }
}
