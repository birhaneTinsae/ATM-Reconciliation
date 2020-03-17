/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.utils;

import com.enatbanksc.ATMReconciliation.enat.ENTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;
import java.util.List;

/**
 *
 * @author btinsae
 * @version 1.0
 */

public class SearchTransaction {

//    @Autowired
//    private SortTransactions sort;

//    public SearchTransaction(SortTransactions sort) {
//        this.sort = sort;
//    }
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
                .filter(transaction -> transaction.getStan() == stan)
                .findFirst().isPresent();// != "";
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
                .filter(transaction -> transaction.getStan() == stan)
                .findFirst().isPresent();
    }
}
