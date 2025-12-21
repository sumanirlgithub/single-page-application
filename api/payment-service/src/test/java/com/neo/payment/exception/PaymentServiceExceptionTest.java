package com.neo.payment.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceExceptionTest {

    @Test
    void test_constructor() {
        assertEquals("error", new PaymentServiceException("error").getMessage());
    }
}
