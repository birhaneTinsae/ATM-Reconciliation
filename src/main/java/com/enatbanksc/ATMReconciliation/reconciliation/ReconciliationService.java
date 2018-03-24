/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;

import com.enatbanksc.ATMReconciliation.config.BatchConfiguration;
import com.enatbanksc.ATMReconciliation.enat.transaction.ENTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
import com.enatbanksc.ATMReconciliation.utils.SearchTransaction;
import java.util.ArrayList;
import java.util.List;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author btinsae
 * @version 1.0
 *
 */
public class ReconciliationService {

    SearchTransaction searchTransaction;

    public ReconciliationService(SearchTransaction searchTransaction) {
        this.searchTransaction = searchTransaction;
    }

    /**
     * Search transactions that exist in CBS but missed from Switch vendor
     *
     * @param enatTransactions
     * @return missing transactions from ETS
     */
    public List<ENTransaction> getTransactionsMissingFromETS(List<ENTransaction> enatTransactions) {
        List<ENTransaction> missingTransactions = new ArrayList<>();
        enatTransactions.stream().filter((transaction) -> (!searchTransaction.searchENTransaction(enatTransactions, transaction.getStan()))).forEachOrdered((transaction) -> {
            missingTransactions.add(transaction);
        });
        return missingTransactions;
    }

    /**
     * Search transactions that exist in Switch vendor but missed from CBS.
     *
     * @param etsTransactions
     * @return missing transactions from CBS.
     */
    public List<ETSTransaction> getTransactionsMissingFromENT(List<ETSTransaction> etsTransactions) {
        List<ETSTransaction> missingTransactions = new ArrayList<>();
        etsTransactions.stream().filter((transaction) -> (searchTransaction.searchETSTransaction(etsTransactions, transaction.getStan()))).forEachOrdered((transaction) -> {
            missingTransactions.add(transaction);
        });
        return missingTransactions;
    }

}
