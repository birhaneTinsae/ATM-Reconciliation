/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.batch;

import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
import java.util.Date;
import org.springframework.batch.item.ItemProcessor;

/**
 *
 * @author btinsae
 */
public class ETSTransactionItemProcessor implements ItemProcessor<ETSTransaction, ETSTransaction> {

    @Override
    public ETSTransaction process(ETSTransaction i) throws Exception {
        String issuer = i.getIssuer();
        String acquirer = i.getAcquirer();
        int MTI = i.getMTI();
        String cardNumber = i.getCardNumber();
        float amount = i.getAmount();
        String currency = i.getCurrency();
        Date transactionDate = i.getTransactionDate();
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
        ETSTransaction transformedTransaction = new ETSTransaction(issuer, acquirer, MTI, cardNumber, amount, currency, transactionDate, transactionDesc, terminalId, transactionPlace, stan, refnumF37, authIdRespF38, FeUtrnno, BoUtrnno, feeAmountOne, feeAmountTwo);
        return transformedTransaction;
    }

}
