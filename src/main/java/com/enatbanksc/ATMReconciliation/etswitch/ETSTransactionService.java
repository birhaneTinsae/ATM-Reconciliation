/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch;

import com.enatbanksc.ATMReconciliation.branch.Branch;
import com.enatbanksc.ATMReconciliation.branch.BranchRepository;
import com.enatbanksc.ATMReconciliation.utils.Common;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

/**
 * @author btinsae
 */
@Service
@RequiredArgsConstructor
public class ETSTransactionService implements Common<ETSTransaction> {

    private final ETSTransactionRepository repository;
    private final BranchRepository branchRepository;

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
                .orElseThrow(EntityNotFoundException::new);
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
    public List<ETSTransaction> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<ETSTransaction> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    /**
     * @param from
     * @param to
     * @param branchCode
     * @return
     */
    public List<ETSTransaction> getTransactionsBetween(LocalDate from, LocalDate to, String branchCode) {
        Branch branch = branchRepository.findByCode(branchCode);
        return repository.findByTransactionDateBetweenAndTerminalIdOrderByTransactionDate(from, to, branch.getTerminalId());
    }

    public List<ETSTransaction> getTransactionsBetween(LocalDate from, LocalDate to) {
        return repository.findByTransactionDateGreaterThanOrEqualAndTransactionDateLessThanOrderByTransactionDate(from, to);
    }

    public List<Date> loadedTransactionsDate() {
        return repository.loadedTransactionsDate();

    }
}
