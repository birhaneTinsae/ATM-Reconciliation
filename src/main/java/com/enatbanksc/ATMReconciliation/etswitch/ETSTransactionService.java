/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch;

import com.enatbanksc.ATMReconciliation.branch.Branch;
import com.enatbanksc.ATMReconciliation.branch.BranchRepository;
import com.enatbanksc.ATMReconciliation.utils.Common;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.enatbanksc.ATMReconciliation.etswitch.ETSTransaction;

/**
 * @author btinsae
 */
@Service
public class ETSTransactionService implements Common<ETSTransaction> {

    @Autowired
    private ETSTransactionRepository repository;
    @Autowired
    private BranchRepository branchRepository;

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
        return repository.getOne(id);
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

    /**
     * @param from
     * @param to
     * @param branchCode
     * @return
     */
    public List<ETSTransaction> getTransactionsBetween(Date from, Date to, String branchCode) {
        Branch branch = branchRepository.findByCode(branchCode);
        return repository.findByTransactionDateBetweenAndTerminalIdOrderByTransactionDate(from, to, branch.getTerminalId());
    }

    public List<ETSTransaction> getTransactionsBetween(Date from, Date to) {
        //return repository.findByTransactionDateBetweenOrderByTransactionDate(from, to);
        return repository.findByTransactionDateGreaterThanOrEqualAndTransactionDateLessThanOrderByTransactionDate(from, to);
    }

    public List<Date> loadedTransactionsDate() {
        return repository.loadedTransactionsDate();

    }
}
