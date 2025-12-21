package com.neo.payment.connect.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AdditionalRemittanceInformation {
    private String confidential;
}
