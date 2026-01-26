package com.neo.api.order.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "Outbound_Event")
public class OutboundEvent {

    @Id
    @GeneratedValue
    private Long eventId;

    @Column(name = "event_type", nullable = false)
    private String eventType;

    @Column(name="order_id", nullable = false)
    private String orderId;

    @Column(nullable = false)
    private boolean sent = false;

    @Lob
    @Column(name = "paylaod",  columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}