/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;

import com.enatbanksc.ATMReconciliation.enat.ENTransaction;
import com.enatbanksc.ATMReconciliation.local.branch.Branch;
import com.enatbanksc.ATMReconciliation.local.branch.BranchService;
import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransaction;
import com.enatbanksc.ATMReconciliation.utils.SearchTransaction;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author btinsae
 * @version 1.0
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class ReconciliationService {

    private final BranchService branchService;

    /**
     * Search transactions that exist in CBS but missed from Switch vendor
     *
     * @param enatTransactions list of ENAT CBS transactions within the given
     *                         dates.
     * @param etsts            list of ETSwitch transaction within the given dates.
     * @return list of missing transactions from ETS
     */
    private List<ENTransaction> getTransactionsMissingFromETSNotPaid(@javax.validation.constraints.NotNull() @NotEmpty() List<ENTransaction> enatTransactions,
                                                                     List<ETSTransaction> etsts) {
        if (enatTransactions == null || enatTransactions.isEmpty()) {
            return null;
        }

        return enatTransactions.stream()
                .filter(entTransaction
                        -> (!SearchTransaction.searchETSTransaction(etsts,
                        entTransaction.getRrn())))
                .filter(entTransaction -> !isPaid(entTransaction))
                .collect(Collectors.toList());

    }

    private List<ENTransaction> getTransactionsMissingFromETSPaid(@javax.validation.constraints.NotNull() @NotEmpty() List<ENTransaction> enatTransactions,
                                                                  List<ETSTransaction> etsts) {
        if (enatTransactions == null || enatTransactions.isEmpty()) {
            return null;
        }

        return enatTransactions.stream()
                .filter(entTransaction
                        -> (!SearchTransaction.searchETSTransaction(etsts,
                        entTransaction.getRrn())))
                .filter(this::isPaid)
                .collect(Collectors.toList());

    }

    /**
     * *
     * Search for successful transaction. Transactions that exist in CBS and
     * EtSwitch.
     *
     * @param enatTransactions
     * @param etsts
     * @return
     */
    private List<ENTransaction> getSuccessfulATMTransactions(
            @NotNull List<ENTransaction> enatTransactions,
            List<ETSTransaction> etsts) {
        if ((enatTransactions == null || enatTransactions.isEmpty())
                && (etsts.isEmpty())) {
            return null;
        }

        return enatTransactions.stream()
                .filter(entTransaction
                        -> (SearchTransaction.searchETSTransaction(etsts,
                        entTransaction.getRrn())))
                .collect(Collectors.toList());

    }

    /**
     * Search transactions that exist in Switch vendor but missed from CBS.
     *
     * @param eNTransactions list of ENAT CBS transactions within the given
     *                       dates.
     * @param etsts          list of ETSwitch transaction within the given dates.
     * @return list of missing transactions from ENAT CBS.
     */
    private List<ETSTransaction> getTransactionsMissingFromENT(@javax.validation.constraints.NotNull() @NotEmpty()List<ENTransaction> eNTransactions,
                                                               List<ETSTransaction> etsts) {

        if (eNTransactions == null || eNTransactions.isEmpty()) {
            return null;
        }

        return etsts.stream()
                .filter(etsTransaction
                        -> (!SearchTransaction.searchENTransaction(eNTransactions,
                        etsTransaction.getRefnumF37())))
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
                    eNTransaction.getTransactionDate().atStartOfDay().toLocalDate());
        } else {

            ETSTransaction estTransaction = (ETSTransaction) transaction;
            Branch branch = branchService.getBranchByTerminalId(estTransaction.getTerminalId());
            return EJUtil.isPaid(branch.getEjUri(), branch.getEjDirectory(),
                    String.valueOf(estTransaction.getStan()),
                    estTransaction.getTransactionDate().toLocalDate());

        }

    }

    /**
     * *
     * return ENAT transactions that are not paid and not recognized by Switch
     * vendor(ETSWITCH).
     *
     * @param enTransactions list of ENAT CBS transactions within the given
     *                       dates.
     * @param etsts          list of ETSwitch transaction within the given dates.
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
     *                       dates.
     * @param etsts          list of ETSwitch transaction within the given dates.
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
     * @param etsts        list of ETSwitch transaction within the given dates.
     * @return List ETSTransaction.
     */
    public List<ETSTransaction> getPosts(List<ENTransaction> transactions,
                                         List<ETSTransaction> etsts) {
        return getTransactionsMissingFromENT(transactions, etsts);
    }

    public List<ENTransaction> getATMTransactions(List<ENTransaction> transactions,
                                                  List<ETSTransaction> etsts) {
        return getSuccessfulATMTransactions(transactions, etsts);
    }
}
