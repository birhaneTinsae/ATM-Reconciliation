/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.local.etswitch;

import io.micrometer.core.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author btinsae
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ETSTransactionInput implements Serializable, Comparable<ETSTransactionInput> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String issuer;
    private String acquirer;
    @Nullable
    private int MTI;
    private String cardNumber;
    private float amount;
    private String currency;
    private String transactionDate;
    private String transactionDesc;
    private String terminalId;
    private String transactionPlace;
    private int stan;
    private String refnumF37;
    private String authIdRespF38;
    private String FeUtrnno;
    private String BoUtrnno;
    private Float feeAmountOne;
    private Float feeAmountTwo;

    //
    @Override
    public int compareTo(ETSTransactionInput o) {
        return Integer.compare(o.getStan(), this.getStan());
    }

}
