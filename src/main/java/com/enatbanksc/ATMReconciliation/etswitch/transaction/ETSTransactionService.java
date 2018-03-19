/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.etswitch.transaction;

import com.enatbanksc.ATMReconciliation.utils.Common;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author btinsae
 */
@Service
public class ETSTransactionService implements Common<ETSTransaction> {

    @Autowired
    ETSTransactionRepository repository;

    /**
     *
     * @param t
     * @return
     */
    @Override
    public ETSTransaction store(ETSTransaction t) {
        return repository.save(t);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public ETSTransaction show(int id) {
        return repository.getOne(id);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public ETSTransaction update(ETSTransaction t) {
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
        return repository.findById(id) != null;
    }

    /**
     *
     * @return
     */
    @Override
    public List<ETSTransaction> getAll() {
        return repository.findAll();
    }

    /**
     *
     * @param from
     * @param to
     * @return
     */
    public List<ETSTransaction> getTransactionsBetween(Date from, Date to) {
        return repository.findTransactionDateBetween(from, to);
    }
}
