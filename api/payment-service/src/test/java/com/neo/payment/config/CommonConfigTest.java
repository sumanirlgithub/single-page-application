package com.neo.payment.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = {CommonConfig.class})
@ExtendWith(SpringExtension.class)
class CommonConfigTest {

    @Autowired
    private CommonConfig commonConfig;

    @Test
    @DisplayName("Test messageSource")
    void test_MessageSource() {
        // Arrange amd Act
        MessageSource actualMessageSourceResult = commonConfig.messageResource();

        // Assert
        assertTrue(actualMessageSourceResult instanceof ReloadableResourceBundleMessageSource);
        assertNull(
                ((ReloadableResourceBundleMessageSource) actualMessageSourceResult)
                        .getParentMessageSource());
        Set<String> basenameSet =
                ((ReloadableResourceBundleMessageSource) actualMessageSourceResult)
                        .getBasenameSet();
        assertEquals(1, basenameSet.size());
        assertTrue(basenameSet.contains("classpath:errors"));
    }

}
