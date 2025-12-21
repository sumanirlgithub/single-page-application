package com.neo.payment.config;

import com.citi.issuer.sharedservices.payment.common.errors.ApiServiceException;
import lombok.SneakyThrows;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.KeyStore;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FeignClientConfigTest {

    @InjectMocks
    private FeignClientConfig feignClientConfig;

    @Test
    @SneakyThrows
    void test_feignClient() {
        FieldUtils.writeDeclaredField(feignClientConfig, "trustStore", "classpath:client.jks", true);
        FieldUtils.writeDeclaredField(feignClientConfig, "trustStorePass", "Password1", true);
        FieldUtils.writeDeclaredField(feignClientConfig, "clientId", "clientId", true);
        feignClientConfig.feignClient();
    }

    @Test
    @SneakyThrows
    void test_feignClient_throwException() {
        FieldUtils.writeDeclaredField(feignClientConfig, "trustStore", "classpath:client.jks", true);
        FieldUtils.writeDeclaredField(feignClientConfig, "trustStorePass", "Password1", true);
        FieldUtils.writeDeclaredField(feignClientConfig, "clientId", "clientId", true);

        try (MockedStatic<KeyStore> mockedStatic = mockStatic(KeyStore.class)) {
            when(KeyStore.getInstance(anyString())).thenThrow(new RuntimeException());

            assertThrows(ApiServiceException.class, () -> feignClientConfig.feignClient());
        }
    }

}
