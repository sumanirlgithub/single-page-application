package com.neo.payments.hub.contract.enums;

import lombok.Getter;

@Getter
public enum MandateStatus {

    PENDING(0, false),
    ACTIVE(1, true),
    CANCELLED(2, false),
    EXPIRED(3, false);

    public final Integer code;
    public final boolean inUse;

    MandateStatus(Integer code, boolean inUse) {
        this.code = code;
        this.inUse = inUse;
    }
}
