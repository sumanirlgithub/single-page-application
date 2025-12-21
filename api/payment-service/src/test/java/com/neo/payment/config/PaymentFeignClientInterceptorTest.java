package com.neo.payment.config;

import feign.RequestTemplate;
import feign.Target;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {PaymentFeignClientInterceptor.class})
@ExtendWith(SpringExtension.class)
class PaymentFeignClientInterceptorTest {

    @Autowired
    private PaymentFeignClientInterceptor feignClientInterceptor;

    @Test
    @DisplayName("Test apply")
    void testApply() {
        //Arrange
        Target.HardCodedTarget<?> feignTarget = mock(Target.HardCodedTarget.class);

        when(feignTarget.name()).thenReturn("Name");

        RequestTemplate requestTemplate = new RequestTemplate();
        requestTemplate.feignTarget(feignTarget);

        // Act
        feignClientInterceptor.apply(requestTemplate);

        // Assert that nothing has changed
        verify(feignTarget).name();

    }
}
