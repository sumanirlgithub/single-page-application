package com.neo.payment.enums;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StatusTest {

    @Test
    void getValue_test() {
        assertEquals("ACCP", Status.ACCP.getValue());
    }

}
