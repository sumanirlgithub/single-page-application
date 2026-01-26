package com.neo.api.order.model;

import lombok.Data;

import java.util.List;

@Data
public class PaymentInsightRequest {
    List<String> uetrNumbers;
}
