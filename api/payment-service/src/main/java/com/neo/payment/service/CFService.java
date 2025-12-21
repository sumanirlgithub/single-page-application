package com.neo.payment.service;

import com.neo.payment.model.response.PaymentStatusRes;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public interface CFService {

    CompletableFuture<PaymentStatusRes> makeParallelAsyncCallToConnect(String uetrNumber, String finalAuthToken);
}
