package com.neo.payment.entity;

import com.neo.api.common.enums.PaymentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT")
public class Payment {
    @Id
    @GeneratedValue(generator = "UUID")
    @NotNull
    @Column(name = "PAYMENT_ID")
    private UUID paymentId;

    @Column(name = "CARD_NUMBER")
    private String cardNumber;

    @Column(name = "CARD_HOLDER")
    private String cardHolder;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CARD_EXPIRE")
    private String cardExpiry;

    @Column(name = "CVC")
    private Integer cvc;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAYMENT_STATUS")
    private PaymentStatus paymentStatus;

    @NotNull
    @Column(name = "ORDER_ID")
    private String orderId;

}
