package com.neo.api.order.repository;

import com.neo.api.order.dto.PurchaseOrderDTO;
import jakarta.persistence.EntityManager;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;

    private static final String GET_ORDER_DETAILS = """
        SELECT order_id, order_details, total_val, order_status
        FROM PUR_ORDER
        WHERE order_id = :orderId
    """;

    public PurchaseOrderDTO getOrderDetails(String orderId) {
        Tuple t = (Tuple) entityManager.createNativeQuery(GET_ORDER_DETAILS, Tuple.class)
                .setParameter("orderId", orderId)
                .getSingleResult();

        return new PurchaseOrderDTO(
                t.get("order_id", String.class),
                t.get("order_details", String.class),
                t.get("total_val", BigDecimal.class).doubleValue(),
                t.get("order_status", String.class));
    }

}
