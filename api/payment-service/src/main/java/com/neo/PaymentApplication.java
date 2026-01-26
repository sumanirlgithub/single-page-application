package com.neo;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@ComponentScan(basePackages = {"com.neo"})
@EnableFeignClients("com.neo")
@EnableDiscoveryClient
@EnableScheduling
//@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
//@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
@SpringBootApplication
public class PaymentApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(PaymentApplication.class);
		springApplication.run(args);
		System.out.println("Welcome to Spring Boot Application - PaymentApplication");
		System.out.println("Print VM Option ENV=" + System.getProperty("ENV"));
	}
}
