package com.neo.payment.model.request;

import com.neo.payment.dto.PaymentInsightRequest;
import com.neo.payment.dto.PaymentInsightRequestOrg;
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
        PaymentInsightRequestOrg req = new PaymentInsightRequestOrg();
        req.setUetrNumbers(Collections.emptyList());

        assertEquals(0, req.getUetrNumbers().size());
    }

    @Test
    void test_equals() {
        PaymentInsightRequestOrg req1 = new PaymentInsightRequestOrg(Collections.emptyList());
        assertEquals(req1, req1);
        assertNotEquals(req1, new Object());

        PaymentInsightRequestOrg req2 = PaymentInsightRequestOrg.builder()
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
        assertNotEquals(0, new PaymentInsightRequest().hashCode());
    }

    @Test
    void test_toString() {
        assertNotEquals(0, new PaymentInsightRequest().toString().length());
    }

}
