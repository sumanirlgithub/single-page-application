package com.neo.api.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
@Slf4j
public class ConfigurationServiceApplication {

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
        SpringApplication springApplication = new SpringApplication(ConfigurationServiceApplication.class);
        springApplication.run(args);
    }
}
