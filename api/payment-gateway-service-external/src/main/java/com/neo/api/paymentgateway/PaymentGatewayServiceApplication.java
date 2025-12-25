package com.neo.api.paymentgateway;

import com.neo.api.common.enums.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@Slf4j
@EnableFeignClients
@EnableScheduling
@RequiredArgsConstructor
@EnableDiscoveryClient
@EnableJpaRepositories(
        basePackages = "com.neo.api.order.repository",
        transactionManagerRef = "jpaTransactionManager"
)
public class PaymentGatewayServiceApplication implements CommandLineRunner {

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
        SpringApplication springApplication = new SpringApplication(PaymentGatewayServiceApplication.class);
        springApplication.run(args);
    }
    @Override
    public void run(String... args) throws Exception {
    }
}
