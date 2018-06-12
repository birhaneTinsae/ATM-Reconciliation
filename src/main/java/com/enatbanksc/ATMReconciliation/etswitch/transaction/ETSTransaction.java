/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch.transaction;

import io.micrometer.core.lang.Nullable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

/**
 *
 * @author btinsae
 */
@Entity(name = "ets_transactions")
@Getter
@Setter
@NoArgsConstructor
public class ETSTransaction /*extends ResourceSupport*/ implements Serializable, Comparable<ETSTransaction> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String issuer;
    private String acquirer;
    @Nullable
    private int MTI;
//    @Column(name="card_number")
    private String cardNumber;
    private float amount;
    private String currency;
    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name="transaction_date")
    private Date transactionDate;
//    @Column(name="transaction_desc")
    private String transactionDesc;
//    @Column(name="terminal_id")
    private String terminalId;
//    @Column(name="transaction_place")
    private String transactionPlace;
    private int stan;
    private String refnumF37;
//    @Column(name="auth_Id_RespF38")
    private String authIdRespF38;
//    @Column(name="Fe_Utrnno")
    private String FeUtrnno;
//    @Column(name="Bo_Utrnno")
    private String BoUtrnno;
//    @Column(name="fee_Amount_One")
   // private float feeAmountOne;
//    @Column(name="fee_Amount_Two")
   // private float feeAmountTwo;
																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																				
	
    public ETSTransaction(String issuer, String acquirer, int MTI, String cardNumber, float amount, String currency, Date transactionDate, String transactionDesc, String terminalId, String transactionPlace, int stan, String refnumF37, String authIdRespF38, String FeUtrnno, String BoUtrnno/*, float feeAmountOne, float feeAmountTwo*/) {
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
//        this.feeAmountOne = feeAmountOne;
//        this.feeAmountTwo = feeAmountTwo;
    }

    @Override
    public int compareTo(ETSTransaction o) {
        return this.getStan() > o.getStan() ? -1 : (this.getStan() < o.getStan()) ? 1 : 0;
    }

    @Override
    public String toString() {
        return "ETSTransaction{" + "id=" + id + ", issuer=" + issuer + ", acquirer=" + acquirer + ", MTI=" + MTI + ", cardNumber=" + cardNumber + ", amount=" + amount + ", currency=" + currency + ", transactionDate=" + transactionDate + ", transactionDesc=" + transactionDesc + ", terminalId=" + terminalId + ", transactionPlace=" + transactionPlace + ", stan=" + stan + ", refnumF37=" + refnumF37 + ", authIdRespF38=" + authIdRespF38 + ", FeUtrnno=" + FeUtrnno + ", BoUtrnno=" + BoUtrnno /*+ ", feeAmountOne=" + feeAmountOne + ", feeAmountTwo=" + feeAmountTwo */+ '}';
    }

}
