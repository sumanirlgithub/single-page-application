package com.neo.payment.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.nio.charset.StandardCharsets;

@Configuration
@PropertySource(CommonConfig.ERRORS_PROPERTIES_FULL_LOCATION)
public class CommonConfig {

    private static final String ERRORS_PROPERTIES_LOCATION = "classpath:errors";
    public static final String ERRORS_PROPERTIES_FULL_LOCATION = CommonConfig.ERRORS_PROPERTIES_LOCATION + ".properties";

    @Bean
    public MessageSource messageResource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(ERRORS_PROPERTIES_LOCATION);
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        return messageSource;
    }
}
