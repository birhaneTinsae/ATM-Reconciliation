/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.utils;

import com.enatbanksc.ATMReconciliation.enat.ENTransaction;
import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransaction;

import java.util.List;

/**
 *
 * @author btinsae
 * @version 1.0
 */

public class SearchTransaction {


    private SearchTransaction() {
    }
    /**
     * Search for the transaction given stan
     *
     * @param transactions
     * @param stan
     * @return true if the transaction is found else false.
     */
    public static boolean searchETSTransaction(List<ETSTransaction> transactions, int stan) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }
        return SortTransactions.sortETSTransactions(transactions)
                .stream()
                .anyMatch(transaction -> transaction.getRefnumF37() == stan);
    }

    /**
     * Search for the transaction given stan
     *
     * @param transactions
     * @param stan
     * @return true if the transaction is found else false.
     */
    public static boolean searchENTransaction( List<ENTransaction> transactions, int stan) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }

        return SortTransactions.sortENTransactions(transactions)
                .stream()
                .anyMatch(transaction -> transaction.getRrn() == stan);
    }
}
