/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch.transaction;

import io.micrometer.core.lang.Nullable;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *
 * @author btinsae
 */
@Entity(name = "ets_transactions")
@NoArgsConstructor
@Data
public class ETSTransaction  implements Serializable, Comparable<ETSTransaction> {

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
	// @Column(name="card_number")
	private String cardNumber;
	private float amount;
	private String currency;
	@Temporal(TemporalType.TIMESTAMP)
	// @Column(name="transaction_date")
	private Date transactionDate;
	// @Column(name="transaction_desc")
	private String transactionDesc;
	// @Column(name="terminal_id")
	private String terminalId;
	// @Column(name="transaction_place")
	private String transactionPlace;
	private int stan;
	private String refnumF37;
	// @Column(name="auth_Id_RespF38")
	private String authIdRespF38;
	// @Column(name="Fe_Utrnno")
	private String FeUtrnno;
	// @Column(name="Bo_Utrnno")
	private String BoUtrnno;
	// @Column(name="feeAmountOne")
	private float feeAmountOne;
	// @Column(name="feeAmountTwo")
	private float feeAmountTwo;

	public ETSTransaction(String issuer, String acquirer, int MTI, String cardNumber, float amount, String currency,
			Date transactionDate, String transactionDesc, String terminalId, String transactionPlace, int stan,
			String refnumF37, String authIdRespF38, String FeUtrnno, String BoUtrnno, float feeAmountOne,
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

	@Override
	public int compareTo(ETSTransaction o) {
		return Integer.compare(o.getStan(), this.getStan());
	}


}
