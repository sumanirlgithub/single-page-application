package com.neo.api.order.entity;

import com.neo.api.common.enums.OrderStatus;
import com.neo.api.common.enums.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PUR_ORDER")
public class PurchaseOrder {

    /**
     * Here join fetch will solve the N+1 problem in Spring Boot JPA
     * The N+1 query problem silently kills your app’s performance when dealing with relationships like @OneToMany.
     * Instead of fetching everything in one efficient query, JPA keeps lazily loading each child in a separate query.
     */
    public static final String GET_ORDER_BY_ORDER_ID_JOIN_FETCH =
            "SELECT item "
                    + "FROM OrderItem item "
                    + "join fetch item.purchaseOrder po "
                    + "where po.orderId =:orderId";


//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @NotNull
//    @Column(name = "ORDER_ID")
//    private UUID orderId;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "ORDER_ID")
    private String orderId;

    @OneToMany(mappedBy = "purchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();

    private String orderDetails;

    @Enumerated(EnumType.STRING)
    @Column(name = "ORDER_STATUS")
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;

    @Column(name = "FK_CUSTOMER_ID")
    private String customerId;

    @ManyToOne(fetch = FetchType.LAZY) // Default fetch type is EAGER for @ManyToOne
    @JoinColumn(name = "FK_CUSTOMER_ID", referencedColumnName = "CUS_ID", insertable = false, updatable = false)
    @JsonBackReference
    private Customer customer;

    @Column(name = "ORDER_DT_TIME")
    private LocalDateTime orderDateTime;

    @Column(name = "TOTAL_VAL")
    private BigDecimal totalValue;

}
