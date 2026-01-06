package com.neo.api.tenant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@SpringBootApplication
@Slf4j
public class ExternalTenantApplication {

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
        SpringApplication springApplication = new SpringApplication(ExternalTenantApplication.class);
        springApplication.run(args);
    }
}
