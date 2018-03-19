/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch.transaction;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author btinsae
 */
@Entity
@Getter
@Setter
public class ETSTransaction implements Serializable, Comparable<ETSTransaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String issuer;
    private String acquirer;
    private int MTI;
    private String cardNumber;
    private float amount;
    private String currency;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    private String transactionDesc;
    private String terminalId;
    private String transactionPlace;
    private int stan;
    private String refnumF37;
    private String authIdRespF38;
    private String FeUtrnno;
    private String BoUtrnno;
    private float feeAmountOne;
    private float feeAmountTwo;

    @Override
    public int compareTo(ETSTransaction o) {
        return this.getStan() > o.getStan() ? -1 : (this.getStan() < o.getStan()) ? 1 : 0;
    }
}
