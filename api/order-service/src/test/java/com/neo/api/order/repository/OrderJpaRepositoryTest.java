package com.neo.api.order.repository;

import com.neo.api.common.enums.OrderStatus;
import com.neo.api.common.enums.PaymentStatus;
import com.neo.api.order.entity.OrderItem;
import com.neo.api.order.entity.PurchaseOrder;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class OrderJpaRepositoryTest {
    @Autowired
    OrderJpaRepository orderJpaRepository;

    @BeforeEach
    public void setUp() {
        OrderItem item = new OrderItem();
        item.setItemId("999");
        item.setItemName("my-item");
        item.setItemCode(001);
        item.setQuantity(5);
        item.setUnitPrice(BigDecimal.valueOf(10));

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("101");
        purchaseOrder.setOrderDetails("my-order");
        purchaseOrder.setOrderStatus(OrderStatus.PENDING);
        purchaseOrder.setPaymentStatus(PaymentStatus.PENDING);
        purchaseOrder.setTotalValue(BigDecimal.valueOf(1000.00));
        purchaseOrder.setItems(Arrays.asList(item));
        orderJpaRepository.saveAndFlush(purchaseOrder);
        // one row already inserted in OrderServiceApplication.java
    }

    @AfterEach
    public void destroy() {
        orderJpaRepository.deleteAll();
    }

    @Test
    public void testGetAllOrders() {
        List<PurchaseOrder> orderList = orderJpaRepository.findAll();
        Assertions.assertThat(orderList.size()).isEqualTo(2);
    }
}
