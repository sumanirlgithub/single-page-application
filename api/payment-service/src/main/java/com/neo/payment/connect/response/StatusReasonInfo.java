package com.neo.payment.connect.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatusReasonInfo {
    private String reason;
    private String reason_description;
    private String type;
}
