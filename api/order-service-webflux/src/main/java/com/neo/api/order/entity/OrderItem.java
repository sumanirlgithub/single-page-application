package com.neo.api.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "ORDER_ITEM")
public class OrderItem {

//    @Id
//    @GeneratedValue(generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
//    @NotNull
//    @Column(name = "ITEM_ID")
//    private UUID itemId;

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "ITEM_ID")
    private String itemId;

    @ManyToOne(fetch = FetchType.LAZY) // Default fetch type is EAGER for @ManyToOne and @OneToOne
    @JoinColumn(name = "FK_ORDER_ID")
    @JsonBackReference
    private PurchaseOrder purchaseOrder;

    @NotNull
    @Column(name = "ITEM_NAME")
    private String itemName;

    @NotNull
    @Column(name = "ITEM_CODE")
    private Integer itemCode;

    @NotNull
    @Column(name = "QUANTITY")
    private Integer quantity;

    @NotNull
    @Column(name = "UNIT_PRICE")
    private BigDecimal unitPrice;

}
