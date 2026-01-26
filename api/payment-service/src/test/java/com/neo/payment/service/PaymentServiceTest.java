package com.neo.payment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.neo.payment.connect.client.AuthFeignClient;
import com.neo.payment.connect.response.ConnectAuthResponse;
import com.neo.payment.connect.response.ConnectPaymentStatusResponse;
import com.neo.payment.connect.response.PaymentTransaction;
import com.neo.payment.dto.PaymentInsightRequest;
import com.neo.payment.dto.PaymentInsightRequestOrg;
import com.neo.payment.exception.PaymentServiceException;
import com.neo.payment.model.response.PaymentStatusRes;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private AuthFeignClient authFeignClient;
    @Mock
    private CFService cfService;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void testGetPaymentStatus_ThrowPaymentException() {
        PaymentInsightRequestOrg request = new PaymentInsightRequestOrg(
                Arrays.asList("123456", "12345", "1234", "123", "12", "1"));

        assertThrows(PaymentServiceException.class, () -> {
            paymentService.getPaymentStatus(request);
        });
    }

    @Test
    void testGetAuthTokenFromneoConnect_throwPaymentException()  throws Exception {
        when(authFeignClient.getAuthTokenFromConnectApi(any(), any())).thenReturn(null);

        assertThrows(PaymentServiceException.class, () -> {
            paymentService.getAuthTokenFromConnect();
        });

    }

    @Test
    void testGetPaymentStatus_Success_200() throws Exception {
        PaymentInsightRequestOrg request = new PaymentInsightRequestOrg(
                Arrays.asList("123456"));
        ConnectAuthResponse authResponse = new ConnectAuthResponse();
        authResponse.setDecryptedContent("{\"token\":{ \"token_type\":Bearer\", \"access_token\":test\", \"expire_in:\"1800, \"scope\":\"/authenticationservices/v1\" } }");
        ResponseEntity<ConnectAuthResponse> response = new ResponseEntity<>(HttpStatus.OK);
        when(authFeignClient.getAuthTokenFromConnectApi(any(), any())).thenReturn(response);

        ConnectPaymentStatusResponse paymentStatusResponse = new ConnectPaymentStatusResponse();
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentStatusResponse.setTransactions(Arrays.asList(paymentTransaction));

        when(cfService.makeParallelAsyncCallToConnect(anyString(), any())).thenReturn(CompletableFuture.completedFuture(new PaymentStatusRes()));

        List<PaymentStatusRes> result = paymentService.getPaymentStatus(request);

        assertEquals(1, result.size());
    }
}


