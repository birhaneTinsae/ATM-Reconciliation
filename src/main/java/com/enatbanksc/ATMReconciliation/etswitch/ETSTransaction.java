/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch;

import io.micrometer.core.lang.Nullable;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String issuer;
    private String acquirer;
    @Nullable
    private int MTI;
    //   @Column(name = "card_number")
    private String cardNumber;
    private float amount;
    private String currency;
    //   @Column(name = "transaction_date")
    private LocalDate transactionDate;
    //  @Column(name = "transaction_desc")
    private String transactionDesc;
    //    @Column(name = "terminal_id")
    private String terminalId;

    private String transactionPlace;
    private Integer stan;

    private String refnumF37;

    private String authIdRespF38;

    private String FeUtrnno;
    //    @Column(name = "BoUtrnno")
    private String BoUtrnno;
    //   @Column(name = "fee_amount_one")
    private Float feeAmountOne;
    //  @Column(name = "fee_amount_two")
    private Float feeAmountTwo;

    @Override
    public int compareTo(ETSTransaction o) {
        return o.getStan().compareTo(this.getStan());
    }

}
