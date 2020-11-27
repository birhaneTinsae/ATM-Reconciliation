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
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author btinsae
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ENTransactionService implements Common<ENTransaction> {

    private final ENTransactionRepository repository;

    /**
     * @param t
     * @return ENTtransaction object
     */
    @Override
    public ENTransaction store(ENTransaction t) {
        return repository.save(t);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public ENTransaction show(int id) {
        return repository.getOne(id);
    }

    /**
     * @param t
     * @return
     */
    @Override
    public ENTransaction update(ENTransaction t) {
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
    public List<ENTransaction> getAll() {
        return repository.findAll();
    }

    @Override
    public Page<ENTransaction> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<ENTransaction> getEntTransactionBetween(LocalDate fromDate, LocalDate toDate, String branch) {
        log.debug(String.format("From date %s to date %s and branch %s", fromDate.toString(), toDate.toString(), branch));
        return repository.findByTransactionDateBetweenAndBranchOrderById(fromDate, toDate, branch);
    }

}
