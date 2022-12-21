/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.local.etswitch;

import com.enatbanksc.ATMReconciliation.local.branch.Branch;
import com.enatbanksc.ATMReconciliation.local.branch.BranchRepository;
import com.enatbanksc.ATMReconciliation.exceptions.EntityNotFoundException;
import com.enatbanksc.ATMReconciliation.local.branch.BranchService;
import com.enatbanksc.ATMReconciliation.utils.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * @author btinsae
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ETSTransactionService implements Common<ETSTransaction> {

    private final ETSTransactionRepository repository;
    private final BranchService branchService;

    /**
     * @param t
     * @return
     */
    @Override
    public ETSTransaction store(ETSTransaction t) {
        return repository.save(t);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ETSTransaction show(int id) {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(ETSTransaction.class,"id",String.valueOf(id)));
    }

    /**
     * @param t
     * @return
     */
    @Override
    public ETSTransaction update(ETSTransaction t) {
        return repository.save(t);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean delete(int id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    /**
     * @return
     */
    @Override
    public Iterable<ETSTransaction> getAll() {
        return repository.findAll();
    }

    /**
     * @param from
     * @param to
     * @param branchCode
     * @return
     */
    public List<ETSTransaction> getTransactionsBetween(LocalDate from, LocalDate to, String branchCode) {
        Branch branch = branchService.getBranchByCode(branchCode);
        return repository.findByTransactionDateBetweenAndTerminalIdOrderByTransactionDate(from.atStartOfDay(), to.plusDays(1).atStartOfDay(), branch.getTerminalId());
    }

    public List<ETSTransaction> getTransactionsBetween(LocalDate from, LocalDate to) {
        return repository.findByTransactionDateGreaterThanOrEqualAndTransactionDateLessThanOrderByTransactionDate(from, to);
    }

    public List<LocalDate> loadedTransactionsDate() {
        return repository.loadedTransactionsDate();

    }
}
