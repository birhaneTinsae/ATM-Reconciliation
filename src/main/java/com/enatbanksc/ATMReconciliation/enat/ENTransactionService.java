/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.enat;

import com.enatbanksc.ATMReconciliation.utils.Common;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author btinsae
 */
@Service
@RequiredArgsConstructor
public class ENTransactionService implements Common<ENTransaction> {

    private final ENTransactionRepository repository;

    /**
     *
     * @param t
     * @return ENTtransaction object
     */
    @Override
    public ENTransaction store(ENTransaction t) {
        return repository.save(t);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ENTransaction show(int id) {
        return repository.getOne(id);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public ENTransaction update(ENTransaction t) {
        return repository.save(t);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public boolean delete(int id) {
        repository.deleteById(id);
        return !repository.existsById(id);
    }

    /**
     *
     * @return
     */
    @Override
    public List<ENTransaction> getAll() {
        return repository.findAll();
    }

    public List<ENTransaction> getEntTransactionBetween(LocalDate fromDate, LocalDate toDate, String branch) {
       // return repository.findByTransactionDateGreaterThanAndTransactionDateLessThanAndBranch(fromDate, toDate, branch);
        return repository.findByTransactionDateBetweenAndBranchOrderById(fromDate, toDate, branch);
    }

}
