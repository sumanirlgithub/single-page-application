package com.neo.api.order;

import com.neo.api.common.enums.OrderStatus;
import com.neo.api.order.entity.Customer;
import com.neo.api.order.entity.OrderItem;
import com.neo.api.order.entity.PurchaseOrder;
import com.neo.api.order.repository.CustomerRepository;
import com.neo.api.order.repository.OrderJpaRepository;
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
public class OrderServiceWebFluxApplication implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final OrderJpaRepository orderJpaRepository;

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
        SpringApplication springApplication = new SpringApplication(OrderServiceWebFluxApplication.class);
        springApplication.run(args);
    }
    @Override
    public void run(String... args) throws Exception {
        Customer customer = new Customer();
        customer.setName("Suman");
        customer.setEmail("suman@gmail.com");
        customerRepository.saveAndFlush(customer);

        List<OrderItem> orderItems = new ArrayList<>();
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName("first-item");
        orderItem.setUnitPrice(BigDecimal.valueOf(20.0));
        orderItem.setItemCode(101);
        orderItem.setQuantity(5);
        orderItem.setPurchaseOrder(purchaseOrder);
        orderItems.add(orderItem);

        purchaseOrder.setOrderDetails("first-order");
        purchaseOrder.setItems(orderItems);
        purchaseOrder.setOrderStatus(OrderStatus.CREATED);
        purchaseOrder.setCustomerId(customer.getCustomerId());

        orderJpaRepository.saveAndFlush(purchaseOrder);
    }
}
