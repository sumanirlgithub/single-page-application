package com.neo.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
@Slf4j
public class ApiGatewayServiceApplication {

    /**
     * the entry point for the application.
     * <p>
     * By default, the application will register the {@link //SecretEnvironmentPropertyConfigure} for
     * retrieving sensitive data via the Secrets API.
     * </p>
     *
     * @param args the arguments used when running the application
     */
    public static void main(String[] args) {
        log.info("Starting Application Context");
        SpringApplication springApplication = new SpringApplication(ApiGatewayServiceApplication.class);
        springApplication.run(args);
    }
}
