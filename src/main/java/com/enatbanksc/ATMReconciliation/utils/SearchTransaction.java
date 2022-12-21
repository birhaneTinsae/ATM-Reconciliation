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
     * Search for the transaction given rrn
     *
     * @param transactions
     * @param rrn
     * @return true if the transaction is found else false.
     */
    public static boolean searchETSTransaction(List<ETSTransaction> transactions, String rrn) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }
        return SortTransactions.sortETSTransactions(transactions)
                .stream()
                .anyMatch(transaction -> transaction.getRefnumF37().equals(rrn));
    }

    /**
     * Search for the transaction given refNum37
     *
     * @param transactions
     * @param refNum37
     * @return true if the transaction is found else false.
     */
    public static boolean searchENTransaction( List<ENTransaction> transactions, String refNum37) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }

        return SortTransactions.sortENTransactions(transactions)
                .stream()
                .anyMatch(transaction -> transaction.getRrn().equals(refNum37));
    }
}
