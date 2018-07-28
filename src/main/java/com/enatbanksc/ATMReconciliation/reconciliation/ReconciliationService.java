/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;

import com.enatbanksc.ATMReconciliation.branch.Branch;
import com.enatbanksc.ATMReconciliation.branch.BranchService;
import com.enatbanksc.ATMReconciliation.enat.transaction.ENTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
import com.enatbanksc.ATMReconciliation.utils.SearchTransaction;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author btinsae
 * @version 1.0
 *
 */
@Service
public class ReconciliationService {

    @Autowired
    BranchService branchService;

    /**
     * Search transactions that exist in CBS but missed from Switch vendor
     *
     * @param enatTransactions list of ENAT CBS transactions within the given
     * dates.
     * @param etsts list of ETSwitch transaction within the given dates.
     * @return list of missing transactions from ETS
     */
    private List<ENTransaction> getTransactionsMissingFromETSNotPaid(List<ENTransaction> enatTransactions,
            List<ETSTransaction> etsts) {
        if (enatTransactions == null || enatTransactions.isEmpty()) {
            return null;
        }

        return enatTransactions.stream()
                .filter(entTransaction
                        -> (!SearchTransaction.searchETSTransaction(etsts,
                        entTransaction.getStan())))
                .filter(entTransaction -> !isPaid(entTransaction))
                .collect(Collectors.toList());

    }

    private List<ENTransaction> getTransactionsMissingFromETSPaid(List<ENTransaction> enatTransactions,
            List<ETSTransaction> etsts) {
        if (enatTransactions == null || enatTransactions.isEmpty()) {
            return null;
        }

        return enatTransactions.stream()
                .filter(entTransaction
                        -> (!SearchTransaction.searchETSTransaction(etsts,
                        entTransaction.getStan())))
                .filter(entTransaction -> isPaid(entTransaction))
                .collect(Collectors.toList());

    }

    /**
     * Search transactions that exist in Switch vendor but missed from CBS.
     *
     * @param eNTransactions list of ENAT CBS transactions within the given
     * dates.
     * @param etsts list of ETSwitch transaction within the given dates.
     * @return list of missing transactions from ENAT CBS.
     */
    private List<ETSTransaction> getTransactionsMissingFromENT(List<ENTransaction> eNTransactions,
            List<ETSTransaction> etsts) {

        if (eNTransactions == null || eNTransactions.isEmpty()) {
            return null;
        }

        return etsts.stream()
                .filter(etsTransaction
                        -> (!SearchTransaction.searchENTransaction(eNTransactions,
                        etsTransaction.getStan())))
                .collect(Collectors.toList());

    }

    /**
     * *
     * Check if the transaction is paid or not based on EJ reference.
     *
     * @param transaction ETSTransaction or ENTransction instance.
     * @return true if paid else return false.
     */
    private boolean isPaid(Object transaction) {
        if (transaction instanceof ENTransaction) {
            ENTransaction eNTransaction = (ENTransaction) transaction;
            Branch branch = branchService.getBranchByCode(eNTransaction.getBranch());
            return EJUtil.isPaid(branch.getEjUri(), branch.getEjDirectory(),
                    String.valueOf(eNTransaction.getStan()),
                    eNTransaction.getTransactionDate());
        } else {

            ETSTransaction estTransaction = (ETSTransaction) transaction;
            Branch branch = branchService.getBranchByTerminalId(estTransaction.getTerminalId());
            return EJUtil.isPaid(branch.getEjUri(), branch.getEjDirectory(),
                    String.valueOf(estTransaction.getStan()),
                    estTransaction.getTransactionDate());

        }

    }

    /**
     * *
     * return ENAT transactions that are not paid and not recognized by Switch
     * vendor(ETSWITCH).
     *
     * @param enTransactions list of ENAT CBS transactions within the given
     * dates.
     * @param etsts list of ETSwitch transaction within the given dates.
     * @return List of ENTransaction that are not paid and not recognized by
     * Switch vendor(ETSWITCH).
     */
    public List<ENTransaction> getReversals(List<ENTransaction> enTransactions,
            List<ETSTransaction> etsts) {

        return getTransactionsMissingFromETSNotPaid(enTransactions, etsts);
    }

    /**
     * *
     * return ENAT transactions that are paid and not recognized by switch
     * vendor(ETSWITCH)
     *
     * @param enTransactions list of ENAT CBS transactions within the given
     * dates.
     * @param etsts list of ETSwitch transaction within the given dates.
     * @return List of ENTransaction that are paid and not recognized by switch
     * vendor(ETSWITCH)
     */
    public List<ENTransaction> getClaims(List<ENTransaction> enTransactions, List<ETSTransaction> etsts) {

        return getTransactionsMissingFromETSPaid(enTransactions, etsts);
    }

    /**
     * *
     * return ETS transactions that are paid but doesn't have accounting entry
     * on ENT CBS.
     *
     * @param transactions list of ENAT CBS transactions within the given dates.
     * @param etsts list of ETSwitch transaction within the given dates.
     * @return List ETSTransaction.
     */
    public List<ETSTransaction> getPosts(List<ENTransaction> transactions,
            List<ETSTransaction> etsts) {

        return getTransactionsMissingFromENT(transactions, etsts);
    }
}
