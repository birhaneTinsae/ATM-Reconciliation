/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.local.branch;

import com.enatbanksc.ATMReconciliation.exceptions.EntityNotFoundException;
import com.enatbanksc.ATMReconciliation.utils.Common;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author btinsae
 */
@Service
@RequiredArgsConstructor
public class BranchService implements Common<Branch> {

    private final BranchRepository repository;

    /**
     * @param t
     * @return
     */
    @Override
    public Branch store(Branch t) {
        return repository.save(t);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Branch show(int id) {
        return repository.findById(id)
                .orElseThrow(()->new EntityNotFoundException(Branch.class,"Id",String.valueOf(id)));
    }

    /**
     * @param t
     * @return
     */
    @Override
    public Branch update(Branch t) {
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
    public Iterable<Branch> getAll() {
        return repository.findAll();
    }

    public Branch getBranchByTerminalId(String terminalId) {
        return repository.findByTerminalId(terminalId);
    }

    public Branch getBranchByCode(String code) {
        return repository.findByCode(code).orElseThrow(()->new EntityNotFoundException(Branch.class,"Code",code));
    }
}
