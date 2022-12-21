package com.enatbanksc.ATMReconciliation.batch;

import io.micrometer.core.lang.Nullable;
import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;

@Data
public class TransactionInput {
    private String issuer;
    private String acquirer;
    @Nullable
    private String MTI;
    private String cardNumber;
    private String amount;
    private String currency;
    private String transactionDate;
    private String transactionDesc;
    private String terminalId;
    private String transactionPlace;
    private String stan;
    private String refnumF37;
    private String authIdRespF38;
    private String FeUtrnno;
    private String BoUtrnno;
    private String feeAmountOne;
    private String feeAmountTwo;
}
