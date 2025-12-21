package com.neo.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PAYMENT_ISO_MAPPING", schema = "PMTDATA")
@SequenceGenerator(name = "iso_mapping_seq", sequenceName = "ISO_MAPPING_SEQ", allocationSize = 1) //Oracle database
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ISOMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "iso_mapping_seq")
    private Integer id;

    @Column(name = "STATUS_CODE", length = 10, nullable = false)
    private String statusCode;

    @Column(name = "REASON_CODE", length = 10, nullable = false)
    private String reasonCode;

    @Column(name = "ADDITIONAL_INFO", length = 255)
    private String additionalInfo;

    @Column(name = "STATUS", length = 20)
    private String status;

    @Column(name = "REASON", length = 255)
    private String statusReason;

}
