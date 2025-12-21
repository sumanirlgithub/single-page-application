package com.neo.api.common.order.event;

import com.neo.api.common.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateOrderStatusEvent implements Serializable {

    private UUID orderId;
    private OrderStatus orderStatus;

}
