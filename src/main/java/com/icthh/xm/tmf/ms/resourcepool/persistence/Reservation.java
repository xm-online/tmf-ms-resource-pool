package com.icthh.xm.tmf.ms.resourcepool.persistence;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column
    private OffsetDateTime confirmDate;
    @Column
    private OffsetDateTime createDate;
    @Column
    private OffsetDateTime modifyDate;
    @Column
    private OffsetDateTime validFor;
    @Column
    private String channel;
    @Column
    private String reservedMsisdn;
    @Column
    private String reservationInitiator;
    @Column
    @Enumerated(EnumType.STRING)
    private Commerce commerce;
    @Column
    private String billingCategory;
    @Column
    private String reservationCode;
    @Column
    private String customerLang;
    @Column
    private Boolean isTimeoutNotified;
    @Column
    private BigDecimal serviceCost;
    @Column
    private String errorDescription;
    @Column
    private String errorCorrelationId;
}
