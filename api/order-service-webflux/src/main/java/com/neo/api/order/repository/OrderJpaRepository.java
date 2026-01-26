package com.neo.api.order.repository;

import com.neo.api.order.entity.OrderItem;
import com.neo.api.order.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.neo.api.order.entity.PurchaseOrder.GET_ORDER_BY_ORDER_ID_JOIN_FETCH;

@Repository
public interface OrderJpaRepository extends JpaRepository<PurchaseOrder, String> {
    Optional<PurchaseOrder> findByOrderId(String orderId);

    //@Query(value = GET_ORDER_BY_ORDER_ID_JOIN_FETCH, nativeQuery = true)
    @Query(value = GET_ORDER_BY_ORDER_ID_JOIN_FETCH) // JPQL query
    List<OrderItem> findByOrderIdWithJoinFetch(@Param("orderId") String orderId);

}
