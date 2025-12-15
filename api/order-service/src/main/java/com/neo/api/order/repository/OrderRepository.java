package com.neo.api.order.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager entityManager;
    public static final String GET_ITEMS_FOR_ORDER =
            "SELECT ORDITEM.ITEM_NAME "
                    + " FROM ORDER_ITEM ORDITEM "
                    + " WHERE ORDITEM.FK_ORDER_ID = :orderId";

    /**
     * Get the Order Item List for the given Order.
     */
    public List<String> getOrderItemsForOrder(String orderId) {
        return entityManager.createNativeQuery(GET_ITEMS_FOR_ORDER)
                .setParameter("orderId", orderId)
                .unwrap(Query.class)
                .getResultList();
    }

}
