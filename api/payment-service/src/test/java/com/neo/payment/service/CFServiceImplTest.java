package com.neo.payment.service;

import com.neo.payment.connect.client.PaymentFeignClient;
import org.apache.tomcat.util.threads.InlineExecutorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

//@ContextConfiguration(classes = {CFServiceImpl.class, ExecutorService.class})
//@DisabledInAotMode
@ExtendWith(MockitoExtension.class)
public class CFServiceImplTest {

    @InjectMocks
    private CFServiceImpl cfServiceImpl;

    @MockBean
    private PaymentFeignClient paymentFeignClient;

    @MockBean
    private ExecutorService executorService;


    @Test
    @DisplayName("Test makeParallelAsyncCallToCitiConnect(String, String")
    void testMakeParallelAsyncCallToCitiConnect() {
        // Arrange
        InlineExecutorService paymentStatusExecutor = new InlineExecutorService();

        doNothing().when(executorService).execute(Mockito.<Runnable>any());

        // Act and Assert
        assertTrue((new CFServiceImpl(paymentStatusExecutor, paymentFeignClient))
                .makeParallelAsyncCallToConnect("42", "ABC123")
                .isDone());

        verify(executorService).execute(isA(Runnable.class));

    }
}
