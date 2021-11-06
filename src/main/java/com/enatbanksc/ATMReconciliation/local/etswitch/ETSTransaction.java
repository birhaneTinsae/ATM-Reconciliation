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
@Entity(name = "ets_transactions")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class ETSTransaction implements Serializable, Comparable<ETSTransaction> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String issuer;
    private String acquirer;
    @Nullable
    private int MTI;
    private String cardNumber;
    private float amount;
    private String currency;
    private LocalDateTime transactionDate;
    private String transactionDesc;
    private String terminalId;
    private String transactionPlace;
    private int stan;
    private int refnumF37;
    private String authIdRespF38;
    private String FeUtrnno;
    private String BoUtrnno;
    private Float feeAmountOne;
    private Float feeAmountTwo;

    public ETSTransaction(String issuer, String acquirer, int MTI, String cardNumber, float amount, String currency,
                          LocalDateTime transactionDate, String transactionDesc, String terminalId, String transactionPlace, int stan,
                          int refnumF37, String authIdRespF38, String FeUtrnno, String BoUtrnno, float feeAmountOne,
                          float feeAmountTwo) {
        this.issuer = issuer;
        this.acquirer = acquirer;
        this.MTI = MTI;
        this.cardNumber = cardNumber;
        this.amount = amount;
        this.currency = currency;
        this.transactionDate = transactionDate;
        this.transactionDesc = transactionDesc;
        this.terminalId = terminalId;
        this.transactionPlace = transactionPlace;
        this.stan = stan;
        this.refnumF37 = refnumF37;
        this.authIdRespF38 = authIdRespF38;
        this.FeUtrnno = FeUtrnno;
        this.BoUtrnno = BoUtrnno;
        this.feeAmountOne = feeAmountOne;
        this.feeAmountTwo = feeAmountTwo;
    }

    //
    @Override
    public int compareTo(ETSTransaction o) {
        return Integer.compare(o.getStan(), this.getStan());
    }

}
