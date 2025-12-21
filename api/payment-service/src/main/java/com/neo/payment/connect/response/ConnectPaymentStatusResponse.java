package com.neo.payment.connect.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConnectPaymentStatusResponse {
    List<PaymentTransaction> transactions;
}
