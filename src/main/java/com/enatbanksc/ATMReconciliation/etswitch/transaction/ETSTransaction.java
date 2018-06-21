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
import lombok.NoArgsConstructor;


/**
 *
 * @author btinsae
 */
@Entity(name = "ets_transactions")
@NoArgsConstructor
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
		return this.getStan() > o.getStan() ? -1 : (this.getStan() < o.getStan()) ? 1 : 0;
	}

	@Override
	public String toString() {
		return "ETSTransaction{" + "id=" + id + ", issuer=" + issuer + ", acquirer=" + acquirer + ", MTI=" + MTI
				+ ", cardNumber=" + cardNumber + ", amount=" + amount + ", currency=" + currency + ", transactionDate="
				+ transactionDate + ", transactionDesc=" + transactionDesc + ", terminalId=" + terminalId
				+ ", transactionPlace=" + transactionPlace + ", stan=" + stan + ", refnumF37=" + refnumF37
				+ ", authIdRespF38=" + authIdRespF38 + ", FeUtrnno=" + FeUtrnno + ", BoUtrnno="
				+ BoUtrnno /* + ", feeAmountOne=" + feeAmountOne + ", feeAmountTwo=" + feeAmountTwo */ + '}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIssuer() {
		return issuer;
	}

	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public String getAcquirer() {
		return acquirer;
	}

	public void setAcquirer(String acquirer) {
		this.acquirer = acquirer;
	}

	public int getMTI() {
		return MTI;
	}

	public void setMTI(int mTI) {
		MTI = mTI;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getTransactionDesc() {
		return transactionDesc;
	}

	public void setTransactionDesc(String transactionDesc) {
		this.transactionDesc = transactionDesc;
	}

	public String getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}

	public String getTransactionPlace() {
		return transactionPlace;
	}

	public void setTransactionPlace(String transactionPlace) {
		this.transactionPlace = transactionPlace;
	}

	public int getStan() {
		return stan;
	}

	public void setStan(int stan) {
		this.stan = stan;
	}

	public String getRefnumF37() {
		return refnumF37;
	}

	public void setRefnumF37(String refnumF37) {
		this.refnumF37 = refnumF37;
	}

	public String getAuthIdRespF38() {
		return authIdRespF38;
	}

	public void setAuthIdRespF38(String authIdRespF38) {
		this.authIdRespF38 = authIdRespF38;
	}

	public String getFeUtrnno() {
		return FeUtrnno;
	}

	public void setFeUtrnno(String feUtrnno) {
		FeUtrnno = feUtrnno;
	}

	public String getBoUtrnno() {
		return BoUtrnno;
	}

	public void setBoUtrnno(String boUtrnno) {
		BoUtrnno = boUtrnno;
	}

	public float getFeeAmountOne() {
		return feeAmountOne;
	}

	public void setFeeAmountOne(float feeAmountOne) {
		this.feeAmountOne = feeAmountOne;
	}

	public float getFeeAmountTwo() {
		return feeAmountTwo;
	}

	public void setFeeAmountTwo(float feeAmountTwo) {
		this.feeAmountTwo = feeAmountTwo;
	}

}
