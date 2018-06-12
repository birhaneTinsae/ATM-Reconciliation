/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.branch;

import com.enatbanksc.ATMReconciliation.utils.Common;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author btinsae
 */
@Service
public class BranchService implements Common<Branch> {

    @Autowired
    BranchRepository repository;

    /**
     *
     * @param t
     * @return
     */
    @Override
    public Branch store(Branch t) {
        return repository.save(t);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public Branch show(int id) {
        return repository.getOne(id);
    }

    /**
     *
     * @param t
     * @return
     */
    @Override
    public Branch update(Branch t) {
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
    public List<Branch> getAll() {
        return repository.findAll();
    }

    public Branch getBranchByTerminalId(String terminalId) {
        return repository.findByTerminalId(terminalId);
    }
    public Branch getBranchByCode(String code){
        return repository.findByCode(code);
    }
}
