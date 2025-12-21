package com.neo.payment.model.request;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class PaymentStatusReqTest {

    @Test
    void test_fields() {
        PaymentStatusReq req = new PaymentStatusReq();
        req.setUetrNumbers(Collections.emptyList());

        assertEquals(0, req.getUetrNumbers().size());
    }

    @Test
    void test_equals() {
        PaymentStatusReq req1 = new PaymentStatusReq(Collections.emptyList());
        assertEquals(req1, req1);
        assertNotEquals(req1, new Object());

        PaymentStatusReq req2 = PaymentStatusReq.builder()
                .uetrNumbers(Collections.emptyList())
                .build();
        assertEquals(req1, req2);

        req2.setUetrNumbers(Collections.singletonList("test"));
        assertNotEquals(req1, req2);
        req2.setUetrNumbers(null);
        assertNotEquals(req2, req1);
        req1.setUetrNumbers(null);
        assertEquals(req2, req1);
    }

    @Test
    void test_hasCode() {
        assertNotEquals(0, new PaymentStatusReq().hashCode());
    }

    @Test
    void test_toString() {
        assertNotEquals(0, new PaymentStatusReq().toString().length());
    }

}
