/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransactionInput;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 *
 * @author btinsae
 * @version 1.0
 */
@Component
public class ETSTransactionItemProcessor implements ItemProcessor<ETSTransactionInput, ETSTransaction> {

	@Override
	public ETSTransaction process(ETSTransactionInput i) {
		String issuer = i.getIssuer();
		String acquirer = i.getAcquirer();
		int MTI = i.getMTI();
		String cardNumber = i.getCardNumber();
		float amount = i.getAmount();
		String currency = i.getCurrency();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		LocalDateTime transactionDate = LocalDateTime.parse(i.getTransactionDate(),formatter);
		String transactionDesc = i.getTransactionDesc();
		String terminalId = i.getTerminalId();
		String transactionPlace = i.getTransactionPlace();
		int stan = i.getStan();
		String refnumF37 = i.getRefnumF37();
		String authIdRespF38 = i.getAuthIdRespF38();
		String FeUtrnno = i.getFeUtrnno();
		String BoUtrnno = i.getBoUtrnno();
		float feeAmountOne = i.getFeeAmountOne();
		float feeAmountTwo = i.getFeeAmountTwo();
		return new ETSTransaction(issuer, acquirer, MTI, cardNumber, amount, currency,
				transactionDate, transactionDesc, terminalId, transactionPlace, stan, refnumF37, authIdRespF38,
				FeUtrnno, BoUtrnno, feeAmountOne, feeAmountTwo);
	}

}
