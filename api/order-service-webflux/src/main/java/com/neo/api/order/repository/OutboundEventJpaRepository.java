package com.neo.api.order.repository;

import com.neo.api.order.entity.OutboundEvent;
import com.neo.api.order.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboundEventJpaRepository extends JpaRepository<OutboundEvent, Long> {

    List<OutboundEvent> findTop100BySentFalseOrderByCreatedAtAsc();
}
