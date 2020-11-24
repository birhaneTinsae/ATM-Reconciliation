/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

/**
 * @author btinsae
 * @version 1.0
 */
@Component
@Log4j2
public class ETSTransactionItemProcessor implements ItemProcessor<TransactionInput, TransactionOutput> {

    @Override
    public TransactionOutput process(TransactionInput i) {
        String issuer = i.getIssuer();
        String acquirer = i.getAcquirer();
        int MTI = Integer.parseInt(i.getMTI());
        String cardNumber = i.getCardNumber();
        float amount = Float.parseFloat(i.getAmount());
        String currency = i.getCurrency();
        log.debug(i.getTransactionDate());
        LocalDateTime transactionDate = LocalDateTime.parse(i.getTransactionDate(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        String transactionDesc = i.getTransactionDesc();
        String terminalId = i.getTerminalId();
        String transactionPlace = i.getTransactionPlace();
        int stan = Integer.parseInt(i.getStan());
        String refnumF37 = i.getRefnumF37();
        String authIdRespF38 = i.getAuthIdRespF38();
        String feUtrnno = i.getFeUtrnno();
        String boUtrnno = i.getBoUtrnno();
        float feeAmountOne = Float.parseFloat(i.getFeeAmountOne());
        float feeAmountTwo = Float.parseFloat(i.getFeeAmountTwo());


        return new TransactionOutput(issuer, acquirer,
                MTI, cardNumber, amount, currency,
                transactionDate, transactionDesc,
                terminalId, transactionPlace, stan, refnumF37,
                authIdRespF38, feUtrnno, boUtrnno,
                feeAmountOne, feeAmountTwo);
    }

}
