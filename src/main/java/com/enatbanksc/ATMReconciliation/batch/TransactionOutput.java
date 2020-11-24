package com.enatbanksc.ATMReconciliation.batch;

import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionOutput {
    private String issuer;
    private String acquirer;
    @Nullable
    private int MTI;
    private String cardNumber;
    private float amount;
    private String currency;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime transactionDate;
    private String transactionDesc;
    private String terminalId;
    private String transactionPlace;
    private Integer stan;
    private String refnumF37;
    private String authIdRespF38;
    private String FeUtrnno;
    private String BoUtrnno;
    private Float feeAmountOne;
    private Float feeAmountTwo;
}
