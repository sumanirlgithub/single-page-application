package com.neo.payment.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {

    ACCP("ACCP"),
    REJCT("REJCT");

    private final String value;
}
