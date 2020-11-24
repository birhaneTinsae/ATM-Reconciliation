/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.utils;

import com.enatbanksc.ATMReconciliation.enat.ENTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author btinsae
 * @version 1.0
 */

public class SearchTransaction {


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
        var txn = new ETSTransaction();
        txn.setStan(stan);
        return Collections.binarySearch(SortTransactions.sortETSTransactions(transactions), txn, Comparator.comparing(ETSTransaction::getStan)) > 0;
//        return SortTransactions.sortETSTransactions(transactions)
//                .stream()
//                .anyMatch(transaction -> transaction.getStan() == stan);
    }

    /**
     * Search for the transaction given stan
     *
     * @param transactions
     * @param stan
     * @return true if the transaction is found else false.
     */
    public static boolean searchENTransaction(List<ENTransaction> transactions, int stan) {
        if (transactions == null || transactions.isEmpty()) {
            return false;
        }
        var txn = new ENTransaction();
        txn.setStan(stan);
        return Collections.binarySearch(SortTransactions.sortENTransactions(transactions),
                txn,
                Comparator.comparing(ENTransaction::getStan)) > 0;
//
//        return SortTransactions.sortENTransactions(transactions)
//                .stream()
//                .anyMatch(transaction -> transaction.getStan() == stan);
    }
}
