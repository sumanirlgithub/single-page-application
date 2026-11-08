package com.neo.payments.hub.contract.directdebits;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.neo.payments.hub.contract.enums.MandateStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * This class is used for storing Direct Debit details in tbvddmandate table.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectDebitsDTO {

    /** primary key - store direct debit ID in UUID format **/
    private String idVddMandate;

    private String idSubmission;

    /** primary key - store mandate ID **/
    private String idUmr;

    private Integer idIInternal;

    /** This field store tax registration number/PPSN for the taxpayer **/
    private String idEntity;

    @JsonIgnore
    private String customerId;

    @NotBlank(message = "typeAcct is required")
    private String typeAcct;

    private MandateStatus mandateStatus;

    private LocalDateTime datePaymentStart;

    private LocalDateTime dateMandateCreated;

    private LocalDateTime dateMandateExpired;

    private Map<String, String> errorDescMap;
}
