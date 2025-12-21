package com.neo.payment.connect.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TransactionStatus {
    private String status;
    private List<StatusReasonInfo> statusReasonInfoList;
}
