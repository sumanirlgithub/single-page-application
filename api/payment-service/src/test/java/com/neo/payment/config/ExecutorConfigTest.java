package com.neo.payment.config;

import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class ExecutorConfigTest {

    @InjectMocks
    private ExecutorConfig executorConfig;

    @Test
    @SneakyThrows
    void test_paymentStatusExecutor() {
        FieldUtils.writeDeclaredField(executorConfig, "numberOfThreads", 1, true);
        assertNotNull(executorConfig.paymentStatusExecutor());
    }
}
