/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.utils;

import com.enatbanksc.ATMReconciliation.enat.transaction.ENTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author btinsae
 * @version 1.0
 */
public class SearchTransaction {

    @Autowired
    private SortTransactions sort;

    /**
     * Search for the transaction given stan
     *
     * @param transactions
     * @param stan
     * @return true if the transaction is found else false.
     */
    public boolean searchETSTransaction(List<ETSTransaction> transactions, int stan) {
        return sort.sortETSTransactions(transactions)
                .stream()
                .filter(transaction -> transaction.getStan() == stan)
                .findFirst().get() != null;
    }

    /**
     * Search for the transaction given stan
     *
     * @param transactions
     * @param stan
     * @return true if the transaction is found else false.
     */
    public boolean searchENTransaction(List<ENTransaction> transactions, int stan) {
        return sort.sortENTransactions(transactions)
                .stream()
                .filter(transaction -> transaction.getStan() == stan)
                .findFirst().get() != null;
    }
}
