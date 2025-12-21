package com.neo.payment;

import com.neo.PaymentApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.SpringApplication;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentApplicationTest {

    @Test
    void test_main() {
        try(MockedStatic<SpringApplication> mockedStatic = mockStatic(SpringApplication.class)) {
            String[] args = {"test"};
            when(SpringApplication.run(eq(PaymentApplication.class), eq(args))).thenReturn(null);
            PaymentApplication.main(args);
        }
    }

}
