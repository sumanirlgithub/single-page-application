package com.neo.api.order.model.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentStatusReq {

    @Size(min=1, max = 5, message = "UetrNumbers is required, and Maximum number of uetrs allowed is")
    private List<String> uetrNumbers;
}
